#!/usr/bin/env python3
"""Build the AC Ustad wordmark and horizontal lockups from the concept SVGs.

The wordmark is set in IBM Plex Sans (SIL OFL): "AC" in Medium at 80,
"Ustad" in Bold at 104, both outlined to paths so the files are font-independent.
"""
import os
import re
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)
FONT_DIR = "/home/atif/AC-Ustad/android/app/src/main/res/font"
MEDIUM = os.path.join(FONT_DIR, "ibm_plex_sans_medium.ttf")
BOLD = os.path.join(FONT_DIR, "ibm_plex_sans_bold.ttf")
INK = "#0B1F2A"

sys.path.insert(0, HERE)
from wordmark import text_path  # noqa: E402


def clean(d):
    """Round every decimal coordinate to at most 2 places."""
    def r(m):
        v = float(m.group())
        s = f"{v:.2f}".rstrip("0").rstrip(".")
        return "0" if s in ("", "-0") else s
    return re.sub(r"-?\d+\.\d+", r, d)


def wordmark_paths():
    ac_d, ac_w = text_path(MEDIUM, "AC", 88, tracking=0)
    ustad_d, ustad_w = text_path(BOLD, "Ustad", 104, tracking=0)
    return clean(ac_d), ac_w, clean(ustad_d), ustad_w


def symbol_inner(path):
    svg = open(path, encoding="utf-8").read()
    inner = re.search(r"<svg[^>]*>(.*)</svg>", svg, re.S).group(1)
    # Drop the title element; it is not needed in the lockup.
    inner = re.sub(r"<title[^>]*>.*?</title>", "", inner, flags=re.S)
    return inner.strip()


def lockup(symbol_path, out_path):
    ac_d, ac_w, ustad_d, ustad_w = wordmark_paths()
    inner = symbol_inner(symbol_path)

    # Each symbol is drawn in a 256 viewBox but has a different visual bbox.
    # Scale the bbox to 176 x 176 and place it at (24, 40) so it sits beside
    # the wordmark at a consistent optical size.
    bboxes = {
        "a-ustad": (36, 36, 184, 184),
        "b-compass": (24, 24, 208, 208),
        "c-seal": (16, 16, 224, 224),
    }
    key = os.path.splitext(os.path.basename(symbol_path))[0]
    bx, by, bw, bh = bboxes[key]
    scale = 176 / max(bw, bh)
    tx = 24 - bx * scale
    ty = 40 - by * scale

    gap = 18
    word_x = 220
    total_w = word_x + ac_w + gap + ustad_w + 24

    svg = f'''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {total_w:.0f} 256" width="{total_w:.0f}" height="256" role="img" aria-labelledby="title">
  <title id="title">AC Ustad — horizontal lockup</title>
  <g transform="translate({tx:.2f} {ty:.2f}) scale({scale:.4f})">
    {inner}
  </g>
  <g transform="translate({word_x} 172)" fill="{INK}">
    <path d="{ac_d}"/>
    <path d="{ustad_d}" transform="translate({ac_w + gap:.2f} 0)"/>
  </g>
</svg>
'''
    open(out_path, "w", encoding="utf-8").write(svg)
    print("wrote", out_path, f"({total_w:.0f} x 256)")


def wordmark_only(out_path):
    ac_d, ac_w, ustad_d, ustad_w = wordmark_paths()
    gap = 18
    total_w = 8 + ac_w + gap + ustad_w + 8
    svg = f'''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {total_w:.0f} 130" width="{total_w:.0f}" height="130" role="img" aria-labelledby="title">
  <title id="title">AC Ustad — wordmark</title>
  <g transform="translate(8 110)" fill="{INK}">
    <path d="{ac_d}"/>
    <path d="{ustad_d}" transform="translate({ac_w + gap:.2f} 0)"/>
  </g>
</svg>
'''
    open(out_path, "w", encoding="utf-8").write(svg)
    print("wrote", out_path, f"({total_w:.0f} x 130)")


if __name__ == "__main__":
    wordmark_only(os.path.join(ROOT, "concepts", "wordmark.svg"))
    for key in ("a-ustad", "b-compass", "c-seal"):
        lockup(os.path.join(ROOT, "concepts", key + ".svg"),
               os.path.join(ROOT, "concepts", key + "-lockup.svg"))
