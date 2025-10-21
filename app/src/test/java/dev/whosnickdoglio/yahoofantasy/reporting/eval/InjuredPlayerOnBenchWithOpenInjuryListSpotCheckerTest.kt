// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import dev.whosnickdoglio.yahoofantasy.reporting.data.FakeLeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import org.junit.Test

class InjuredPlayerOnBenchWithOpenInjuryListSpotCheckerTest {

    @Test
    fun `empty injury list with injured player on bench returns violation`() {
        val checker = InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(FakeLeagueInfo())
        val result =
            checker.check(
                listOf(Player(position = "BN", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY))
            )

        assertThat(result).isEqualTo(Violation.IL_PLAYER_ON_BENCH_WITH_OPEN_IL_SPOT)
    }

    @Test
    fun `open injury list with injured player on bench returns violation`() {
        val checker = InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(FakeLeagueInfo())
        val result =
            checker.check(
                listOf(
                    Player(position = "BN", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                    Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                    Player(position = "IL+", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                )
            )

        assertThat(result).isEqualTo(Violation.IL_PLAYER_ON_BENCH_WITH_OPEN_IL_SPOT)
    }

    @Test
    fun `empty injury list with  short term injured player on bench returns no violation`() {
        val checker = InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(FakeLeagueInfo())
        val result =
            checker.check(
                listOf(Player(position = "BN", healthStatus = PlayerHealthStatus.SHORT_TERM_INJURY))
            )

        assertThat(result).isNull()
    }

    @Test
    fun `full injury list with injured player on bench returns no violation`() {
        val checker = InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(FakeLeagueInfo())
        val result =
            checker.check(
                listOf(
                    Player(healthStatus = PlayerHealthStatus.HEALTHY),
                    Player(healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                    Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                    Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                    Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                    Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                )
            )

        assertThat(result).isNull()
    }

    @Test
    fun `full injury list with no injured bench players returns no violations`() {
        val checker = InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(FakeLeagueInfo())
        val result =
            checker.check(
                listOf(
                    Player(healthStatus = PlayerHealthStatus.HEALTHY),
                    Player(healthStatus = PlayerHealthStatus.HEALTHY),
                    Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                    Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                    Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                    Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
                )
            )

        assertThat(result).isNull()
    }
}
