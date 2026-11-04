#!/usr/bin/env python3
"""Build the final AC Ustad compass logo kit.

All wordmark text is outlined from IBM Plex Sans (SIL OFL) so the files are
font-independent. The compass ring is a stroke-based master; expand it to
outlines before embroidery or vinyl cutting (noted in the guidelines).
"""
import os
import re
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)
FINAL = os.path.join(ROOT, "final")
FONT_DIR = "/home/atif/AC-Ustad/android/app/src/main/res/font"
MEDIUM = os.path.join(FONT_DIR, "ibm_plex_sans_medium.ttf")
BOLD = os.path.join(FONT_DIR, "ibm_plex_sans_bold.ttf")

INK = "#0B1F2A"
BLUE = "#1668A8"
ORANGE = "#E24A1E"
WHITE = "#FFFFFF"
SURFACE = "#EFF6FB"

sys.path.insert(0, HERE)
from wordmark import text_path  # noqa: E402


def clean(d):
    def r(m):
        v = float(m.group())
        s = f"{v:.2f}".rstrip("0").rstrip(".")
        return "0" if s in ("", "-0") else s
    return re.sub(r"-?\d+\.\d+", r, d)


def wordmark_paths():
    ac_d, ac_w = text_path(MEDIUM, "AC", 88, tracking=0)
    ustad_d, ustad_w = text_path(BOLD, "Ustad", 104, tracking=0)
    return clean(ac_d), ac_w, clean(ustad_d), ustad_w


AC_D, AC_W, USTAD_D, USTAD_W = wordmark_paths()
GAP = 18


def compass(ring, needle, tail, dot):
    return f'''<g fill="none" stroke="{ring}" stroke-linecap="round" stroke-linejoin="round">
    <path stroke-width="32" d="M184.6 60.6 A88 88 0 1 0 184.6 195.4"/>
  </g>
  <path fill="{needle}" d="M200 56 L116.69 116.69 L139.31 139.31 Z"/>
  <path fill="{tail}" d="M92 164 L122.34 122.34 L133.66 133.66 Z"/>
  <circle cx="128" cy="128" r="18" fill="{dot}"/>'''


def symbol_svg(out, markup, title):
    svg = f'''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 256 256" width="256" height="256" role="img" aria-labelledby="title">
  <title id="title">{title}</title>
  {markup}
</svg>
'''
    open(out, "w", encoding="utf-8").write(svg)


def horizontal_svg(out, markup, ac_fill, ustad_fill, title):
    word_x = 220
    total_w = word_x + AC_W + GAP + USTAD_W + 24
    svg = f'''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {total_w:.0f} 256" width="{total_w:.0f}" height="256" role="img" aria-labelledby="title">
  <title id="title">{title}</title>
  <g transform="translate(3.69 19.69) scale(0.846)">
    {markup}
  </g>
  <g transform="translate({word_x} 172)">
    <path fill="{ac_fill}" d="{AC_D}"/>
    <path fill="{ustad_fill}" d="{USTAD_D}" transform="translate({AC_W + GAP:.2f} 0)"/>
  </g>
</svg>
'''
    open(out, "w", encoding="utf-8").write(svg)


def stacked_svg(out, markup, ac_fill, ustad_fill, title):
    width = 480
    symbol_scale = 176 / 208
    tx = 152 - 24 * symbol_scale
    ty = 24 - 24 * symbol_scale
    word_w = AC_W + GAP + USTAD_W
    word_x = (width - word_w) / 2
    baseline = 310
    svg = f'''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {width} 340" width="{width}" height="340" role="img" aria-labelledby="title">
  <title id="title">{title}</title>
  <g transform="translate({tx:.2f} {ty:.2f}) scale(0.846)">
    {markup}
  </g>
  <g transform="translate({word_x:.2f} {baseline})">
    <path fill="{ac_fill}" d="{AC_D}"/>
    <path fill="{ustad_fill}" d="{USTAD_D}" transform="translate({AC_W + GAP:.2f} 0)"/>
  </g>
</svg>
'''
    open(out, "w", encoding="utf-8").write(svg)


def wordmark_svg(out, ac_fill, ustad_fill, title):
    total_w = 8 + AC_W + GAP + USTAD_W + 8
    svg = f'''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {total_w:.0f} 130" width="{total_w:.0f}" height="130" role="img" aria-labelledby="title">
  <title id="title">{title}</title>
  <g transform="translate(8 110)">
    <path fill="{ac_fill}" d="{AC_D}"/>
    <path fill="{ustad_fill}" d="{USTAD_D}" transform="translate({AC_W + GAP:.2f} 0)"/>
  </g>
</svg>
'''
    open(out, "w", encoding="utf-8").write(svg)


def app_icon_svg(out, markup, tile, title, rx=0):
    scale = 200 / 208
    tx = 28 - 24 * scale
    ty = 28 - 24 * scale
    svg = f'''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 256 256" width="256" height="256" role="img" aria-labelledby="title">
  <title id="title">{title}</title>
  <rect width="256" height="256" rx="{rx}" fill="{tile}"/>
  <g transform="translate({tx:.2f} {ty:.2f}) scale(0.962)">
    {markup}
  </g>
</svg>
'''
    open(out, "w", encoding="utf-8").write(svg)


def favicon_source(out):
    # Simplified small-size drawing: thicker ring, thicker needle, no tail.
    ring = "176.2 59.2 A84 84 0 1 0 176.2 196.8"
    svg = f'''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 256 256" width="256" height="256" role="img" aria-labelledby="title">
  <title id="title">AC Ustad — favicon source</title>
  <rect width="256" height="256" rx="56" fill="{BLUE}"/>
  <g fill="none" stroke="{WHITE}" stroke-linecap="round" stroke-linejoin="round">
    <path stroke-width="40" d="M{ring}"/>
  </g>
  <path fill="{WHITE}" d="M198 58 L113.86 113.86 L142.14 142.14 Z"/>
  <circle cx="128" cy="128" r="22" fill="{ORANGE}"/>
</svg>
'''
    open(out, "w", encoding="utf-8").write(svg)


if __name__ == "__main__":
    os.makedirs(FINAL, exist_ok=True)

    black = compass(INK, INK, INK, INK)
    blue = compass(BLUE, BLUE, BLUE, BLUE)
    color = compass(BLUE, ORANGE, BLUE, BLUE)
    white = compass(WHITE, WHITE, WHITE, WHITE)

    symbol_svg(os.path.join(FINAL, "ac-ustad-symbol-black.svg"), black, "AC Ustad — symbol, black")
    symbol_svg(os.path.join(FINAL, "ac-ustad-symbol-blue.svg"), blue, "AC Ustad — symbol, blue")
    symbol_svg(os.path.join(FINAL, "ac-ustad-symbol-color.svg"), color, "AC Ustad — symbol, colour")
    symbol_svg(os.path.join(FINAL, "ac-ustad-symbol-white.svg"), white, "AC Ustad — symbol, white")

    horizontal_svg(os.path.join(FINAL, "ac-ustad-horizontal-black.svg"), black, INK, INK, "AC Ustad — horizontal, black")
    horizontal_svg(os.path.join(FINAL, "ac-ustad-horizontal-blue.svg"), blue, BLUE, BLUE, "AC Ustad — horizontal, blue")
    horizontal_svg(os.path.join(FINAL, "ac-ustad-horizontal-color.svg"), color, BLUE, INK, "AC Ustad — horizontal, colour")
    horizontal_svg(os.path.join(FINAL, "ac-ustad-horizontal-white.svg"), white, WHITE, WHITE, "AC Ustad — horizontal, white")

    stacked_svg(os.path.join(FINAL, "ac-ustad-stacked-black.svg"), black, INK, INK, "AC Ustad — stacked, black")
    stacked_svg(os.path.join(FINAL, "ac-ustad-stacked-color.svg"), color, BLUE, INK, "AC Ustad — stacked, colour")
    stacked_svg(os.path.join(FINAL, "ac-ustad-stacked-white.svg"), white, WHITE, WHITE, "AC Ustad — stacked, white")

    wordmark_svg(os.path.join(FINAL, "ac-ustad-wordmark-black.svg"), INK, INK, "AC Ustad — wordmark, black")
    wordmark_svg(os.path.join(FINAL, "ac-ustad-wordmark-blue.svg"), BLUE, BLUE, "AC Ustad — wordmark, blue")
    wordmark_svg(os.path.join(FINAL, "ac-ustad-wordmark-color.svg"), BLUE, INK, "AC Ustad — wordmark, colour")
    wordmark_svg(os.path.join(FINAL, "ac-ustad-wordmark-white.svg"), WHITE, WHITE, "AC Ustad — wordmark, white")

    app_icon_svg(os.path.join(FINAL, "ac-ustad-app-icon.svg"), compass(WHITE, WHITE, WHITE, ORANGE), BLUE, "AC Ustad — app icon")
    app_icon_svg(os.path.join(FINAL, "ac-ustad-app-icon-rounded.svg"), compass(WHITE, WHITE, WHITE, ORANGE), BLUE, "AC Ustad — app icon, rounded", rx=56)
    app_icon_svg(os.path.join(FINAL, "ac-ustad-app-icon-mono.svg"), compass(WHITE, WHITE, WHITE, WHITE), BLUE, "AC Ustad — app icon, mono")
    app_icon_svg(os.path.join(FINAL, "ac-ustad-app-icon-ink.svg"), compass(WHITE, WHITE, WHITE, ORANGE), INK, "AC Ustad — app icon, ink")
    app_icon_svg(os.path.join(FINAL, "ac-ustad-app-icon-surface.svg"), compass(BLUE, ORANGE, BLUE, BLUE), SURFACE, "AC Ustad — app icon, surface", rx=56)

    favicon_source(os.path.join(FINAL, "ac-ustad-favicon-source.svg"))

    print("wrote final masters to", FINAL)
