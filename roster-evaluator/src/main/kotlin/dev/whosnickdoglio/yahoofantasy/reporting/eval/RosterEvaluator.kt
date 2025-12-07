// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.Inject

@Inject
public class RosterEvaluator(private val rosterCheckers: Set<RosterChecker>) {

    public fun evaluate(roster: List<PlayerRowRawInfo>): EvaluationResult {
        val violations = rosterCheckers.mapNotNull { checker -> checker.check(roster) }

        return if (violations.isNotEmpty()) {
            EvaluationResult.UnsetRoster(violations.toList())
        } else {
            EvaluationResult.SetRoster
        }
    }
}

public sealed interface EvaluationResult {
    public data object SetRoster : EvaluationResult

    public data class UnsetRoster(val violations: List<Violation>) : EvaluationResult
}
