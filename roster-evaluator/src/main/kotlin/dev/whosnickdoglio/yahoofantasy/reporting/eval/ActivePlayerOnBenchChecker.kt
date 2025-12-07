// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
public class ActivePlayerOnBenchChecker : RosterChecker {
    @Suppress(
        "CyclomaticComplexMethod",
        "CognitiveComplexMethod",
        "ReturnCount",
    ) // TODO clean this up
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val activeBenchPlayers =
            roster.filter {
                it.position == "BN" &&
                    it.hasGameToday() &&
                    it.healthStatus == PlayerHealthStatus.HEALTHY
            }

        val startingLineup = roster.filter { it.isStarting() }

        if (activeBenchPlayers.isEmpty() || startingLineup.isEmpty()) return null

        val spotToPlayer = mutableMapOf<Int, PlayerRowRawInfo>()
        for ((index, player) in startingLineup.withIndex()) {
            if (player.hasGameToday() && player.healthStatus == PlayerHealthStatus.HEALTHY) {
                spotToPlayer[index] = player
            }
        }

        fun canFindAugmentingPath(
            player: PlayerRowRawInfo,
            visitedSpots: MutableSet<Int>,
        ): Boolean {
            for ((spotIndex, spot) in startingLineup.withIndex()) {
                if (
                    player.fullPositionalEligibility().contains(spot.position) &&
                        spotIndex !in visitedSpots
                ) {
                    visitedSpots.add(spotIndex)
                    val occupant = spotToPlayer[spotIndex]
                    if (occupant == null || canFindAugmentingPath(occupant, visitedSpots)) {
                        return true
                    }
                }
            }
            return false
        }

        activeBenchPlayers.forEach { benchPlayer ->
            if (canFindAugmentingPath(benchPlayer, mutableSetOf())) {
                return Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT
            }
        }

        return null
    }
}
