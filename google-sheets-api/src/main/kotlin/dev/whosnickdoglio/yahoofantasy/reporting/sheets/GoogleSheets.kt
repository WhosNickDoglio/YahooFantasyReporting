// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.sheets

import java.time.LocalDate

public interface GoogleSheets {
    public suspend fun sendReport(teamReport: List<GoogleSheetsTeamReport>)

    public suspend fun retrieveReports(): List<GoogleSheetsTeamReport>
}

public data class GoogleSheetsTeamReport(
    val date: LocalDate,
    val teamName: String,
    val healthyOnInjuryList: Int,
    val activePlayerOnBenchWithOpenStartingSpot: Int,
    val injuredPlayerInStartingLineup: Int,
    val injuredPlayerOnBenchWithOpenInjuryListSpot: Int,
    val teamId: Int,
    val url: String,
)

public fun Boolean.toInt(): Int = if (this) 1 else 0
