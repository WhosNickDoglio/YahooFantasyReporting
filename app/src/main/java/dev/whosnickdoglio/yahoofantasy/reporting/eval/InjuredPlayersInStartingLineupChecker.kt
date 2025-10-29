// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
internal class InjuredPlayersInStartingLineupChecker : RosterChecker {
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val injuredPlayersStarting =
            roster.filter { rosterSpot -> rosterSpot.isStarting() && rosterSpot.isInjured() }

        return if (injuredPlayersStarting.isNotEmpty()) {
            Violation.IL_IN_STARTING_LINEUP
        } else {
            null
        }
    }

    private fun PlayerRowRawInfo.isInjured(): Boolean {
        val shortTermInjuryNoGame =
            (healthStatus == PlayerHealthStatus.SHORT_TERM_INJURY && hasGameToday())

        return healthStatus == PlayerHealthStatus.LONG_TERM_INJURY || shortTermInjuryNoGame
    }
}
