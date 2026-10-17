// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.di.AppDependencyGraph
import dev.zacsweers.metro.createGraphFactory
import java.time.LocalDate

public suspend fun main(args: Array<String>) {
    val leagues =
        listOf(
            LeagueInfo.JHart,
            LeagueInfo.Redacted,
            LeagueInfo.BirthdayCakeOreo,
            LeagueInfo.PeoplesRepublic,
        )

    val leagueInfo =
        leagues.firstOrNull { info -> info.name == args.first() }
            ?: error("Unknown league ${args.first()}")

    val yesterday = LocalDate.now().minusDays(1)
    val graph =
        createGraphFactory<AppDependencyGraph.Factory>()
            .create(yesterday = yesterday, leagueInfo = leagueInfo)
    graph.app()
}
