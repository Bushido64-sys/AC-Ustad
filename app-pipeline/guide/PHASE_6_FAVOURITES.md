# PHASE_6_FAVOURITES.md — the saved list

**Goal: a technician can mark the six codes they keep needing and find them in one tap, on a
phone with no signal.**

`favourites` is the **only writable table** in the app (RULE 5).

---

## 1. Schema (already in the shipped database)

```sql
CREATE TABLE favourites (
  code_id    INTEGER PRIMARY KEY REFERENCES codes(id) ON DELETE CASCADE,
  created_at TEXT NOT NULL
)
```

**That is the whole table.** Two columns. There is no `code_uid`, no copied brand or series
name, no `is_read`, and no title columns — an earlier version of this guide invented all of
them. Write queries that match this schema, or they will throw.

## 2. What `code_id` costs you, and how to pay it

Keying on `code_id` is the schema's decision, not a good one: a data release can renumber
`codes.id`, so a saved row can end up pointing at a **different code**.

Pay for it explicitly, on every database (re)copy:

1. Read every saved `code_id` with its code and brand.
2. Drop rows whose code no longer exists.
3. Anything left is fine — and because `is_read` does not exist, there is nothing else to fix.

Consequences for the UI: a saved row has **no** brand, series or title of its own, so the list
must join back to `codes` (and `series` for the model name) to render. Hold the joined values
in memory in the ViewModel rather than adding columns to a table you do not own. If you later
need the copied columns, add a **second app-owned table in the cache copy** — never `ALTER`
the shipped one.

`created_at` is **TEXT**, not an integer. SQLite only enforces the foreign key if you switch
it on; leave it off so a stale `code_id` is cleaned up rather than throwing.

## 3. Writing

```kotlin
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun save(codeId: Long, createdAt: String)   // 1 row, in a transaction

@Query("DELETE FROM favourites WHERE code_id = :codeId")
suspend fun remove(codeId: Long)

@Query("SELECT code_id, created_at FROM favourites ORDER BY created_at DESC")
fun observeAll(): Flow<List<FavouriteRow>>          // refreshes on every write

/** Every saved code_id, for the staleness sweep in §2. */
@Query("SELECT code_id FROM favourites")
suspend fun allIds(): List<Long>
```

There is **no `is_read` column**, so there is no unread dot and no `markRead`. Drop that idea
unless you add your own table. The row is `(codeId, createdAt)` and nothing more.

- Open the **cache copy read-write** for `favourites` and keep every
  other table untouched by discipline (no `UPDATE` anywhere else, asserted in review).
- Optimistic UI update, rollback on failure with a single `Couldn't save` line.
- Debounce double-taps: ignore a repeat toggle within 400ms.

## 4. The Saved screen

- Third bottom-nav item, 56dp, filled star icon — one of the five permitted icons (RULE 11).
- Row: **code** in Plex Mono 18sp · **series name** 14sp · **brand name** 14sp muted ·
  severity chip on the right, same component as everywhere else. 56dp minimum. Brand, series
  and title come from the join, not from the favourites row.
- Tap → open the detail screen.
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
- [ ] Swipe + Undo restores the row with the same `created_at` order
- [ ] EN/UR toggle switches the saved row's title (it comes from the join, in both languages)
- [ ] Favouriting a code in a brand that later loses that code → the staleness sweep drops
      the row; no crash
- [ ] `SELECT COUNT(*) FROM brands` is still 62 after starring something (proof the app never
      wrote to a read-only table)

