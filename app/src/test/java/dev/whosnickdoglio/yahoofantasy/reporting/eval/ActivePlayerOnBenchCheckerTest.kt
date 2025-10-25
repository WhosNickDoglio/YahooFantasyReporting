// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import org.junit.Test

class ActivePlayerOnBenchCheckerTest {

    private val checker = ActivePlayerOnBenchChecker()

    @Test
    fun `starting spot is empty so return null`() {
        val result =
            checker.check(
                listOf(
                    Player(
                        position = "BN",
                        positionEligibility = listOf("SG", "G"),
                        opponent = "CLE",
                    )
                )
            )

        assertThat(result).isNull()
    }

    @Test
    fun `no available starting spots returns null`() {
        val result =
            checker.check(
                listOf(
                    Player(position = "PG", opponent = "CLE"),
                    Player(position = "SG", opponent = "CLE"),
                    Player(position = "C", opponent = "NYK"),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("SG", "G"),
                        opponent = "CLE",
                    ),
                )
            )

        assertThat(result).isNull()
    }

    @Test
    fun `no bench players with games returns null`() {
        val result =
            checker.check(
                listOf(
                    EmptyRosterSpot(position = "PG"),
                    EmptyRosterSpot(position = "SG"),
                    EmptyRosterSpot(position = "G"),
                    Player(position = "BN", positionEligibility = listOf("SG", "G")),
                )
            )

        assertThat(result).isNull()
    }

    @Test
    fun `easy to move shooting guard on bench into open shooting guard position returns violation`() {
        val result =
            checker.check(
                listOf(
                    EmptyRosterSpot(position = "SG"),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("SG", "G"),
                        opponent = "CLE",
                    ),
                )
            )

        assertThat(result)
            .isEqualTo(Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT)
    }

    @Test
    fun `bench player not eligible for open spot returns null`() {
        val result =
            checker.check(
                listOf(
                    EmptyRosterSpot(position = "PG"),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("C", "F"),
                        opponent = "NYK",
                    ),
                )
            )

        assertThat(result).isNull()
    }

    @Test
    fun `bench player eligible for open Util spot returns violation`() {
        val result =
            checker.check(
                listOf(
                    EmptyRosterSpot(position = "Util"),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("C", "F", "Util"),
                        opponent = "NYK",
                    ),
                )
            )

        assertThat(result)
            .isEqualTo(Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT)
    }

    @Test
    fun `one of multiple bench players is eligible for open spot returns violation`() {
        val result =
            checker.check(
                listOf(
                    EmptyRosterSpot(position = "PG"),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("C", "F"),
                        opponent = "BOS",
                    ),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("PG", "G"),
                        opponent = "NYK",
                    ),
                )
            )

        assertThat(result)
            .isEqualTo(Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT)
    }

    @Test
    fun `bench player with game but not healthy returns null`() {
        val result =
            checker.check(
                listOf(
                    EmptyRosterSpot(position = "PG"),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("PG", "G"),
                        opponent = "NYK",
                        healthStatus = PlayerHealthStatus.SHORT_TERM_INJURY,
                    ),
                )
            )

        assertThat(result).isNull()
    }

    @Test
    fun `empty roster returns null`() {
        val result = checker.check(emptyList())
        assertThat(result).isNull()
    }

    @Test
    fun `multiple open spots and multiple bench players with one match returns violation`() {
        val result =
            checker.check(
                listOf(
                    EmptyRosterSpot(position = "PG"),
                    EmptyRosterSpot(position = "C"),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("SG", "G"),
                        opponent = "BOS",
                    ),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("C", "F"),
                        opponent = "NYK",
                    ),
                )
            )

        assertThat(result)
            .isEqualTo(Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT)
    }

    @Test
    fun `multiple open spots and multiple bench players with no matches returns null`() {
        val result =
            checker.check(
                listOf(
                    EmptyRosterSpot(position = "PG"),
                    EmptyRosterSpot(position = "SG"),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("C", "F"),
                        opponent = "BOS",
                    ),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("PF", "F"),
                        opponent = "NYK",
                    ),
                )
            )

        assertThat(result).isNull()
    }

    @Test
    fun `player with multiple position eligibilities matches open spot returns violation`() {
        val result =
            checker.check(
                listOf(
                    EmptyRosterSpot(position = "G"),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("PG", "SG", "G"),
                        opponent = "NYK",
                    ),
                )
            )

        assertThat(result)
            .isEqualTo(Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT)
    }

    @Test
    fun `starter moves to open spot for bench player returns violation`() {
        val result =
            checker.check(
                listOf(
                    // Player A at PG, playing, but can move to G
                    Player(
                        position = "PG",
                        positionEligibility = listOf("PG", "G"),
                        opponent = "CLE",
                    ),
                    // Player B at G, NOT playing
                    Player(position = "G", positionEligibility = listOf("G", "SG")),
                    // Player C on Bench, playing, can play PG
                    Player(position = "BN", positionEligibility = listOf("PG"), opponent = "NYK"),
                )
            )

        assertThat(result)
            .isEqualTo(Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT)
    }

    @Test
    fun `one-for-one swap with non-playing starter returns violation`() {
        val result =
            checker.check(
                listOf(
                    Player(position = "PG", positionEligibility = listOf("PG"), opponent = "CLE"),
                    Player(position = "SG", positionEligibility = listOf("SG")),
                    Player(
                        position = "BN",
                        positionEligibility = listOf("SG", "G"),
                        opponent = "NYK",
                    ),
                )
            )

        assertThat(result)
            .isEqualTo(Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT)
    }

    @Test
    fun `complex multi-player move to optimize returns violation`() {
        val result =
            checker.check(
                listOf(
                    Player(
                        position = "PG",
                        positionEligibility = listOf("PG", "G"),
                        opponent = "CLE",
                    ),
                    Player(
                        position = "SG",
                        positionEligibility = listOf("SG", "G"),
                        opponent = "BOS",
                    ),
                    Player(position = "F", positionEligibility = listOf("F")),
                    EmptyRosterSpot(position = "G"),
                    EmptyRosterSpot(position = "C"),
                    Player(position = "BN", positionEligibility = listOf("PG"), opponent = "NYK"),
                    Player(position = "BN", positionEligibility = listOf("F"), opponent = "MIA"),
                )
            )

        assertThat(result)
            .isEqualTo(Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT)
    }

    @Test
    fun `moves possible but no improvement in active starters returns null`() {
        val result =
            checker.check(
                listOf(
                    Player(
                        position = "PG",
                        positionEligibility = listOf("PG", "G"),
                        opponent = "CLE",
                    ),
                    Player(
                        position = "G",
                        positionEligibility = listOf("G", "SG"),
                        opponent = "BOS",
                    ),
                    Player(position = "BN", positionEligibility = listOf("PG"), opponent = "NYK"),
                )
            )

        assertThat(result).isNull()
    }

    @Test
    fun `complex scenario from x`() {
        val result =
            checker.check(
                listOf(
                    Player(
                        position = "PG",
                        playerName = "Anthony Edwards",
                        positionEligibility = listOf("PG", "SG"),
                        opponent = "POR",
                    ),
                    Player(
                        position = "SG",
                        playerName = "Immanual Quickley",
                        positionEligibility = listOf("PG", "SG"),
                        opponent = "ATL",
                    ),
                    Player(
                        position = "G",
                        playerName = "Tyrese Maxey",
                        positionEligibility = listOf("PG"),
                        opponent = "BOS",
                    ),
                    Player(
                        position = "SF",
                        playerName = "Jalen Johnson",
                        positionEligibility = listOf("SF", "PF"),
                        opponent = "TOR",
                    ),
                    Player(
                        position = "PF",
                        playerName = "Cameron Johnson",
                        positionEligibility = listOf("SF", "PF"),
                    ),
                    Player(
                        position = "F",
                        playerName = "Cooper Flagg",
                        positionEligibility = listOf("SF"),
                        opponent = "SAS",
                    ),
                    Player(
                        position = "C",
                        playerName = "Paolo Banchero",
                        positionEligibility = listOf("PF", "C"),
                        opponent = "MIA",
                    ),
                    Player(
                        position = "C",
                        playerName = "Jakob Poeltl",
                        positionEligibility = listOf("SF", "PF"),
                        opponent = "ATL",
                    ),
                    Player(
                        position = "Util",
                        playerName = "Lauri Markkanen",
                        positionEligibility = listOf("SF", "PF"),
                        opponent = "LAC",
                    ),
                    Player(
                        position = "Util",
                        playerName = "Devin Vassell",
                        positionEligibility = listOf("SG", "SF"),
                        opponent = "DAL",
                    ),
                    Player(
                        position = "BN",
                        playerName = "OG Anunoby",
                        positionEligibility = listOf("SF", "PF"),
                        opponent = "CLE",
                    ),
                    Player(
                        position = "BN",
                        playerName = "Cason Wallace",
                        positionEligibility = listOf("PG", "SG"),
                    ),
                    Player(
                        position = "BN",
                        playerName = "Cason Wallace",
                        positionEligibility = listOf("PG", "SG"),
                    ),
                    Player(
                        position = "BN",
                        playerName = "VJ Edgecombe",
                        positionEligibility = listOf("PG"),
                    ),
                    Player(
                        position = "IL+",
                        playerName = "Zach Edey",
                        positionEligibility = listOf("C"),
                        healthStatus = PlayerHealthStatus.LONG_TERM_INJURY,
                    ),
                )
            )

        assertThat(result)
            .isEqualTo(Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT)
    }
}
