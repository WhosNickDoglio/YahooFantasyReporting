// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.containsSubList
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.Test

class RosterUtilsTest {

    @Test
    fun `given player with no game today when hasGameToday is called returns false`() {
        assertThat(Player().hasGameToday()).isFalse()
    }

    @Test
    fun `given player with game today when hasGameToday is called returns true`() {
        assertThat(Player(opponent = "CLE").hasGameToday()).isTrue()
    }

    @Test
    fun `given empty starting spot when isAvailableStartingSpot is called returns true`() {
        assertThat(EmptyRosterSpot().isAvailableStartingSpot()).isTrue()
    }

    @Test
    fun `given taken starting spot with no opponent when isAvailableStartingSpot is called returns true`() {
        assertThat(Player().isAvailableStartingSpot()).isTrue()
    }

    @Test
    fun `given taken starting spot with an opponent when isAvailableStartingSpot is called returns false`() {
        assertThat(Player(opponent = "CLE").isAvailableStartingSpot()).isFalse()
    }

    @Test
    fun `given bench player when isAvailableStartingSpot is called returns false`() {
        assertThat(Player(position = "BN").isAvailableStartingSpot()).isFalse()
    }

    @Test
    fun `given injury list player when isAvailableStartingSpot is called returns false`() {
        assertThat(Player(position = "IL").isAvailableStartingSpot()).isFalse()
    }

    @Test
    fun `given player in starting lineup when isStarting is called returns true`() {
        assertThat(Player(position = "G").isStarting()).isTrue()
    }

    @Test
    fun `given player on bench when isStarting is called returns false`() {
        assertThat(Player(position = "BN").isStarting()).isFalse()
    }

    @Test
    fun `given player on injury list when isStarting is called returns false`() {
        assertThat(Player(position = "IL").isStarting()).isFalse()
    }

    @Test
    fun `given player in starting lineup when isOnInjuryList is called returns false`() {
        assertThat(Player(position = "G").isOnInjuryList()).isFalse()
    }

    @Test
    fun `given player on bench when isOnInjuryList is called returns false`() {
        assertThat(Player(position = "BN").isOnInjuryList()).isFalse()
    }

    @Test
    fun `given player on injury list when isOnInjuryList is called returns true`() {
        assertThat(Player(position = "IL").isOnInjuryList()).isTrue()
    }

    @Test
    fun `given player on injury list plus when isOnInjuryList is called returns true`() {
        assertThat(Player(position = "IL+").isOnInjuryList()).isTrue()
    }

    @Test
    fun `given player with G eligibility when fullPositionalEligibility is called return all eligible positions including Util`() {
        assertThat(Player(positionEligibility = listOf("G")).fullPositionalEligibility())
            .isEqualTo(listOf("G", "Util"))
    }

    @Test
    fun `given player with G eligibility when fullPositionalEligibility is called returned list contains G`() {
        val player = Player(positionEligibility = listOf("G"))
        assertThat(player.fullPositionalEligibility()).containsSubList(player.positionEligibility!!)
    }
}
