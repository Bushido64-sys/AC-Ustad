#!/usr/bin/env python3
"""Generate the Android launcher icons and the home-header vector from the final kit.

The launcher uses the surface app icon (light tile, blue/orange compass). The
adaptive icon uses the same surface background with the compass foreground.
"""
import os
from PIL import Image, ImageDraw
import cairosvg

ROOT = "/home/atif/AC-Ustad"
FINAL = os.path.join(ROOT, "brand/logo/final")
RES = os.path.join(ROOT, "android/app/src/main/res")
SURFACE_SVG = os.path.join(FINAL, "ac-ustad-app-icon-surface.svg")
SYMBOL_SVG = os.path.join(FINAL, "ac-ustad-symbol-color.svg")

DENSITIES = {
    "mdpi": 48,
    "hdpi": 72,
    "xhdpi": 96,
    "xxhdpi": 144,
    "xxxhdpi": 192,
}

HOME_VECTOR = '''<?xml version="1.0" encoding="utf-8"?>
<!-- AC Ustad home mark: the surface app icon as a vector. -->
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="48dp"
    android:height="48dp"
    android:viewportWidth="256"
    android:viewportHeight="256">
    <path
        android:fillColor="#EFF6FB"
        android:pathData="M56,0 H200 A56,56 0 0,1 256,56 V200 A56,56 0 0,1 200,256 H56 A56,56 0 0,1 0,200 V56 A56,56 0 0,1 56,0 Z" />
    <group
        android:scaleX="0.962"
        android:scaleY="0.962"
        android:translateX="4.92"
        android:translateY="4.92">
        <path
            android:fillColor="@android:color/transparent"
            android:strokeColor="#1668A8"
            android:strokeWidth="32"
            android:strokeLineCap="round"
            android:strokeLineJoin="round"
            android:pathData="M184.6,60.6 A88,88 0 1,0 184.6,195.4" />
        <path
            android:fillColor="#E24A1E"
            android:pathData="M200,56 L116.69,116.69 L139.31,139.31 Z" />
        <path
            android:fillColor="#1668A8"
            android:pathData="M92,164 L122.34,122.34 L133.66,133.66 Z" />
        <path
            android:fillColor="#1668A8"
            android:pathData="M128,110 A18,18 0 1,0 128,146 A18,18 0 1,0 128,110 Z" />
    </group>
</vector>
'''

FOREGROUND_VECTOR = '''<?xml version="1.0" encoding="utf-8"?>
<!-- Adaptive-icon foreground: the compass, centred inside the 66dp safe zone. -->
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="256"
    android:viewportHeight="256">
    <group
        android:scaleX="0.6"
        android:scaleY="0.6"
        android:translateX="51.2"
        android:translateY="51.2">
        <path
            android:fillColor="@android:color/transparent"
            android:strokeColor="#1668A8"
            android:strokeWidth="32"
            android:strokeLineCap="round"
            android:strokeLineJoin="round"
            android:pathData="M184.6,60.6 A88,88 0 1,0 184.6,195.4" />
        <path
            android:fillColor="#E24A1E"
            android:pathData="M200,56 L116.69,116.69 L139.31,139.31 Z" />
        <path
            android:fillColor="#1668A8"
            android:pathData="M92,164 L122.34,122.34 L133.66,133.66 Z" />
        <path
            android:fillColor="#1668A8"
            android:pathData="M128,110 A18,18 0 1,0 128,146 A18,18 0 1,0 128,110 Z" />
    </group>
</vector>
'''

ADAPTIVE = '''<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/surface" />
    <foreground android:drawable="@drawable/ic_launcher_foreground" />
</adaptive-icon>
'''


def square_icon(size, out):
    cairosvg.svg2png(url=SURFACE_SVG, write_to=out, output_width=size, output_height=size)
    print("wrote", out)


def round_icon(size, out):
    tmp = out + ".tmp.png"
    cairosvg.svg2png(url=SYMBOL_SVG, write_to=tmp, output_width=size, output_height=size)
    canvas = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    d = ImageDraw.Draw(canvas)
    d.ellipse((0, 0, size - 1, size - 1), fill="#EFF6FB")
    mark = Image.open(tmp).convert("RGBA")
    canvas.alpha_composite(mark, (0, 0))
    canvas.save(out, "PNG")
    os.remove(tmp)
    print("wrote", out)


if __name__ == "__main__":
    drawable = os.path.join(RES, "drawable")
    anydpi = os.path.join(RES, "mipmap-anydpi-v26")
    os.makedirs(drawable, exist_ok=True)
    os.makedirs(anydpi, exist_ok=True)

    open(os.path.join(drawable, "ic_ustad_mark.xml"), "w", encoding="utf-8").write(HOME_VECTOR)
    open(os.path.join(drawable, "ic_launcher_foreground.xml"), "w", encoding="utf-8").write(FOREGROUND_VECTOR)
    open(os.path.join(anydpi, "ic_launcher.xml"), "w", encoding="utf-8").write(ADAPTIVE)
    open(os.path.join(anydpi, "ic_launcher_round.xml"), "w", encoding="utf-8").write(ADAPTIVE)
    print("wrote drawable + adaptive XML")

    for density, size in DENSITIES.items():
        folder = os.path.join(RES, f"mipmap-{density}")
        os.makedirs(folder, exist_ok=True)
        square_icon(size, os.path.join(folder, "ic_launcher.png"))
        round_icon(size, os.path.join(folder, "ic_launcher_round.png"))
