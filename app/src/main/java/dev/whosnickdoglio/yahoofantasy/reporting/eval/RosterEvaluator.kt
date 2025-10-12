package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.Inject

@Inject
internal class RosterEvaluator(private val rosterCheckers: Set<RosterChecker>) {

    fun evaluate(roster: List<PlayerRowRawInfo>): EvaluationResult {
        val benchPlayersWithGamesToday = roster.filter { it.position.equals("BN") }.any { it.hasGameToday() }
        val emptyStartingSpots = roster.filter { it.isEmptyStartingSpot() }

        if (!benchPlayersWithGamesToday || emptyStartingSpots.isEmpty()) {
            return EvaluationResult.SetRoster
        }

        val violations = rosterCheckers.mapNotNull { it.check(roster) }

        if (violations.isNotEmpty()) {
            return EvaluationResult.UnsetRoster(violations.toList())
        }

        return EvaluationResult.SetRoster
    }
}

internal sealed interface EvaluationResult {
    data object SetRoster : EvaluationResult
    data class UnsetRoster(val violations: List<Violation>) : EvaluationResult
}
