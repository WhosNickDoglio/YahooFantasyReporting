// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.sheets

import java.time.LocalDate

internal interface GoogleSheets {
    suspend fun sendReport(teamReport: List<GoogleSheetsTeamReport>)
}

internal data class GoogleSheetsTeamReport(
    val date: LocalDate,
    val teamName: String,
    val healthyOnInjuryList: Boolean,
    val activePlayerOnBenchWithOpenStartingSpot: Boolean,
    val injuredPlayerInStartingLineup: Boolean,
    val injuredPlayerOnBenchWithOpenInjuryListSpot: Boolean,
    val url: String,
)
