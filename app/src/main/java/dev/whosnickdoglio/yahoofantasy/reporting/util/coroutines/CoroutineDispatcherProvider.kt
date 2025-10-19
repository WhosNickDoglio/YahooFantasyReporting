// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.util.coroutines

import dev.zacsweers.metro.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@Inject
internal class CoroutineDispatcherProvider(
    val io: CoroutineContext = Dispatchers.IO,
    val default: CoroutineContext = Dispatchers.Default,
)
