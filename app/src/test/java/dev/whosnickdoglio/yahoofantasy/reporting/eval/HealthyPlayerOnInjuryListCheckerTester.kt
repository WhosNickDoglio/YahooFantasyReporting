// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import org.junit.Test

class HealthyPlayerOnInjuryListCheckerTester {

    private val checker = HealthyPlayerOnInjuryListChecker()

    @Test
    fun `injury list is empty`() {
        val result = checker.check(Roster())
        assertThat(result).isNull()
    }

    @Test
    fun `injury list only has injured players on it`() {
        val result = checker.check(Roster(
            Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
            Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
        ))
        assertThat(result).isNull()
    }

    @Test
    fun `injury list has a healthy player on it`() {
        val result = checker.check(Roster(
            Player(position = "IL", healthStatus = PlayerHealthStatus.LONG_TERM_INJURY),
            Player(position = "IL", healthStatus = PlayerHealthStatus.HEALTHY),
        ))
        assertThat(result).isEqualTo(Violation.HEALTHY_ON_IL)
    }
}
