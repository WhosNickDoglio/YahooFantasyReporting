// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.util.coroutines.IoDispatcher
import dev.whosnickdoglio.yahoofantasy.reporting.util.log.SimpleLogger
import dev.zacsweers.metro.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

@Inject
public class App(
    private val simpleLogger: SimpleLogger,
    @param:IoDispatcher private val coroutineContext: CoroutineContext,
) {

    public suspend operator fun invoke(): Unit =
        withContext(coroutineContext) { simpleLogger.log("Hello data reporter") }
}
