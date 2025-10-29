// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.Inject

@Inject
internal class RosterEvaluator(private val rosterCheckers: Set<RosterChecker>) {

    fun evaluate(roster: List<PlayerRowRawInfo>): EvaluationResult {
        val violations = rosterCheckers.mapNotNull { checker -> checker.check(roster) }

        return if (violations.isNotEmpty()) {
            EvaluationResult.UnsetRoster(violations.toList())
        } else {
            EvaluationResult.SetRoster
        }
    }
}

internal sealed interface EvaluationResult {
    data object SetRoster : EvaluationResult

    data class UnsetRoster(val violations: List<Violation>) : EvaluationResult
}
