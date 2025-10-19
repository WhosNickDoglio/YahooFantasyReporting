// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class ActivePlayerOnBenchCheckerTest {

    private val checker = ActivePlayerOnBenchChecker()

    @Test
    fun `easy to move shooting guard on bench into open shooting guard position returns violation`() {
        val result = checker.check(
            Roster(
                EmptyRosterSpot(position = "SG"),
                Player(position = "BN", positionEligibility = listOf("SG", "G"), opponent = "CLE")
            )
        )

        assertThat(result).isEqualTo(
            Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT
        )
    }

    @Test
    fun `move player from util to fully set lineup returns violation`() {
        val result = checker.check(
            Roster(
                EmptyRosterSpot(position = "PG"),
                EmptyRosterSpot(position = "SG"),
                Player(position = "Util", positionEligibility = listOf("SG"), opponent = "CLE"),
                Player(position = "BN", positionEligibility = listOf("C", "F"), opponent = "NYK")
            )
        )

        assertThat(result).isEqualTo(
            Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT
        )
    }
}
