// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
internal class ActivePlayerOnBenchChecker : RosterChecker {
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val activePlayers =
            roster.filter { it.hasGameToday() && it.healthStatus == PlayerHealthStatus.HEALTHY }
        val startingSpots = roster.filter { it.isStarting() }.map { it.position }

        // If there are no active players on the bench, then there is nothing to do
        if (
            activePlayers.none { it.position == "BN" } ||
                // This should not be possible but if there are no starting spots then this check is
                // invalid
                startingSpots.isEmpty()
        ) {
            return null
        }

        val currentActiveStartersCount =
            roster.count {
                it.isStarting() &&
                    it.hasGameToday() &&
                    it.healthStatus == PlayerHealthStatus.HEALTHY
            }

        // A greedy approach to solving this problem. We sort our list of potential starters by the
        // number of positions they are eligible for. This ensures that we prioritize players with
        // fewer options first.
        val potentialStarters = activePlayers.sortedBy { it.fullPositionalEligibility().size }
        val availableSpots = startingSpots.toMutableList()
        var maxPossibleActiveStarters = 0

        for (player in potentialStarters) {
            val eligibleSpot =
                availableSpots.find { spot -> player.fullPositionalEligibility().contains(spot) }

            if (eligibleSpot != null) {
                maxPossibleActiveStarters++
                availableSpots.remove(eligibleSpot)
            }
        }

        return if (maxPossibleActiveStarters > currentActiveStartersCount) {
            Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT
        } else {
            null
        }
    }
}
