package dev.whosnickdoglio.yahoofantasy.reporting.sheets

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
internal class DefaultGoogleSheetsClient(
    private val sheets: Sheets,
    private val leagueInfo: LeagueInfo,
) : GoogleSheets {
    override suspend fun sendReport(teamReport: List<GoogleSheetsTeamReport>) {
        require(teamReport.isNotEmpty()) { "Team report cannot be empty" }
        with(sheets.spreadsheets().values()) {
            append(
                leagueInfo.spreadSheetInfo.id,
                leagueInfo.spreadSheetInfo.range,
                ValueRange().setValues(
                    teamReport.map { it.toList() }),
            ).setValueInputOption("USER_ENTERED")

        }.execute()
    }
}

private fun GoogleSheetsTeamReport.toList(): List<String> = listOf(
    date.toString(),
    teamName,
    healthyOnInjuryList.toString(),
    activePlayerOnBenchWithOpenStartingSpot.toString(),
    injuredPlayerInStartingLineup.toString(),
    injuredPlayerOnBenchWithOpenInjuryListSpot.toString(),
    url,
)
