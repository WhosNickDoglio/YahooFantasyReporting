// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.di.AppDependencyGraph
import dev.zacsweers.metro.createGraphFactory
import java.time.LocalDate

public suspend fun main() {
    val threeDaysAgo = LocalDate.now().minusDays(3)
    val graph =
        createGraphFactory<AppDependencyGraph.Factory>()
            .create(threeDaysAgo = threeDaysAgo, leagueInfo = LeagueInfo.JHart)
    graph.app()
}
