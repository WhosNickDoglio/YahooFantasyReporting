// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
@file:Suppress("JUnitMalformedDeclaration")

package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import app.cash.burst.Burst
import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import org.junit.Test

internal class SanitizationTest {

    enum class PlayerHealthValues(val input: String, val expected: PlayerHealthStatus) {
        NO_GAME_HEALTHY("Cade CunninghamPlayer Note DET - PG,SG", PlayerHealthStatus.HEALTHY),
        GAME_HEALTHY(
            "Austin ReavesNew Player Note LAL - PG,SG L, 109-119 vs GSW",
            PlayerHealthStatus.HEALTHY,
        ),
        NO_GAME_INJURED(
            "Zach EdeyINJNo new player Notes MEM - C",
            PlayerHealthStatus.LONG_TERM_INJURY,
        ),
        NO_GAME_GTD(
            "OG AnunobyGTDNew Player Note NYK - SF,PF",
            PlayerHealthStatus.GAME_TIME_DECISION,
        ),
    }

    enum class SanitizeValues(val input: String, val expected: String) {
        PLAYER_NAME("Cade CunninghamPlayer Note DET - PG,SG", "Cade Cunningham"),
        PLAYER_NAME_WITH_GAME(
            "Austin ReavesNew Player Note LAL - PG,SG L, 109-119 vs GSW",
            "Austin Reaves",
        ),
        PLAYER_NAME_WITH_INJURY("Zach EdeyINJNo new player Notes MEM - C", "Zach Edey"),
        PLAYER_NAME_WITH_GTD("OG AnunobyGTDNew Player Note NYK - SF,PF", "OG Anunoby"),
        PLAYER_NAME_WITH_INJURY_AND_GAME(
            "Keegan MurrayINJVideo ForecastPlayer Note SAC - SF,PF",
            "Keegan Murray",
        ),
    }

    enum class SanitizeHealthStatus(val input: String, val expected: String) {
        GTD("OG AnunobyGTD", "OG Anunoby"),
        INJ("Zach EdeyINJ", "Zach Edey"),
        OUT("John WallO", "John Wall"),
        HEALTHY("Healthy Player", "Healthy Player"),
    }

    enum class RemoveTeamAbbr(val input: String, val expected: String) {
        TEAM_AT_END("Cade Cunningham DET", "Cade Cunningham "),
        TEAM_IN_MIDDLE("Jalen Brunson NYK - PG", "Jalen Brunson  - PG"),
        NO_TEAM("No team here", "No team here"),
        PHOENIX_ABBR("Chris Paul PHO - PG", "Chris Paul  - PG"),
    }

    enum class SanitizePositionEligibility(val input: String, val expected: List<String>) {
        MULTIPLE_POS("Cade Cunningham - PG,SG", listOf("PG", "SG")),
        MULTIPLE_POS_WITH_SPACES("Cade Cunningham - PG, SG", listOf("PG", "SG")),
        MULTIPLE_POS_WITH_GAME(
            "Austin ReavesNew Player Note LAL - PG,SG L, 109-119 vs GSW",
            listOf("PG", "SG"),
        ),
        SINGLE_POS("Jalen Duren - C", listOf("C")),
    }

    @Burst
    @Test
    fun getPlayerHealthStatus(playerName: PlayerHealthValues) {
        assertThat(playerName.input.getPlayerHealthStatus()).isEqualTo(playerName.expected)
    }

    @Burst
    @Test
    fun sanitizePlayerName(player: SanitizeValues) {
        assertThat(player.input.sanitizePlayerName()).isEqualTo(player.expected)
    }

    @Burst
    @Test
    fun sanitizePlayerHealthStatus(player: SanitizeHealthStatus) {
        assertThat(player.input.sanitizePlayerHealthStatus()).isEqualTo(player.expected)
    }

    @Burst
    @Test
    fun removeTeamAbbreviations(player: RemoveTeamAbbr) {
        assertThat(player.input.removeTeamAbbreviations()).isEqualTo(player.expected)
    }

    @Burst
    @Test
    fun sanitizePlayerPositionEligibility(player: SanitizePositionEligibility) {
        assertThat(player.input.sanitizePlayerPositionEligibility()).isEqualTo(player.expected)
    }
}
