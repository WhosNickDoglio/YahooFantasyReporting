// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import org.junit.Test

class InjuredPlayersInStartingLineupCheckerTest {

    private val checker = InjuredPlayersInStartingLineupChecker()

    @Test
    fun `starting lineup is empty`() {
        val result =
            checker.check(
                listOf(
                    EmptyRosterSpot(),
                    EmptyRosterSpot(),
                    EmptyRosterSpot(),
                    EmptyRosterSpot(),
                    EmptyRosterSpot(),
                )
            )
        assertThat(result).isNull()
    }

    @Test
    fun `starting lineup only has healthy players on it`() {
        val result =
            checker.check(
                listOf(Player(position = "PG"), Player(position = "Util"), Player(position = "C"))
            )
        assertThat(result).isNull()
    }

    @Test
    fun `starting lineup has a injured player on it`() {
        val result =
            checker.check(
                listOf(
                    Player(position = "PG", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                    Player(position = "Util"),
                    Player(position = "C"),
                )
            )
        assertThat(result).isEqualTo(Violation.IL_IN_STARTING_LINEUP)
    }

    @Test
    fun `starting lineup  has a out player with a game today on it`() {
        val result =
            checker.check(
                listOf(
                    Player(
                        position = "PG",
                        healthStatus = PlayerHealthStatus.SHORT_TERM_INJURY,
                        opponent = "CLE",
                    ),
                    Player(position = "Util"),
                    Player(position = "C"),
                )
            )
        assertThat(result).isEqualTo(Violation.IL_IN_STARTING_LINEUP)
    }

    @Test
    fun `starting lineup  has a out player with no game today on it`() {
        val result =
            checker.check(
                listOf(
                    Player(position = "PG", healthStatus = PlayerHealthStatus.SHORT_TERM_INJURY),
                    Player(position = "Util"),
                    Player(position = "C"),
                )
            )
        assertThat(result).isNull()
    }
}
