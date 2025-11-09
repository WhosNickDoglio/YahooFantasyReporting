// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.di.AppDependencyGraph
import dev.zacsweers.metro.createGraphFactory
import java.time.LocalDate

internal class CheckRosterCommand : SuspendingCliktCommand() {

    private val yesterday by lazy { LocalDate.now().minusDays(1) }

    private val league: LeagueInfo by
        argument(help = "League to check").convert { arg ->
            when (arg) {
                "mitch" -> LeagueInfo.MitchRobLeagueInfo
                "redacted" -> LeagueInfo.Redacted
                "birthday" -> LeagueInfo.BirthdayCakeOreo
                else -> error("Unknown league $arg")
            }
        }

    private val graph by lazy {
        createGraphFactory<AppDependencyGraph.Factory>().create(yesterday, league)
    }

    override suspend fun run() = graph.app()
}
