// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
internal class HealthyPlayerOnInjuryListChecker : RosterChecker {
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val healthyPlayersOnInjuryList = roster.filter { rosterSpot ->
            rosterSpot.isOnInjuryList() && rosterSpot.healthStatus == PlayerHealthStatus.HEALTHY
        }

        return if (healthyPlayersOnInjuryList.isNotEmpty()) {
            Violation.HEALTHY_ON_IL
        } else {
            null
        }
    }
}
