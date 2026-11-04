# AC Ustad — Logo Guidelines

## 1. The logo

**The idea:** a C-shaped compass with a needle pointing to the answer.
The C is the “C” in AC, the open ring reads as a dial, and the orange needle is the right answer when the signal is gone. It is a field instrument, not a snowflake.

**Versions**
- Primary: horizontal lockup (symbol + “AC Ustad”)
- Stacked lockup (square formats, social)
- Symbol only (app icon, favicon, avatar)
- Wordmark only (when the symbol is already present)
- App icon (blue tile, white compass, orange pivot)

**Files**
- `final/*.svg` — masters for screen and print
- `final/*.png` — ready-to-use raster exports
- `final/web/` — favicon, app icons, web manifest, `<head>` snippet

---

## 2. Clear space

Keep a clear zone of **1 × the pivot-dot diameter** around the logo on all sides.
On the symbol master that is **36 units** (the dot is 36 units across); the zone scales with the artwork, never a fixed distance.

Keep other logos, text and busy image edges outside that zone.

---

## 3. Minimum size

| Version | Screen | Print |
|---|---|---|
| Horizontal | 120 px wide | 30 mm wide |
| Stacked | 96 px wide | 25 mm wide |
| Symbol | 16 px (use `favicon.svg` / app icon) | 6 mm |
| App icon | 48 px | 12 mm |

Below these sizes, use the symbol or the app icon — not the full lockup.

---

## 4. Colour

| Name | HEX | RGB | CMYK | Pantone (nearest) |
|---|---|---|---|---|
| Ustad Blue | `#1668A8` | 22 104 168 | 87 38 0 34 | 2945 C |
| Signal Orange | `#E24A1E` | 226 74 30 | 0 67 87 11 | 172 C |
| Ink | `#0B1F2A` | 11 31 42 | 74 26 0 84 | Black 6 C |
| White | `#FFFFFF` | 255 255 255 | 0 0 0 0 | — |
| Surface | `#EFF6FB` | 239 246 251 | 5 2 0 2 | — (background only) |

Pantone values are nearest matches. Confirm against a physical chip before a spot-colour print run.

**Approved pairs**
- Full colour on white or Surface
- White version on Ustad Blue or Ink
- Ink version on white or Surface
- Blue mono version on white or Surface
- Orange needle only on white or Surface (large graphics, never body text)

**Contrast**
- Blue on white: **5.87 : 1**
- White on blue: **5.87 : 1**
- Ink on white: **16.89 : 1**
- Orange on white: **4.01 : 1** (large graphics only)
- Orange on blue: **1.46 : 1 — never use**

Do not put the orange needle on the blue tile. The app-icon version uses a white needle and keeps the orange dot as a small accent.

---

## 5. Typography

- **Wordmark:** IBM Plex Sans Medium (“AC”) and Bold (“Ustad”), outlined to paths.
- **Licence:** IBM Plex is licensed under the SIL Open Font License 1.1; logo use is permitted.
- **Body text:** IBM Plex Sans. Web fallback: `system-ui, -apple-system, "Segoe UI", Roboto, sans-serif`.
- Use the supplied wordmark files. Do not retype the name and hope the spacing matches.

---

## 6. Do

- Use the horizontal lockup by default; switch to stacked in square or narrow formats.
- Give the mark room — the clear-space rule is the quickest way to make it look professional.
- Use the white version on photos, blue, ink and dark UI.
- Use the app icon for launchers, favicons and social avatars.
- Keep the needle pointing up and to the right; that is the “answer” direction.
- Scale the whole lockup proportionally.

## 7. Don’t

- Don’t stretch, squash, rotate or skew the logo.
- Don’t recolour outside the palette, especially the orange needle on blue.
- Don’t add shadows, outlines, glows, gradients, bevels or 3D effects.
- Don’t rearrange the symbol and wordmark or resize one part independently.
- Don’t put the full horizontal lockup inside the app icon; use the symbol tile.
- Don’t crop the C or the needle, or place the mark over a busy photo without a solid container.
- Don’t use the old snowflake/AC-unit icon; it is retired.
- Don’t recreate the wordmark by typing it in a font.

---

## 8. App icon

- **Primary:** Ustad Blue tile, white compass, Signal Orange pivot dot.
- **Mono:** Ustad Blue tile, all-white mark.
- **Ink:** Ink tile, white mark, orange dot (dark contexts).
- **Surface:** Surface tile, blue/orange mark (light UI).
- Keep the mark inside the central 80 % safe circle; add no text.
- The full-bleed 512 px PNG is the Play Store / launcher master; the rounded version is for web and social.

---

## 9. Backgrounds

| Background | Use |
|---|---|
| White / Surface | Full-colour horizontal or stacked lockup |
| Ustad Blue / Ink | White horizontal or stacked lockup |
| Photo | White version on a calm area, or a solid blue/ink container |
| Busy pattern | Solid container first, logo second |

---

## 10. Handover notes

- The surface app icon is now the Android launcher icon (adaptive + legacy densities) and the 44 dp mark before “AC Ustad” on the home screen.
- The compass ring is a **stroke-based master**; expand it to outlines before embroidery, vinyl cutting or any process that ignores stroke settings.
- The wordmark is already outlined; no font is needed to display it.
- Masters are SVG; export PDF/EPS from a vector editor only if a printer asks.
- Trademark clearance is not guaranteed. Before a wide launch, run a professional search in the relevant classes and a reverse image search.

**Questions / master files:** `atif.support@proton.me` · `brand/logo/final/`
