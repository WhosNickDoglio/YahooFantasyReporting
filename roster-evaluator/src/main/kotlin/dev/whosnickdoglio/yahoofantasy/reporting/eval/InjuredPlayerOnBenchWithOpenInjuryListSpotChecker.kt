// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
public class InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(private val leagueInfo: LeagueInfo) :
    RosterChecker {
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val injuredPlayersOnBench =
            roster.filter { rosterSpot ->
                rosterSpot.position == "BN" &&
                    rosterSpot.healthStatus == PlayerHealthStatus.LONG_TERM_INJURY
            }

        val injuryListSpotsTaken = roster.filter { rosterSpot -> rosterSpot.isOnInjuryList() }

        val hasOpenInjuryListSpots = injuryListSpotsTaken.size < leagueInfo.injuryListCount

        return if (injuredPlayersOnBench.isNotEmpty() && hasOpenInjuryListSpots) {
            Violation.IL_PLAYER_ON_BENCH_WITH_OPEN_IL_SPOT
        } else {
            null
        }
    }
}
