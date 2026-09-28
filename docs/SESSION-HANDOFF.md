# Session handoff — paste this prompt to resume

> Copy everything in the block below into a new session.

```
Read docs/PROGRESS.md first (rows 30–44 registered, KB 2311 codes / 44 brands, validate 0/0),
then docs/BRAND-ROADMAP.md, docs/SCHEMA.md, docs/RESEARCH-PROTOCOL.md, docs/AUTHORING-GUIDE.md,
data/index.json.

Continue Phase 3 from row 45 **Sharp** (`sharp`). The previous Sharp research subagent was
interrupted twice (internet drops) — research.md was never written, but /tmp/opencode/sharp/
has salvageable cache (sm-zu1.txt = extracted Sharp ZU1 service manual, pg37–46.html =
ManualsLib pages, trseng.html). Include that cache in the research prompt.

Follow the established per-brand process exactly:
1. mkdir -p /tmp/opencode/<brand>
2. task subagent research → write only /tmp/opencode/<brand>/research.md
   (fenced YAML blocks {series, unitType, source, entries:[...]}; brand-recon/source-table/
   NEGATIVE/QUARANTINE sections; target 30–90 codes; report count/series/top-5 URLs/blockers/
   official-table yes-no/PK-presence yes-no). On subagent or internet drop: retry same prompt.
3. Inspect YAML blocks with python + fix_yaml_colons
4. Write /tmp/opencode/gen_<brand>.py (SERIES_META + SRC_MAP + UNIT_MAP + make_source;
   helpers already exist at /tmp/opencode/gen_helpers.py — rebuild from registered data shape
   in data/ac/*/ if /tmp is wiped again)
5. Run generator + URDU leak/dup scan (per-entry key-set; aliases[0]==code is by design —
   only scan against keys of PREVIOUS entries)
6. docs/sources/<brand>.md → index.json entry → BRAND-ROADMAP row ☑ → PROGRESS §1/§3/§5
7. .venv/bin/python tools/validate.py must be 0 errors / 0 warnings → next row

Hard rules: ur = Roman Urdu (Latin only, no Arabic/Urdu script in ur OR en fields);
model-scoped series never merged; brand exclusions (Sharp ≠ other brands; Carrier row 46 =
residential if any); confidence + per-code notes conflicts logged; english paraphrased.

Remaining queue: rows 45–46 finish Phase 3 (Sharp, Carrier), rows 47–61 Phase 4 inverters
(Growatt, Deye, GoodWe, Solis, FoxESS, Sungrow, Huawei, Sofar, Victron, Fronius-AT, SMA,
Felicity, Must, APC, UPS bulk-add).
```
