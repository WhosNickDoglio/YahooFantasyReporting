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

    val threeDaysAgo = LocalDate.now().minusDays(3)
    val graph =
        createGraphFactory<AppDependencyGraph.Factory>()
            .create(threeDaysAgo = threeDaysAgo, leagueInfo = leagueInfo)
    graph.app()
}
