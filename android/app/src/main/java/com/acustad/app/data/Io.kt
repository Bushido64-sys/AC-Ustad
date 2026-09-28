package com.acustad.app.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Every database read and write in this app goes through here, so that no query can ever be
 * issued on the main thread. This is the single place that guarantee is enforced, rather than
 * being repeated — and eventually forgotten — in every DAO.
 *
 * The dispatcher is injectable so tests can substitute a deterministic one.
 */
internal suspend fun <T> io(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: () -> T,
): T = withContext(dispatcher) { block() }
