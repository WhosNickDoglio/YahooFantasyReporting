// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.di

import dev.whosnickdoglio.yahoofantasy.reporting.App
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.util.coroutines.IoDispatcher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@DependencyGraph(AppScope::class)
internal interface AppDependencyGraph {

    val app: App

    @Suppress("InjectDispatcher") // silly detekt I am injecting this
    @Provides
    @IoDispatcher
    fun provideIoCoroutineContext(): CoroutineContext = Dispatchers.IO

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides yesterday: LocalDate,
            @Provides leagueInfo: LeagueInfo,
        ): AppDependencyGraph
    }
}
