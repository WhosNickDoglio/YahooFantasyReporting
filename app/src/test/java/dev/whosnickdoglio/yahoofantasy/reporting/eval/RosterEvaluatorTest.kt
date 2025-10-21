// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import org.junit.Test

class RosterEvaluatorTest {

    @Test
    fun `evaluate returns SetRoster when there are no players on the bench with opponents`() {
        val evaluator = RosterEvaluator(emptySet())

        val result = evaluator.evaluate(listOf(Player(position = "BN"), Player(position = "BN")))

        assertThat(result).isEqualTo(EvaluationResult.SetRoster)
    }

    @Test
    fun `evaluate returns SetRoster when the starting lineup is full with active spots`() {
        val evaluator = RosterEvaluator(emptySet())

        val result =
            evaluator.evaluate(
                listOf(
                    Player(position = "PG", opponent = "CLE"),
                    Player(position = "C", opponent = "NYK"),
                    Player(position = "BN", opponent = "BKN"),
                )
            )

        assertThat(result).isEqualTo(EvaluationResult.SetRoster)
    }

    @Test
    fun `evaluate returns UnsetRoster when a checker finds a violation`() {
        val violation = Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT
        val evaluator = RosterEvaluator(setOf(RosterChecker { _ -> violation }))

        val result =
            evaluator.evaluate(
                listOf(
                    Player(position = "PG"),
                    Player(
                        position = "BN",
                        opponent = "BKN",
                        healthStatus = PlayerHealthStatus.HEALTHY,
                    ),
                )
            )

        assertThat(result).isEqualTo(EvaluationResult.UnsetRoster(listOf(violation)))
    }
}
