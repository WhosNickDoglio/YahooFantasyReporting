// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.Inject

@Inject
internal class RosterEvaluator(private val rosterCheckers: Set<RosterChecker>) {

    fun evaluate(roster: List<PlayerRowRawInfo>): EvaluationResult {
        val benchPlayersWithGamesToday =
            roster.filter { rosterSpot -> rosterSpot.position.equals("BN") && rosterSpot.healthStatus == PlayerHealthStatus.HEALTHY }
                .any { rosterSpot -> rosterSpot.hasGameToday() }
        val noGamesStartingSpots = roster.filter { rosterSpot -> !rosterSpot.hasGameToday() }

        if (!benchPlayersWithGamesToday || noGamesStartingSpots.isEmpty()) return EvaluationResult.SetRoster

        val violations = rosterCheckers.mapNotNull { checker -> checker.check(roster) }

        if (violations.isNotEmpty()) return EvaluationResult.UnsetRoster(violations.toList())

        return EvaluationResult.SetRoster
    }
}

internal sealed interface EvaluationResult {
    data object SetRoster : EvaluationResult
    data class UnsetRoster(val violations: List<Violation>) : EvaluationResult
}
