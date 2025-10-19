// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.isEqualTo
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
}
