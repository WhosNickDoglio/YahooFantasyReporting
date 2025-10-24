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
                healthyOnInjuryList = true,
                activePlayerOnBenchWithOpenStartingSpot = true,
                injuredPlayerInStartingLineup = true,
                injuredPlayerOnBenchWithOpenInjuryListSpot = true,
                teamId = 1,
                url = "example.com",
            )

        assertThat(report.toList())
            .containsExactly(
                "2025-05-10",
                "Team Name",
                "true",
                "true",
                "true",
                "true",
                "1",
                "example.com",
            )
    }
}
