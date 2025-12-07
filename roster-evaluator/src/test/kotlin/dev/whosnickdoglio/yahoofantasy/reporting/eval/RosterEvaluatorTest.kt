// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isInstanceOf
import assertk.assertions.size
import dev.whosnickdoglio.yahoofantasy.reporting.data.FakeLeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.Player
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import org.junit.Test

class RosterEvaluatorTest {

    private fun TestRosterEvaluator(
        leagueInfo: LeagueInfo = FakeLeagueInfo(),
        checkers: Set<RosterChecker> =
            setOf(
                ActivePlayerOnBenchChecker(),
                HealthyPlayerOnInjuryListChecker(),
                InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(leagueInfo),
                InjuredPlayersInStartingLineupChecker(),
            ),
    ): RosterEvaluator = RosterEvaluator(rosterCheckers = checkers)

    @Test
    fun `evaluate returns SetRoster when there are no players on the bench with opponents`() {
        val evaluator = TestRosterEvaluator(checkers = emptySet())

        val result = evaluator.evaluate(listOf(Player(position = "BN"), Player(position = "BN")))

        assertThat(result).isEqualTo(EvaluationResult.SetRoster)
    }

    @Test
    fun `evaluate returns SetRoster when the starting lineup is full with active spots`() {
        val evaluator = TestRosterEvaluator(checkers = emptySet())

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
    fun `evaluate returns SetRoster when bench player has game and starting spot is open but no checkers fail`() {
        val evaluator = TestRosterEvaluator(checkers = emptySet())

        val result =
            evaluator.evaluate(
                listOf(
                    Player(position = "PG"), // No game today
                    Player(
                        position = "BN",
                        opponent = "BKN",
                        healthStatus = PlayerHealthStatus.HEALTHY,
                    ),
                )
            )

        assertThat(result).isEqualTo(EvaluationResult.SetRoster)
    }

    @Test
    fun `evaluate returns UnsetRoster when a checker finds a violation`() {
        val evaluator = TestRosterEvaluator()

        val result =
            evaluator.evaluate(
                listOf(
                    Player(position = "PG"), // No game today
                    Player(
                        position = "BN",
                        opponent = "BKN",
                        healthStatus = PlayerHealthStatus.HEALTHY,
                    ),
                )
            )

        assertThat(result).isInstanceOf(EvaluationResult.UnsetRoster::class)
    }

    @Test
    fun `evaluate returns SetRoster when bench player with game is not healthy`() {
        val evaluator = RosterEvaluator(emptySet())

        val result =
            evaluator.evaluate(
                listOf(
                    Player(position = "PG"), // No game today
                    Player(
                        position = "BN",
                        opponent = "BKN",
                        healthStatus = PlayerHealthStatus.SHORT_TERM_INJURY,
                    ),
                )
            )

        assertThat(result).isEqualTo(EvaluationResult.SetRoster)
    }

    @Test
    fun `evaluate returns SetRoster for an empty roster`() {
        val evaluator = RosterEvaluator(emptySet())
        val result = evaluator.evaluate(emptyList())
        assertThat(result).isEqualTo(EvaluationResult.SetRoster)
    }

    @Test
    fun `evaluate returns UnsetRoster with multiple violations from multiple checkers for complex roster`() {
        val evaluator = TestRosterEvaluator()

        val result =
            evaluator.evaluate(
                listOf(
                    Player(position = "PG"), // Open spot
                    Player(position = "SG"), // Open spot
                    Player(position = "SF", opponent = "LAL"),
                    Player(
                        position = "SF",
                        opponent = "LAL",
                        healthStatus = PlayerHealthStatus.LONG_TERM_INJURY,
                    ),
                    Player(
                        position = "BN",
                        opponent = "MIA",
                        healthStatus = PlayerHealthStatus.HEALTHY,
                    ),
                    Player(
                        position = "BN",
                        opponent = "CHI",
                        healthStatus = PlayerHealthStatus.HEALTHY,
                    ),
                    Player(position = "BN", healthStatus = PlayerHealthStatus.HEALTHY), // No game
                    Player(
                        position = "BN",
                        opponent = "DEN",
                        healthStatus = PlayerHealthStatus.SHORT_TERM_INJURY,
                    ),
                )
            )

        assertThat(result).isInstanceOf(EvaluationResult.UnsetRoster::class)
        assertThat((result as EvaluationResult.UnsetRoster).violations).size().isGreaterThan(1)
    }
}
