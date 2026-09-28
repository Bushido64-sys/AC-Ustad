#!/usr/bin/env python3
"""Validate AC Ustad data files against schema/brand.schema.json.

Usage:
    pip install jsonschema
    python3 tools/validate.py [path-to-data-dir]

Checks performed:
  1. data/index.json is valid JSON with required shape.
  2. Every brand.json under data/{ac,inverter}/<brand>/ validates (brandFile).
  3. Every other *.json in a brand folder validates (seriesFile).
  4. Cross-checks: brand.json series[].file exists; series file brandId matches
     folder id; series category matches parent category (ac/inverter);
     index.json lists every brand folder and vice versa (status/planned).
  5. Duplicate code detection within a single series file (by code + aliases).
Exit code 0 = clean; 1 = errors found.
"""
from __future__ import annotations

import json
import sys
from pathlib import Path

try:
    import jsonschema
except ImportError:
    print("ERROR: jsonschema not installed. Run: pip install jsonschema")
    sys.exit(2)

ROOT = Path(__file__).resolve().parent.parent
DATA = ROOT / "data"
SCHEMA_PATH = ROOT / "schema" / "brand.schema.json"


def main() -> int:
    errors: list[str] = []
    warnings: list[str] = []

    schema = json.loads(SCHEMA_PATH.read_text(encoding="utf-8"))

    def load(path: Path):
        try:
            return json.loads(path.read_text(encoding="utf-8"))
        except json.JSONDecodeError as exc:
            errors.append(f"{path.relative_to(ROOT)}: invalid JSON — {exc}")
            return None

    def validate(path: Path, doc) -> None:
        if doc is None:
            return
        try:
            jsonschema.validate(doc, schema)
        except jsonschema.ValidationError as exc:
            loc = "/".join(str(p) for p in exc.absolute_path) or "(root)"
            errors.append(f"{path.relative_to(ROOT)}: {exc.message} @ {loc}")

    # --- index.json -------------------------------------------------------
    index_path = DATA / "index.json"
    index = load(index_path)
    index_ids: dict[tuple[str, str], dict] = {}
    if index is not None:
        if not isinstance(index, dict) or "brands" not in index:
            errors.append("data/index.json: missing 'brands' array")
        else:
            for i, entry in enumerate(index["brands"]):
                for key in ("id", "name", "category", "path", "status"):
                    if key not in entry:
                        errors.append(f"data/index.json: brands[{i}] missing '{key}'")
                if entry.get("category") not in ("ac", "inverter"):
                    errors.append(f"data/index.json: brands[{i}] bad category {entry.get('category')!r}")
                if entry.get("status") not in ("planned", "researching", "done"):
                    errors.append(f"data/index.json: brands[{i}] bad status {entry.get('status')!r}")
                if "id" in entry:
                    key = (entry["id"], entry.get("category"))
                    if key in index_ids:
                        errors.append(
                            f"data/index.json: duplicate id {entry['id']!r} "
                            f"for category {entry.get('category')!r}"
                        )
                    index_ids[key] = entry

    # --- brand folders ----------------------------------------------------
    folder_ids: set[str] = set()
    for category in ("ac", "inverter"):
        cat_dir = DATA / category
        if not cat_dir.is_dir():
            continue
        for brand_dir in sorted(p for p in cat_dir.iterdir() if p.is_dir()):
            brand_id = brand_dir.name
            folder_ids.add(brand_id)
            brand_path = brand_dir / "brand.json"
            if not brand_path.exists():
                errors.append(f"{brand_path.relative_to(ROOT)}: missing brand.json")
                continue
            brand_doc = load(brand_path)
            validate(brand_path, brand_doc)
            if brand_doc is None:
                continue

            b = brand_doc.get("brand", {})
            if b.get("id") != brand_id:
                errors.append(
                    f"{brand_path.relative_to(ROOT)}: brand.id {b.get('id')!r} != folder {brand_id!r}"
                )
            if b.get("category") != category:
                errors.append(
                    f"{brand_path.relative_to(ROOT)}: category {b.get('category')!r} != parent dir {category!r}"
                )

            declared_files = {s.get("file") for s in brand_doc.get("series", [])}

            # series files present on disk
            for series_path in sorted(brand_dir.glob("*.json")):
                if series_path.name == "brand.json":
                    continue
                if series_path.name not in declared_files:
                    warnings.append(
                        f"{series_path.relative_to(ROOT)}: not listed in brand.json series[]"
                    )
                sdoc = load(series_path)
                validate(series_path, sdoc)
                if sdoc is None:
                    continue
                if sdoc.get("brandId") != brand_id:
                    errors.append(
                        f"{series_path.relative_to(ROOT)}: brandId {sdoc.get('brandId')!r} != folder {brand_id!r}"
                    )
                if sdoc.get("series", {}).get("category") != category:
                    errors.append(
                        f"{series_path.relative_to(ROOT)}: series.category != {category!r}"
                    )
                # duplicate codes across entries in same file (case-insensitive);
                # case variants of the same entry (E1/e1, F0/Fo) are fine
                seen: set[str] = set()
                for code_obj in sdoc.get("codes", []):
                    variants = set()
                    if code_obj.get("code"):
                        variants.add(code_obj["code"].strip().lower())
                    for alias in code_obj.get("aliases", []) or []:
                        variants.add(alias.strip().lower())
                    for akey in sorted(variants):
                        if akey and akey in seen:
                            warnings.append(
                                f"{series_path.relative_to(ROOT)}: code/alias {akey!r} "
                                f"used by more than one entry"
                            )
                        seen.add(akey)

            # declared files exist
            for s in brand_doc.get("series", []):
                f = s.get("file")
                if f and not (brand_dir / f).exists():
                    errors.append(
                        f"{brand_path.relative_to(ROOT)}: series file {f!r} listed but not found"
                    )

            if (brand_id, category) not in index_ids:
                errors.append(
                    f"data/index.json: missing entry for brand folder {category}/{brand_id}"
                )

    # index entries without folders
    for (bid, cat), entry in index_ids.items():
        if (bid, cat) not in {
            (p.name, category)
            for category in ("ac", "inverter")
            for p in ((DATA / category).iterdir() if (DATA / category).is_dir() else [])
            if p.is_dir()
        }:
            errors.append(
                f"data/index.json: entry {bid!r}/{cat!r} has no folder under "
                f"data/{cat} (path={entry.get('path')!r})"
            )

    # --- report -----------------------------------------------------------
    for w in warnings:
        print(f"WARN  {w}")
    for e in errors:
        print(f"ERROR {e}")
    print(
        f"\nChecked index + {len(folder_ids)} brand folder(s): "
        f"{len(errors)} error(s), {len(warnings)} warning(s)."
    )
    return 1 if errors else 0


if __name__ == "__main__":
    sys.exit(main())
