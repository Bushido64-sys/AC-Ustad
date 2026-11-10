# AC Ustad — logo kit

**Chosen direction: The Compass.**
A C-shaped dial with an orange needle pointing to the answer. The C is the C in AC; the needle is the right answer when the signal is gone.

## Start here

| Need | File |
|---|---|
| Read the rules first | `ac-ustad-guidelines.md` |
| Main horizontal logo | `ac-ustad-horizontal-color.svg` / `ac-ustad-horizontal-color-1200.png` |
| Square / social lockup | `ac-ustad-stacked-color.svg` / `ac-ustad-stacked-color-512.png` |
| Symbol only | `ac-ustad-symbol-color.svg` / `ac-ustad-symbol-color-512.png` |
| Play Store / launcher icon | `ac-ustad-app-icon-512.png` (full-bleed) |
| Store feature graphic | `ac-ustad-feature-graphic-1024x500.png` |
| Favicon / web icons | `web/` |
| One-page overview | `ac-ustad-brand-board.png` |
| Brand presentation | `ac-ustad-brand-board.html` (open in a browser) |

## Which version when

- **Horizontal** — website header, store listing, WhatsApp/email signature, documents.
- **Stacked** — square posts, t-shirts, signs, anywhere the horizontal gets too wide.
- **Symbol** — app icon, favicon, avatar, small badges.
- **Wordmark only** — when the compass is already next to it, or in very tight horizontal spaces.
- **White** — on Ustad Blue, Ink, photos and dark UI.
- **Black / blue mono** — one-colour print, forms, invoices, embroidery.

## Colour

| Name | HEX | Use |
|---|---|---|
| Ustad Blue | `#1668A8` | Primary, ring, “AC” |
| Signal Orange | `#E24A1E` | Needle / pivot accent only |
| Ink | `#0B1F2A` | Wordmark and text |
| White | `#FFFFFF` | Reversed mark |
| Surface | `#EFF6FB` | Light background |

Never put the orange needle on the blue tile — it is 1.46 : 1. The app icon uses a white needle with a small orange pivot dot. Full detail in the guidelines.

## Files

```
final/
├── ac-ustad-guidelines.md
├── ac-ustad-brand-board.html / .png
├── ac-ustad-feature-graphic-1024x500.png
├── ac-ustad-horizontal-{color,black,blue,white}.svg
├── ac-ustad-horizontal-*-1200.png
├── ac-ustad-stacked-{color,black,white}.svg
├── ac-ustad-stacked-*-512.png
├── ac-ustad-symbol-{color,black,blue,white}.svg
├── ac-ustad-symbol-*-512.png
├── ac-ustad-wordmark-{color,black,blue,white}.svg
├── ac-ustad-wordmark-*-1200.png
├── ac-ustad-app-icon.svg               full-bleed Play/launcher master
├── ac-ustad-app-icon-{1024,512,192,48}.png
├── ac-ustad-app-icon-{mono,ink,surface}.svg / -512.png
├── ac-ustad-favicon-{16,32,48}.png
└── web/
    ├── favicon.ico / favicon.svg
    ├── favicon-{16,32,48}.png
    ├── apple-touch-icon.png
    ├── icon-{192,512}.png
    ├── maskable-512.png
    ├── site.webmanifest
    └── head-snippet.html
```

## Notes

- The compass ring is a **stroke-based master**. Expand it to outlines before embroidery, vinyl cutting or any RIP that ignores stroke settings.
- The wordmark is outlined **IBM Plex Sans** (SIL Open Font License 1.1). No font is needed to display the logo.
- Master files are SVG. Use PNGs for quick uploads and the SVGs for print/vector work.
- Trademark clearance is not guaranteed; run a professional search before a wide launch.

## Integrated into the app

The surface compass is now live in the Android app:

- **Launcher icon:** `android/app/src/main/res/mipmap-*` (mdpi–xxxhdpi) plus the adaptive icon at `mipmap-anydpi-v26/ic_launcher.xml`.
- **Home header:** 44 dp mark before “AC Ustad” in `HomeScreen.kt`, using `res/drawable/ic_ustad_mark.xml`.
- **Store icon:** `screenshots_1200x1920/icons/icon_512x512.png`.
- **Feature graphic:** `screenshots_1200x1920/icons/feature_graphic_1024x500.png`.
- **Build:** `1.0.2` (`versionCode 6`), latest `build-release` run.

## App identity (for the store listing)

- Package: `com.acustad.app`
- Version: `1.0.2` (`versionCode 6`)
- Target: Android 16 (API 36)
- Play icon: `screenshots_1200x1920/icons/icon_512x512.png`
- Feature graphic: `screenshots_1200x1920/icons/feature_graphic_1024x500.png`
- Privacy: https://bushido64-sys.github.io/AC-Ustad/PRIVACY.html
- Support: atif.support@proton.me
