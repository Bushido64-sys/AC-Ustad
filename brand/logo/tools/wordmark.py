#!/usr/bin/env python3
"""Convert a line of text to an SVG path using a local TTF.

No external dependencies beyond fonttools. Coordinates are returned in SVG
space with the baseline at y=0; wrap the path in a translate to position it.

Usage:
  python3 wordmark.py --font FONT.ttf --text "AC Ustad" --size 100
"""
import argparse
import json

from fontTools.ttLib import TTFont
from fontTools.pens.svgPathPen import SVGPathPen
from fontTools.pens.transformPen import TransformPen
from fontTools.pens.roundingPen import RoundingPen
from fontTools.misc.transform import Transform


def text_path(font_path, text, size, tracking=0.0):
    font = TTFont(font_path)
    glyph_set = font.getGlyphSet()
    cmap = font.getBestCmap()
    units = font["head"].unitsPerEm
    scale = size / units
    x = 0.0
    parts = []
    for ch in text:
        if ch == " ":
            x += font["hmtx"][cmap[ord(" ")]][0] + tracking
            continue
        gname = cmap[ord(ch)]
        glyph = glyph_set[gname]
        svg_pen = SVGPathPen(glyph_set)
        pen = RoundingPen(svg_pen, roundFunc=lambda v: round(v, 2))
        tpen = TransformPen(pen, Transform().scale(scale, -scale).translate(x, 0))
        glyph.draw(tpen)
        d = svg_pen.getCommands()
        if d:
            parts.append(d)
        x += font["hmtx"][gname][0] + tracking
    width = (x - tracking) * scale
    return " ".join(parts), width


def font_metrics(font_path):
    font = TTFont(font_path)
    units = font["head"].unitsPerEm
    os2 = font["OS/2"]
    return {
        "unitsPerEm": units,
        "capHeight": getattr(os2, "sCapHeight", None),
        "xHeight": getattr(os2, "sxHeight", None),
        "ascender": font["hhea"].ascender,
        "descender": font["hhea"].descender,
    }


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--font", required=True)
    ap.add_argument("--text", required=True)
    ap.add_argument("--size", type=float, default=100)
    ap.add_argument("--tracking", type=float, default=0)
    ap.add_argument("--json", action="store_true")
    args = ap.parse_args()

    d, width = text_path(args.font, args.text, args.size, args.tracking)
    m = font_metrics(args.font)
    out = {
        "text": args.text,
        "size": args.size,
        "tracking": args.tracking,
        "width": round(width, 2),
        "capHeightPx": round((m["capHeight"] or 0) * args.size / m["unitsPerEm"], 2),
        "xHeightPx": round((m["xHeight"] or 0) * args.size / m["unitsPerEm"], 2),
        "d": d,
    }
    if args.json:
        print(json.dumps(out, indent=2))
    else:
        print(f"width={out['width']}  capHeight={out['capHeightPx']}  xHeight={out['xHeightPx']}")
        print(d)


if __name__ == "__main__":
    main()
