// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
internal class ActivePlayerOnBenchChecker : RosterChecker {
    // TODO need to figure out a good way to check if we can move things around to make it work
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val mutableRoster = roster.toMutableList()
        val availableStartingSpots = mutableRoster.filter { it.isAvailableStartingSpot() }

        var violation: Violation? = null

        val firstPass = mutableRoster.checkForEasyMoveFromBench(availableStartingSpots)

        if (firstPass != null) return firstPass

        availableStartingSpots.forEach { availableSpot ->
            val availableIndex = mutableRoster.indexOf(availableSpot)
            val openPosition = availableSpot.position
            val firstMovablePlayer = mutableRoster.firstOrNull {
                it.isStarting() && it.hasGameToday() && it.fullPositionalEligibility()
                    .contains(openPosition) && availableSpot.fullPositionalEligibility().contains(it.position)
            } ?: return@forEach

            val oldPosition = firstMovablePlayer.position
            val oldIndex = mutableRoster.indexOf(firstMovablePlayer)

            mutableRoster[oldIndex] = firstMovablePlayer.copy(position = availableSpot.position)
            mutableRoster[availableIndex] = availableSpot.copy(position = oldPosition)

            violation = mutableRoster.checkForEasyMoveFromBench(mutableRoster.filter { it.isAvailableStartingSpot() })
        }

        return violation
    }

    private fun List<PlayerRowRawInfo>.checkForEasyMoveFromBench(availableStartingSpots: List<PlayerRowRawInfo>): Violation? {
        val hasBenchPlayersWhoCanStart =
            filter { it.hasGameToday() && it.healthStatus == PlayerHealthStatus.HEALTHY && it.position == "BN" }

        val openPositions = availableStartingSpots.map { it.position }

        val canMoveBenchPlayerToOpenPosition = hasBenchPlayersWhoCanStart.any { benchPlayer ->
            benchPlayer.fullPositionalEligibility().any { position -> position in openPositions }
        }

        return if (canMoveBenchPlayerToOpenPosition) {
            Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT
        } else {
            null
        }

    }
}
