package dev.whosnickdoglio.yahoofantasy.reporting.sheets

import dev.whosnickdoglio.yahoofantasy.reporting.eval.Violation
import kotlinx.datetime.LocalDate

internal interface GoogleSheets {
    suspend fun sendReport(teamReport: List<GoogleSheetsTeamReport>)
}

internal data class GoogleSheetsTeamReport(
    val date: LocalDate,
    val teamName: String,
    val violation: List<Violation>,
    val url: String,
)
