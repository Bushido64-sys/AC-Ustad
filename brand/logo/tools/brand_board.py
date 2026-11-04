#!/usr/bin/env python3
"""Build a one-page PNG brand board for the AC Ustad compass kit."""
import os
from PIL import Image, ImageDraw, ImageFont

BASE = "/home/atif/AC-Ustad/brand/logo/final"
FONT = "/home/atif/AC-Ustad/android/app/src/main/res/font"
W, H = 1600, 1000
BLUE = "#1668A8"
ORANGE = "#E24A1E"
INK = "#0B1F2A"
SURFACE = "#EFF6FB"
MUTED = "#5A7183"

bold = ImageFont.truetype(os.path.join(FONT, "ibm_plex_sans_bold.ttf"), 72)
semi = ImageFont.truetype(os.path.join(FONT, "ibm_plex_sans_semibold.ttf"), 34)
med = ImageFont.truetype(os.path.join(FONT, "ibm_plex_sans_medium.ttf"), 26)
reg = ImageFont.truetype(os.path.join(FONT, "ibm_plex_sans_regular.ttf"), 24)
small = ImageFont.truetype(os.path.join(FONT, "ibm_plex_sans_regular.ttf"), 20)

img = Image.new("RGB", (W, H), SURFACE)
d = ImageDraw.Draw(img)

# Header
d.text((60, 48), "AC Ustad", font=bold, fill=INK)
d.text((62, 136), "The right answer, even offline.", font=semi, fill=BLUE)
d.text((62, 184), "The Compass — a C-shaped field dial with a needle pointing to the answer.",
       font=reg, fill=MUTED)

# White card with horizontal lockup + app icon
card = (60, 240, 1540, 660)
d.rounded_rectangle(card, radius=32, fill="#FFFFFF")
lockup = Image.open(os.path.join(BASE, "ac-ustad-horizontal-color-1200.png")).convert("RGBA")
lockup.thumbnail((980, 380), Image.LANCZOS)
img.paste(lockup, (100, 280), lockup)

app = Image.open(os.path.join(BASE, "ac-ustad-app-icon-512.png")).convert("RGBA")
app.thumbnail((220, 220), Image.LANCZOS)
img.paste(app, (1240, 340), app)
d.text((1240, 580), "App icon", font=med, fill=MUTED)

# Bottom row: symbol, palette, type
y = 720
d.text((60, y), "Symbol", font=med, fill=MUTED)
sym = Image.open(os.path.join(BASE, "ac-ustad-symbol-color-512.png")).convert("RGBA")
sym.thumbnail((180, 180), Image.LANCZOS)
img.paste(sym, (60, y + 40), sym)

# Palette
px = 340
d.text((px, y), "Palette", font=med, fill=MUTED)
swatches = [
    ("Ustad Blue", BLUE, "#1668A8"),
    ("Signal Orange", ORANGE, "#E24A1E"),
    ("Ink", INK, "#0B1F2A"),
    ("Surface", SURFACE, "#EFF6FB"),
]
for i, (name, color, hexv) in enumerate(swatches):
    x = px + i * 165
    d.rounded_rectangle((x, y + 40, x + 140, y + 140), radius=16, fill=color,
                        outline="#C9DCEA" if color == SURFACE else None)
    d.text((x, y + 152), name, font=small, fill=INK)
    d.text((x, y + 176), hexv, font=small, fill=MUTED)

# Type
tx = 1080
d.text((tx, y), "Typography", font=med, fill=MUTED)
d.text((tx, y + 40), "IBM Plex Sans", font=semi, fill=INK)
d.text((tx, y + 84), "Medium / Bold · SIL OFL 1.1", font=reg, fill=MUTED)
d.text((tx, y + 120), "Wordmark outlined to paths", font=reg, fill=MUTED)

# Footer
d.line((60, 936, 1540, 936), fill="#C9DCEA", width=1)
d.text((60, 952), "The Compass · AC Ustad logo kit · 2026", font=small, fill=MUTED)
d.text((1240, 952), "atif.support@proton.me", font=small, fill=MUTED)

out = os.path.join(BASE, "ac-ustad-brand-board.png")
img.save(out, "PNG")
print("wrote", out, img.size)
