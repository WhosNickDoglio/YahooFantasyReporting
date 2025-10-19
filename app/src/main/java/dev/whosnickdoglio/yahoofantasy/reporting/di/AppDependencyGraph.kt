// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.di

import dev.whosnickdoglio.yahoofantasy.reporting.App
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import java.time.LocalDate

@DependencyGraph(AppScope::class)
internal interface AppDependencyGraph {

    val app: App

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides yesterday: LocalDate,
            @Provides leagueInfo: LeagueInfo,
        ): AppDependencyGraph
    }
}
