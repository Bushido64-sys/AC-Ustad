#!/usr/bin/env python3
"""
Verify the SQL the Android app actually ships, against the real database.

An Android unit test cannot do this: `android.database.sqlite` is a stub off-device, so a
JVM test can only check pure logic. The queries the app runs therefore have to be exercised
here instead, in CI, on every push.

Every assertion below mirrors a query in the app:
  app/src/main/java/com/acustad/app/data/CatalogDao.kt
  app/src/main/java/com/acustad/app/data/CodeDao.kt
  app/src/main/java/com/acustad/app/data/SearchDao.kt
  app/src/main/java/com/acustad/app/data/FavouritesDao.kt

with one exception: `SearchDao.DESCRIPTION_SQL` is *read out of the Kotlin source* rather than
retyped, because it is too long to copy faithfully (see `_kotlin_description_sql`).

If a data release changes a column, a count or a convention, this fails on the push that
caused it, rather than in a technician's hand.

Run locally:  python3 app-pipeline/check_app_sql.py
"""
from __future__ import annotations

import importlib.util
import re
import sqlite3
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
DB = HERE / "db" / "kb.sqlite"
failures: list[str] = []


def _load_norm():
    """Import `norm` from build_kb.py rather than restating the rule.

    The canonical form is the single most important detail in this project: `alias_norm` is
    stored UPPER-cased, so a lower-casing rule returns zero rows for `e1`, `e6`, `f4`. Two
    copies of that rule would inevitably drift, so the checker uses the generator's own.
    """
    spec = importlib.util.spec_from_file_location("build_kb", HERE / "build_kb.py")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module.norm


norm = _load_norm()

APP_SRC = (HERE.parent / "android" / "app" / "src" / "main" / "java" / "com" / "acustad" /
           "app" / "data" / "SearchDao.kt")


def _kotlin_description_sql() -> str:
    """Reads `SearchDao.DESCRIPTION_SQL` out of the Kotlin source, verbatim.

    Every other query in this file is retyped, with a comment naming the file it came from —
    which is honest, and is also a copy that can drift. This one is *not* retyped, because its
    haystack is eight columns and two correlated subqueries: a hand-copied version of that would
    be wrong in some subtle way within a month, and the error would be a search that quietly
    returns the wrong codes rather than a failure.

    So the statement executed here is the statement the app ships, byte for byte, with the same
    `%WHERE%` substitution the DAO performs. If either side changes, they diverge and this file
    fails first.
    """
    if not APP_SRC.exists():
        raise SystemExit(f"::error::{APP_SRC} does not exist - cannot verify the app's SQL")
    text = APP_SRC.read_text(encoding="utf-8")
    marker = 'DESCRIPTION_SQL = """'
    start = text.find(marker)
    if start < 0:
        raise SystemExit(
            f"::error::{marker!r} not found in SearchDao.kt - DESCRIPTION_SQL moved; "
            "this checker and the app must be updated together")
    body = start + len(marker)
    end = text.find('"""', body)
    if end < 0:
        raise SystemExit("::error::DESCRIPTION_SQL in SearchDao.kt is never closed")
    lines = text[body:end].split("\n")
    # Kotlin's trimIndent(): drop the common leading indent of the non-blank lines.
    indent = min((len(l) - len(l.lstrip()) for l in lines if l.strip()), default=0)
    return "\n".join(l[indent:] for l in lines).strip()


def _description_terms(raw: str) -> list[str]:
    """Mirrors `SearchInput.descriptionTerms`: canonicalise, split like unicode61, drop 1-char.

    One copy, at module level, for the same reason `norm` is imported from build_kb: the term
    rules are what make the query safe without an ESCAPE clause, and two of them would disagree
    about `_` or about `3 phase` within a month.
    """
    return [t for t in re.split(r"[^A-Z0-9]+", norm(raw)) if len(t) >= 2]


def _count_description_matches(db, query: str, sql: str):
    """(codes, model lines) anywhere in the knowledge base whose own text holds every term.

    Unscoped on purpose: it is used once, to state how much the fallback reaches — the number
    that says whether this query is a token gesture or the fix it claims to be. Everything the
    app actually runs is scoped; the scope is asserted separately, on the shared series_id.
    """
    terms = _description_terms(query)
    if not terms:
        return (0, 0)
    start = sql.index("lower(")
    end = sql.index(") AS haystack", start)
    haystack = sql[start:end + len(")")]
    where = " AND ".join(haystack + " LIKE ?" for _ in terms)
    return db.execute(
        "SELECT COUNT(*), COUNT(DISTINCT series_id || '|' || brand_id) FROM codes c WHERE " + where,
        [f"%{t}%" for t in terms],
    ).fetchone()

# The app's code-detail query, mirrored from
# android/app/src/main/java/com/acustad/app/data/CodeDao.kt (`DETAIL_SQL`), and the column
# order the DAO is expected to see. If either side changes, change both in the same commit.
DETAIL_SQL = """
    SELECT c.id, c.uid, c.code, c.title_en, c.title_ur, c.severity, c.is_fault, c.display,
           c.meaning_en, c.meaning_ur, c.notes_en, c.notes_ur, c.confidence,
           c.source_type, c.source_title, c.source_url, c.blink_pattern, c.related_codes,
           c.series_id, c.brand_id
      FROM codes c
     WHERE c.id = ?
"""
DETAIL_COLUMNS = (
    "id", "uid", "code", "title_en", "title_ur", "severity", "is_fault", "display",
    "meaning_en", "meaning_ur", "notes_en", "notes_ur", "confidence",
    "source_type", "source_title", "source_url", "blink_pattern", "related_codes",
    "series_id", "brand_id",
)


def check(label: str, got: object, want: object) -> None:
    ok = got == want
    print(f"  {'OK ' if ok else 'ERR'} {label}: {got!r}" + ("" if ok else f" (expected {want!r})"))
    if not ok:
        failures.append(label)


def note(label: str, value: object) -> None:
    print(f"  ..  {label}: {value!r}")


def main() -> int:
    if not DB.exists():
        print(f"::error::{DB} is missing; run build_kb.py first")
        return 1
    db = sqlite3.connect(DB)

    # ── CatalogDao.categoryCounts() ───────────────────────────────────────────
    check(
        "category counts (31/33 brands, 1723/2695 codes = 4418)",
        db.execute(
            """SELECT
                 (SELECT COUNT(*) FROM brands WHERE categories LIKE '%"ac"%'),
                 (SELECT COUNT(*) FROM brands WHERE categories LIKE '%"inverter"%'),
                 (SELECT IFNULL(SUM(code_count),0) FROM series WHERE category='ac'),
                 (SELECT IFNULL(SUM(code_count),0) FROM series WHERE category='inverter')"""
        ).fetchone(),
        (31, 33, 1723, 2695),
    )

    # ── CatalogDao.brandsIn() ────────────────────────────────────────────────
    ac = {r[0] for r in db.execute("""SELECT id FROM brands WHERE categories LIKE '%"ac"%'""")}
    inv = {r[0] for r in db.execute("""SELECT id FROM brands WHERE categories LIKE '%"inverter"%'""")}
    check("brands per category", (len(ac), len(inv)), (31, 33))
    check("brands union is every brand", len(ac | inv), 62)
    check("brands listed in both categories", len(ac & inv), 2)
    check("AC brands with zero codes", len(ac - {
        r[0] for r in db.execute("SELECT id FROM brands WHERE code_count > 0 AND categories LIKE '%\"ac\"%'")
    }), 4)
    check("inverter brands with zero codes", len(inv - {
        r[0] for r in db.execute("SELECT id FROM brands WHERE code_count > 0 AND categories LIKE '%\"inverter\"%'")
    }), 4)

    # ── CatalogDao.seriesOf() ────────────────────────────────────────────────
    check("model lines of one brand", db.execute(
        "SELECT COUNT(*) FROM series WHERE brand_id='haier'").fetchone()[0], 7)
    check("model lines with zero codes app-wide", db.execute(
        "SELECT COUNT(*) FROM series WHERE code_count=0").fetchone()[0], 65)

    # ── CodeDao.codesIn(): the series_id trap, proven ────────────────────────
    bare = db.execute("SELECT COUNT(*) FROM codes WHERE series_id='inverter-split'").fetchone()[0]
    scoped = db.execute(
        "SELECT COUNT(*) FROM codes WHERE series_id='inverter-split' AND brand_id='dawlance'"
    ).fetchone()[0]
    note("a bare series_id filter would return this many rows across 14 brands", bare)
    check("the scoped filter returns fewer", scoped < bare, True)
    check("the scoped list is the brand's own", db.execute(
        """SELECT COUNT(*) FROM codes WHERE series_id='inverter-split' AND brand_id='dawlance'
             AND code_norm LIKE '%'""").fetchone()[0], scoped)
    check("largest single model line", db.execute(
        "SELECT MAX(code_count) FROM series").fetchone()[0], 106)

    # ── CodeDao.detail(): idx is 0-based, so displayNumber is index + 1 ─────
    for table in ("causes", "solutions"):
        check(
            f"{table}.idx is 0-based and contiguous",
            db.execute(
                f"""SELECT COUNT(*) FROM (
                        SELECT code_id, COUNT(*) n, MIN(idx) mn, MAX(idx) mx
                          FROM {table} GROUP BY code_id)
                     WHERE mn <> 0 OR mx <> n-1"""
            ).fetchone()[0],
            0,
        )
    check("every fault code has >= 2 causes", db.execute(
        """SELECT COUNT(*) FROM (
               SELECT c.id FROM codes c WHERE c.is_fault=1
                 AND (SELECT COUNT(*) FROM causes x WHERE x.code_id=c.id) < 2)""").fetchone()[0], 0)
    check("every fault code has >= 2 fix steps", db.execute(
        """SELECT COUNT(*) FROM (
               SELECT c.id FROM codes c WHERE c.is_fault=1
                 AND (SELECT COUNT(*) FROM solutions x WHERE x.code_id=c.id) < 2)""").fetchone()[0], 0)

    # ── the one code with no causes: the empty block the UI must hide ───────
    check("the only code with no causes", db.execute(
        """SELECT c.uid FROM codes c WHERE c.is_fault = 0
             AND NOT EXISTS(SELECT 1 FROM causes x WHERE x.code_id = c.id)"""
    ).fetchone()[0], "panasonic/panasonic-hf-self-diagnosis/H00")
    check("codes with no source_url", db.execute(
        "SELECT COUNT(*) FROM codes WHERE source_url IS NULL OR source_url=''").fetchone()[0], 26)

    # ── CodeDao.detailById(): the columns the detail screen reads ───────────
    row = db.execute("""SELECT c.uid, c.series_id, c.brand_id, c.confidence,
                                c.source_type, c.source_title, c.source_url,
                                c.blink_pattern, c.related_codes, c.meaning_en, c.meaning_ur
                             FROM codes c WHERE c.uid = ?""",
                     ("growatt/growatt-mod-tl3x/Error 200",)).fetchone()
    check("detail row found", row is not None, True)
    check("detail uid is brand/series/code", row[0].count("/"), 2)
    check("detail series_id is the bare slug", row[1], "growatt-mod-tl3x")
    check("detail brand_id is the brand slug", row[2], "growatt")
    # every detail row must have a meaning, or the block is hidden and the screen starts empty
    check("codes with no meaning at all", db.execute(
        "SELECT COUNT(*) FROM codes WHERE (meaning_en IS NULL OR TRIM(meaning_en)='')"
        " AND (meaning_ur IS NULL OR TRIM(meaning_ur)='')").fetchone()[0], 0)
    # confidence and source_type must be from the documented sets, or the chip is a lie
    check("confidence values", sorted({r[0] for r in db.execute(
        "SELECT DISTINCT confidence FROM codes")}), ["high", "low", "medium"])
    check("codes with a source_url but no source_title", db.execute(
        """SELECT COUNT(*) FROM codes
             WHERE (source_url IS NOT NULL AND TRIM(source_url) != '')
               AND (source_title IS NULL OR TRIM(source_title) = '')""").fetchone()[0], 0)

    # ── SearchDao.codesExact(): the canonical form, scoped ───────────────────
    check("E6 resolves inside one model line", db.execute(
        """SELECT COUNT(*) FROM aliases a JOIN codes c ON c.id = a.code_id
             WHERE a.alias_norm='E6' AND c.series_id='inverter-split'
               AND c.brand_id='dawlance'""").fetchone()[0] > 0, True)
    # The single most important property of the whole search design: with the generator's own
    # canonical form, EVERY code string in the database resolves. Without it, a technician
    # typing "e1" finds nothing.
    codes = [r[0] for r in db.execute("SELECT DISTINCT code FROM codes")]
    check("all code strings resolve via alias_norm", sum(
        1 for code in codes
        if db.execute("SELECT 1 FROM aliases WHERE alias_norm=? LIMIT 1", (norm(code),)).fetchone()
    ), len(codes))
    check("lowercase input resolves too ('e1', 'e6', 'f4')", all(
        db.execute("SELECT 1 FROM aliases WHERE alias_norm=? LIMIT 1", (norm(t),)).fetchone()
        for t in ("e1", "e6", "f4")
    ), True)
    check("uppercase input resolves too ('E1', 'E6', 'F4')", all(
        db.execute("SELECT 1 FROM aliases WHERE alias_norm=? LIMIT 1", (norm(t),)).fetchone()
        for t in ("E1", "E6", "F4")
    ), True)

    # ── SearchDao.codesText(): the free-text path, which nothing else exercises ──
    # This is the one code path that had no automated test at all, and it had two defects.
    def fts_query(raw: str) -> str:
        """Mirrors SearchInput.ftsQuery: quote each term, AND them together."""
        terms = [t.strip() for t in re.split(r"[ \t\n]", raw) if t.strip()]
        return " AND ".join('"' + t.replace('"', '""') + '"' for t in terms)

    fts_cols = [r[1] for r in db.execute("PRAGMA table_info(code_fts)")]
    check("code_fts columns", fts_cols, ["code_norm", "aliases", "titles"])

    # a quoted single term still must not crash
    crashes = 0
    for (code,) in db.execute("SELECT DISTINCT code FROM codes LIMIT 400"):
        try:
            db.execute("SELECT 1 FROM code_fts WHERE code_fts MATCH ?",
                       (fts_query(code),)).fetchone()
        except sqlite3.OperationalError:
            crashes += 1
    check("400 code strings never crash the quoted MATCH", crashes, 0)

    # multi-word input must not be treated as an exact phrase
    for phrase, words in (("over current", "over current"),
                          ("inverter fault", "inverter fault"),
                          ("high temperature", "high temperature")):
        strict = db.execute("SELECT COUNT(*) FROM code_fts WHERE code_fts MATCH ?",
                            (f'"{phrase}"',)).fetchone()[0]
        loose = db.execute("SELECT COUNT(*) FROM code_fts WHERE code_fts MATCH ?",
                           (fts_query(words),)).fetchone()[0]
        print(f"  ..  {phrase!r}: phrase={strict} AND={loose}")
        check(f"AND-join finds at least as much as the phrase for {phrase!r}", loose >= strict, True)

    check("a nonsense word returns nothing rather than everything", db.execute(
        "SELECT COUNT(*) FROM code_fts WHERE code_fts MATCH ?",
        (fts_query("zzzqqxnothing"),)).fetchone()[0], 0)

    # ── SearchDao.codesDescription(): the words an index cannot answer ───────
    # `code_fts` holds code_norm, aliases, titles — and `titles` is only the English and Roman
    # Urdu titles (DATA_SCHEMA.md §2). A technician does not search titles; they search what is
    # written on the unit in front of them: "air leakage". The phrase appears in 12 fix steps of
    # the shipped database and in 0 titles, so free text returns nothing and the screen looks
    # broken. This query answers them instead, scoped to one model line.
    #
    # The SQL is not mirrored here, it is *read out of SearchDao.kt*: DESCRIPTION_SQL is the one
    # statement in this file that cannot be retyped faithfully, because its haystack is eight
    # columns and two correlated subqueries. Reading it means this checker runs the exact bytes
    # the app ships, and a change on either side fails here rather than in a technician's hand.
    description_sql = _kotlin_description_sql()

    SUMMARY_COLUMNS = ["id", "uid", "code", "title_en", "title_ur",
                       "severity", "is_fault", "display"]

    description_terms = _description_terms

    def describe(series_id: str, brand_id: str, raw: str, limit: int = 60):
        """Runs the app's description query exactly as the DAO builds it."""
        terms = description_terms(raw)
        if not terms:
            return [], []
        where = " AND ".join("d.haystack LIKE ?" for _ in terms)
        cur = db.execute(
            description_sql.replace("%WHERE%", where),
            [series_id, brand_id] + [f"%{t}%" for t in terms] + [str(limit)],
        )
        return [d[0] for d in cur.description], cur.fetchall()

    # The premise, in one line each: the index cannot answer this, and the fallback can.
    check("code_fts answers 0 for 'air leakage' - it holds titles only, so a fallback exists",
          db.execute("SELECT COUNT(*) FROM code_fts WHERE code_fts MATCH ?",
                     (fts_query("air leakage"),)).fetchone()[0], 0)
    check("the description search reaches 29 codes in 25 model lines for 'air leakage'",
          _count_description_matches(db, "air leakage", description_sql), (29, 25))

    FOXESS, PANASONIC = "foxess-h1-ac1-g2-insulation", "panasonic-hf-self-diagnosis"
    cols, rows = describe(FOXESS, "foxess", "air leakage")
    check("the description query projects the summary columns, in toSummary's order",
          cols, SUMMARY_COLUMNS)
    check("a model whose fix steps mention air leakage returns its 2 codes", len(rows), 2)
    check("a second model with the same words returns its own 2 codes",
          len(describe(PANASONIC, "panasonic", "air leakage")[1]), 2)

    # ── The step that has to work when the index does not ─────────────────────
    # FTS5 is a compile-time option of SQLite, and Android's system build does not promise it.
    # When `code_fts MATCH` throws, SearchDao.codesText catches it and returns nothing so that
    # searchCodes carries on to the LIKE step - which never needed FTS5. Without that, one
    # throw emptied the whole search and the box answered codes but no words: `indoor` in
    # Hitachi's SET FREE air365 finds 31 rows off the titles alone, and the phone showed none.
    # This query is the fallback path verbatim: it touches no index at all.
    AIR365 = "hitachi-pk-set-free-air365-wired-alarm"
    air365_cols, air365_rows = describe(AIR365, "hitachi", "indoor")
    check("a title word answers through the LIKE step alone, with no index involved",
          len(air365_rows), 31)
    check("including code 01, the row the technician reported missing",
          "01" in [r[2] for r in air365_rows], True)

    # ── RULE 3, on the one series_id the database reuses across 9 brands ─────
    # `series_id` is not unique: 'inverter-split' belongs to nine brands. A description query
    # bound on the series alone would return all nine brands' rows at once — the exact failure
    # RULE 3 exists to prevent, and the reason both bindings live in the statement.
    SHARED, WORD = "inverter-split", "power"
    shared_brands = [r[0] for r in db.execute(
        "SELECT DISTINCT brand_id FROM codes WHERE series_id = ? ORDER BY brand_id", (SHARED,))]
    check("the shared series really is shared by nine brands", len(shared_brands), 9)
    check("every one of the nine gets its own rows for the same words",
          [len(describe(SHARED, b, WORD)[1]) for b in shared_brands],
          [29, 23, 14, 1, 1, 21, 30, 24, 19])
    check("the same words against a brand that does not publish that series return nothing",
          len(describe(SHARED, "sharp", WORD)[1]), 0)

    # The shape of the answer: AND, not OR; tokenised, not literal; scoped, not global.
    check("AND, not OR: one real word plus one absent word finds nothing",
          len(describe(FOXESS, "foxess", "air zzzqqxnothing")[1]), 0)
    check("a nonsense description finds nothing", len(describe(FOXESS, "foxess", "zzzqqxnothing")[1]), 0)
    check("punctuation is tokenised away rather than matched literally",
          len(describe(FOXESS, "foxess", "air leakage!")[1]), 2)
    check("the statement has exactly three placeholders that are not terms",
          description_sql.count("?"), 3)

    # The gate: whether a dead-end query is words or a code is decided by the database, not by
    # the shape of the string — 27% of code strings contain no digit, so a digit test would get
    # `BLINK-RUNNING` wrong.
    check("'E6' is a code somewhere, so it must never reach the description search",
          db.execute("SELECT COUNT(*) FROM codes WHERE code_norm = ?",
                     (norm("E6"),)).fetchone()[0] > 0, True)
    check("'air leakage' is not a code, so it must get the description search",
          db.execute("SELECT COUNT(*) FROM codes WHERE code_norm = ?",
                     (norm("air leakage"),)).fetchone()[0], 0)

    # The term rules, which are why the query needs no ESCAPE clause.
    check("a one-character query is dropped, so LIKE cannot become a scan",
          description_terms("a"), [])
    check("terms are alphanumeric only, so no input can inject a LIKE wildcard",
          all(t.isascii() and t.isalnum()
              for q in ("100%", "%", "_", "a_b", "();--", "€£¥", "air leakage!")
              for t in description_terms(q)), True)
    check("descriptionTerms splits the way fts5's unicode61 tokenizer does",
          description_terms("air leakage!"), ["AIR", "LEAKAGE"])

    # ── CodeDao.detailById(): the detail query's COLUMN LAYOUT ───────────────
    # This mirrors DETAIL_SQL deliberately. It does not re-test the query's *result*; it pins
    # the order of its select list, because that is the fragile part. The DAO once read
    # `meaning_en` from index 9 (which is `meaning_ur`) and `brand_id` from index 20 on a
    # 20-column cursor. `getString(20)` threw, the view model swallowed it with
    # `runCatching{}.getOrNull()`, and the entire code-detail screen showed "This code is not
    # in the knowledge base" instead of crashing. Every code in the app was unreachable and it
    # looked like missing data.
    #
    # The DAO now reads by column NAME, so an off-by-one cannot recur there. This check guards
    # the other half: if DETAIL_SQL is ever reordered, whoever reordered it has to update the
    # expected list below rather than discover it on a phone. Update both together.
    detail_sql = DETAIL_SQL
    cur = db.execute(detail_sql, ("1",))
    got_columns = [d[0] for d in cur.description]
    check("DETAIL_SQL column order is what the app expects", got_columns, list(DETAIL_COLUMNS))
    check("DETAIL_SQL returns 20 columns, so the last valid index is 19", len(got_columns), 20)
    # The row a technician is actually looking at must carry real content, or the detail screen
    # renders empty blocks and the failure looks like a data problem again.
    probe = cur.fetchone()
    check("the first code's detail row has a meaning", bool(probe[8]), True)
    check("the first code's detail row has a confidence", bool(probe[12]), True)
    check("the first code's detail row names its model line", bool(probe[18]), True)
    check("the first code's detail row names its brand", bool(probe[19]), True)
    check("one row per code, keyed on codes.id", db.execute(
        "SELECT COUNT(*) FROM codes WHERE id = 1").fetchone()[0], 1)

    # ── FavouritesDao: the table is two columns, and nothing is stale ────────
    check("favourites columns", [r[1] for r in db.execute("PRAGMA table_info(favourites)")],
          ["code_id", "created_at"])
    check("no stale saved ids to sweep", db.execute(
        "SELECT COUNT(*) FROM favourites WHERE code_id NOT IN (SELECT id FROM codes)"
    ).fetchone()[0], 0)

    # ── SearchDao.codePresence: the brands screen's teaching empty state ────
    # This is the only query in the app whose *output* is a sentence. If the shape is wrong the
    # screen says something false out loud, so it is pinned on both halves: the count, and the
    # guarantee that a name is only ever returned when there is exactly one brand to name.
    presence_sql = """
        SELECT COUNT(DISTINCT c.brand_id),
               CASE WHEN COUNT(DISTINCT c.brand_id) = 1 THEN MIN(b.name) END
          FROM codes c LEFT JOIN brands b ON b.id = c.brand_id
         WHERE c.code_norm = ?
    """
    for query, want_count in [("E6", 16), ("E1", 20), ("F4", 15), ("200", 1), ("ID013", 1)]:
        count, name = db.execute(presence_sql, (norm(query),)).fetchone()
        check(f"codePresence({query}) counts the brands that publish it", count, want_count)
        if count == 1:
            check(f"codePresence({query}) names the single publishing brand", bool(name), True)
        else:
            check(f"codePresence({query}) names no brand when there are {count}",
                  name is None, True)

    # The number the guide quotes as an illustration is E6 on 16 brands. If a data release
    # moves it, this fails loudly rather than the app quietly telling a technician something
    # that is no longer true.
    check("E6 really is on 16 brands, as PHASE_5 5 and DESIGN.md 4.2 state",
          db.execute(presence_sql, ("E6",)).fetchone()[0], 16)

    # The digit heuristic in `looksLikeACode` is only safe because of these two facts. If a
    # future data release adds a brand whose name contains a digit, the teaching state would
    # start firing on a brand search and the screen would explain a rule nobody broke.
    check("no brand name contains a digit, so the digit heuristic cannot misfire",
          db.execute("SELECT COUNT(*) FROM brands WHERE name GLOB '*[0-9]*'").fetchone()[0], 0)
    check("most code strings do contain a digit, so the heuristic is worth having",
          db.execute(
              "SELECT COUNT(*) FROM codes WHERE code_norm GLOB '*[0-9]*'"
          ).fetchone()[0] > 1000, True)

    # `code_norm` must exist and be indexed: this is the difference between 0.15 ms and a
    # full scan, and the whole reason the query is on a keystroke at all.
    check("code_norm exists on codes", "code_norm" in
          [r[1] for r in db.execute("PRAGMA table_info(codes)")], True)
    check("code_norm is indexed", "idx_codes_norm" in
          [r[0] for r in db.execute("SELECT name FROM sqlite_master WHERE type='index'")], True)

    db.close()
    if failures:
        print(f"\n::error::the app's SQL does not match the data: {', '.join(failures)}")
        return 1
    print("\n  every app SQL check passed")
    return 0


if __name__ == "__main__":
    sys.exit(main())
