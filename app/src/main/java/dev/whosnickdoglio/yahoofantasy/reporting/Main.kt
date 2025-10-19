// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.di.AppDependencyGraph
import dev.zacsweers.metro.createGraphFactory
import java.time.LocalDate

public suspend fun main(args: Array<String>) {
    val leagueInfo =
        when (val league = args.first()) {
            "mitch" -> LeagueInfo.MitchRobLeagueInfo
            "redacted" -> LeagueInfo.Redacted
            "birthday" -> LeagueInfo.BirthdayCakeOreo
            else -> error("Unknown league $league")
        }

    val yesterday = LocalDate.now().minusDays(1)
    val graph =
        createGraphFactory<AppDependencyGraph.Factory>()
            .create(yesterday = yesterday, leagueInfo = leagueInfo)
    graph.app()
}
