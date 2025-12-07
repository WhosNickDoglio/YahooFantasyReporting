// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.sheets

import assertk.assertThat
import assertk.assertions.containsExactly
import java.time.LocalDate
import org.junit.Test

class ToListTest {

    @Test
    fun `report is converted to list with correct order`() {
        val report =
            GoogleSheetsTeamReport(
                date = LocalDate.of(2025, 5, 10),
                teamName = "Team Name",
                healthyOnInjuryList = 1,
                activePlayerOnBenchWithOpenStartingSpot = 1,
                injuredPlayerInStartingLineup = 1,
                injuredPlayerOnBenchWithOpenInjuryListSpot = 1,
                teamId = 1,
                url = "example.com",
            )

        assertThat(report.toList())
            .containsExactly("2025-05-10", "Team Name", "1", "1", "1", "1", "1", "example.com")
    }
}
