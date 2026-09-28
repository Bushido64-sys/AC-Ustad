# EcoStar — sources

Brand id: `ecostar` · Category: `ac` · Researched: 2026-09-23
Official site: https://ecostar.com.pk · Support: 042-111-111-97 / 033-171-113-97, info@dwphome.pk (DWP Home after-sales)

**Status: one written third-party table (Bijli Bazar) + one official non-fault note (EL); no official fault-table manual.**

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Bijli Bazar — Ecostar AC Error Code List 2026 (Lahore) | retailer_page | https://www.bijlibazar.com/ecostar-ac-error-code-list-2026 | **Only full written EcoStar list:** E1–E6, E8, F0–F3, H3, H5, C5, C6, L3, L9, P4 + reset/prevention advice. Page tags also imply H1, H2, Lc, P1–P3, Uc (no meanings) | 2026-09-23 |
| 2 | EcoStar official Facebook — "Seeing EL on your EcoStar AC display…" | official_support | https://www.facebook.com/EcoStar/posts/1436546078506605 | **EL = Energy Limit/Power-limit mode, NOT a fault** (official); disable via Power Limit Mode off + restart, or AMP.C ×8 | 2026-09-23 |
| 3 | EcoStar official YouTube @EcostarPk — Learn How to Troubleshoot Error Codes | official_support | https://www.youtube.com/watch?v=JK3GDeQIKRU | Official troubleshooting video exists (chapters: sensor/motor/driving protection/communication) — individual code values **JS-gated, not extractable** | 2026-09-23 |
| 4 | YouTube — EcoStar AC Mini Split E5 Error Code | video | https://www.youtube.com/watch?v=D1FGjKKIEMg | E5 = indoor↔outdoor communication; reset 10 min — corroborates Bijli Bazar table over its own detail section | 2026-09-23 |
| 5 | Facebook consumer group — Ecostar after-sales / E5 complaint | forum | https://www.facebook.com/groups/voiceofcustomerpk/posts/2236168010301876 | User report: E5 "normally the outdoor kit issue" — third vote for E5 as comms/outdoor-kit (not ODU fan alone) | 2026-09-23 |
| 6 | YouTube @expert.errorcodes — Ecostar Crown AC F1 Error code (2022) | video | https://www.youtube.com/watch?v=WXcIV28oI38 | Occurrence of F1 on the **Crown** line (model-line hint; meaning not stated) | 2026-09-23 |
| 7 | AYS Online — EcoStar AC remote guide (error-page absent) | retailer_page | https://www.aysonline.pk/docs/ecostar-ac-remote-guide | Negative evidence: AYS documents Orient/Gree/Kenwood/Dawlance codes but **no EcoStar error page** | 2026-09-23 |
| 8 | Gree U-Match service manual + Rfwel/Eurocool/AC Pro Gree lists | service_manual | https://www.greeac.co.nz/storage/Downloads/UMatch/U-MatchNZ-R32%20SM%202019%20Trouble%20Shooting%20(pages%2051-86).pdf | **Cross-reference only** — Gree meanings for conflict analysis (H3/H5/C5/E6/E8 agree-ish; E1/E2/E4/E5 diverge). NOT imported as EcoStar data | 2026-09-23 |
| 9 | Excluded name collisions: Cooper & Hunter ECOSTAR manual; Hayward EcoStar pool pump | other | citrus.world / es.hayward.com | Wrong brand/product — logged to prevent contamination | 2026-09-23 |

## Conflicts log (resolved in data notes)

| Code | Conflict | Resolution used |
|---|---|---|
| **E5** | Bijli Bazar **table** = communication I/O; Bijli Bazar **detail section** = outdoor fan motor; YouTube = communication; FB group = "outdoor kit" | **Communication wins (2 of 3 within-source + 2 external)** → E5 entry primary = comms, ODU fan kept as alternate cause. Gree platform says E5=overcurrent — **not imported** |
| **F1** | Bijli Bazar table groups F1 with outdoor sensors; its detail says sensor–PCB communication; Gree says F1=evaporator (indoor) sensor; Crown video occurrence only | **Lowest confidence among F-codes but table detail (sensor–board comms) used**; Gree meaning in notes as trap warning; Crown video in notes as model hint |
| **H3** | Bijli Bazar table groups H3/H5 as IPM/module; detail = compressor overload | **Detail = compressor overload** (matches Gree H3); H5 stays IPM — Gree-consistent split |
| **L9** | Bijli Bazar = power/low-voltage; Gree sources themselves disagree (refrigerant-gathering vs power) | **EcoStar's own table wins** (power); Gree disagreement noted in entry notes |
| **E1/E2/E4** | EcoStar meanings (sensor/fan) contradict Gree E1/E2/E4 (pressure/temp) | Evidence EcoStar uses a **different mapping than Gree** → Gree tables never imported; warning in every affected entry + series notes |
| **EL as fault** | Display "EL" looks like an error code | **Official FB: mode, not fault** → severity `info`, `isFault: false`, confidence high |
| Tag-only H1/H2/Lc/P1/P2/P3/Uc | Page tags imply existence; no meanings anywhere | Included confidence **low**, meaning unpublished, verify badge; Gree meanings logged as hints only |
| Name collisions | Cooper & Hunter ECOSTAR manual; Hayward EcoStar pump | Excluded entirely |

## Exhaustiveness check (Protocol Phase D)

- ~26 searches (English + Urdu, site: on ecostar/aysonline/fully4world/hamariweb/daraz/electrafix/japanelectronics/Scribd, TikTok/FB, video IDs, Gree OEM cross-ref).
- ~18 returned no new EcoStar-AC code; **longest consecutive fruitless streak = 4** at the end (L1/UC → Urdu → site: sweeps → Daraz) — **Phase D stop condition met**.
- Codes covered: **26 entries** = 1 high (EL official) + 18 medium (Bijli Bazar table: E1–E6, E8, F0–F3, H3, H5, C5, C6, L3, L9, P4) + 7 low (tag-only H1/H2/Lc/P1–P3/Uc).
- Negative confirmations: aysonline.pk, fully4world.com, hamariweb.com, daraz.pk, ecostar.com.pk, japanelectronics, electrafix, Scribd — none publish an EcoStar AC fault table.
- Known remaining gap: official EcoStar troubleshooting video (S3) code values not machine-readable — if transcript/description obtained, re-run Phase C and upgrade table confidence; official manual still absent; F2/F3/E6/C6/L3/L9 lack dedicated prose in S1 (table row only).
- Note for future sessions: Cooper & Hayward "EcoStar" hits are wrong-brand; Gree tables are cross-reference only.
