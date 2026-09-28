# PHASE_6_FAVOURITES.md — the saved list

**Goal: a technician can mark the six codes they keep needing and find them in one tap, on a
phone with no signal.**

`favourites` is the **only writable table** in the app (RULE 5).

---

## 1. Schema (already in the shipped database)

| column | type | purpose |
|---|---|---|
| `code_uid` | TEXT PRIMARY KEY | e.g. `growatt/growatt-mod-tl3x/Error 200` — the **stable** identity, never `code_id` |
| `brand_name` | TEXT | copied so the list needs no query |
| `series_name` | TEXT | copied |
| `code` | TEXT | copied |
| `title_en` | TEXT | copied, for the EN/UR toggle |
| `title_ur` | TEXT | copied, for the EN/UR toggle |
| `is_read` | INTEGER | 0 = unread dot |
| `created_at` | INTEGER | unix seconds |

**Why `code_uid` and not `code_id`:** a new app version ships a new database and ids can
shift. Keying on the uid means a saved list survives every future update. Keying on `code_id`
would silently lose favourites on the first data release.

## 2. Why the row copies brand, series, code and titles

So the Saved list renders from one query with no joins, and so it can never break. The trade
is that a data fix to a title will not retroactively appear in an old saved row — acceptable,
and much cheaper than a migration. Compare against the database's `meta.db_version` if you ever
want to refresh stale copies.

## 3. Writing

```kotlin
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun upsite(fav: Favourite)          // 1 row, in a transaction

@Query("DELETE FROM favourites WHERE code_uid = :uid")
suspend fun remove(uid: String)

@Query("SELECT * FROM favourites ORDER BY created_at DESC")
fun observeAll(): Flow<List<Favourite>>     // refreshes on every write

@Query("UPDATE favourites SET is_read = 1 WHERE code_uid = :uid")
suspend fun markRead(uid: String)
```

- Open the same read-only copy for reads. Do not open a second writable handle to a
  read-only-opened file — open the **cache copy read-write** for `favourites` and keep every
  other table untouched by discipline (no `UPDATE` anywhere else, asserted in review).
- Optimistic UI update, rollback on failure with a single `Couldn't save` line.
- Debounce double-taps: ignore a repeat toggle within 400ms.

## 4. The Saved screen

- Third bottom-nav item, 56dp, filled star icon — one of the five permitted icons (RULE 11).
- Row: **code** in Plex Mono 18sp · **series name** 14sp · **brand name** 14sp muted ·
  unread dot (8dp, `#1668A8`) when `is_read = 0` · severity chip on the right, same component
  as everywhere else. 56dp minimum.
- Tap → open the detail screen and mark read.
- Swipe to remove, with a `Snackbar` + **Undo** that re-inserts. Standard, expected, not
  decorative.
- Newest first. No grouping, no headers, no folders. It is a list of 6 things, not a library.

## 5. Bottom navigation (3 items)

| Item | Icon | Screen |
|---|---|---|
| Browse | none (label only, or a plain chevron) | categories → brands → models |
| Saved | star (outline / filled when non-empty) | favourites |
| Settings | none | language, theme, version, sources |

- 3 items, label always visible, 56dp tall, 2dp top hairline, canvas background.
- Selected item: label + indicator in `#1668A8`, **4dp indicator** (not a pill), plus the hard
  `3dp 3dp 0` shadow — the selected state is one of only two places a shadow is allowed
  (RULE 9). Unselected: ink, no icon fill, no shadow.
- Never a floating action button. A star in the app bar is the only write affordance.

## 6. Empty state

> **Nothing saved yet**
> Tap the star on any code to keep it here.

One sentence, no illustration, no emoji. This is the most-seen screen in the app for a new
user.

## 7. Privacy

The saved list never leaves the phone: no sync, no account, no export, no share intent. If a
user clears app data or uninstalls, it is gone — which is honest and must be stated nowhere
loudly, because there is nothing to recover.

## 8. Checks

- [ ] Star → row appears instantly; unstar → disappears
- [ ] Force-stop and relaunch → still saved
- [ ] Clear app data → gone, no error
- [ ] Re-copy a new database over the old one → still saved
- [ ] Unread dot clears on open, survives relaunch
- [ ] Swipe + Undo restores the row with the same `created_at` order
- [ ] EN/UR toggle switches the saved row's title (both languages were copied)
- [ ] Favouriting a code in a brand that later loses that code → the row still opens, with
      whatever the new database has; no crash
- [ ] `SELECT COUNT(*) FROM brands` is still 62 — prove the app never wrote to read-only tables
