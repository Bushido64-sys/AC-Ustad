#!/usr/bin/env python3
"""Export the final AC Ustad PNGs at tight aspect ratios."""
import os
import cairosvg

BASE = "/home/atif/AC-Ustad/brand/logo/final"

JOBS = [
    ("ac-ustad-symbol-color.svg", "ac-ustad-symbol-color-512.png", 512),
    ("ac-ustad-symbol-color.svg", "ac-ustad-symbol-color-192.png", 192),
    ("ac-ustad-symbol-color.svg", "ac-ustad-symbol-color-48.png", 48),
    ("ac-ustad-symbol-black.svg", "ac-ustad-symbol-black-512.png", 512),
    ("ac-ustad-symbol-blue.svg", "ac-ustad-symbol-blue-512.png", 512),
    ("ac-ustad-symbol-white.svg", "ac-ustad-symbol-white-512.png", 512),
    ("ac-ustad-horizontal-color.svg", "ac-ustad-horizontal-color-1200.png", 1200),
    ("ac-ustad-horizontal-color.svg", "ac-ustad-horizontal-color-600.png", 600),
    ("ac-ustad-horizontal-black.svg", "ac-ustad-horizontal-black-1200.png", 1200),
    ("ac-ustad-horizontal-blue.svg", "ac-ustad-horizontal-blue-1200.png", 1200),
    ("ac-ustad-horizontal-white.svg", "ac-ustad-horizontal-white-1200.png", 1200),
    ("ac-ustad-stacked-color.svg", "ac-ustad-stacked-color-512.png", 512),
    ("ac-ustad-stacked-black.svg", "ac-ustad-stacked-black-512.png", 512),
    ("ac-ustad-stacked-white.svg", "ac-ustad-stacked-white-512.png", 512),
    ("ac-ustad-wordmark-color.svg", "ac-ustad-wordmark-color-1200.png", 1200),
    ("ac-ustad-wordmark-black.svg", "ac-ustad-wordmark-black-1200.png", 1200),
    ("ac-ustad-wordmark-white.svg", "ac-ustad-wordmark-white-1200.png", 1200),
    ("ac-ustad-app-icon.svg", "ac-ustad-app-icon-1024.png", 1024),
    ("ac-ustad-app-icon.svg", "ac-ustad-app-icon-512.png", 512),
    ("ac-ustad-app-icon.svg", "ac-ustad-app-icon-192.png", 192),
    ("ac-ustad-app-icon.svg", "ac-ustad-app-icon-48.png", 48),
    ("ac-ustad-app-icon-mono.svg", "ac-ustad-app-icon-mono-512.png", 512),
    ("ac-ustad-app-icon-ink.svg", "ac-ustad-app-icon-ink-512.png", 512),
    ("ac-ustad-app-icon-surface.svg", "ac-ustad-app-icon-surface-512.png", 512),
    ("ac-ustad-favicon-source.svg", "ac-ustad-favicon-16.png", 16),
    ("ac-ustad-favicon-source.svg", "ac-ustad-favicon-32.png", 32),
    ("ac-ustad-favicon-source.svg", "ac-ustad-favicon-48.png", 48),
]

if __name__ == "__main__":
    for src, dst, width in JOBS:
        cairosvg.svg2png(url=os.path.join(BASE, src), write_to=os.path.join(BASE, dst),
                         output_width=width)
        print("wrote", dst)
