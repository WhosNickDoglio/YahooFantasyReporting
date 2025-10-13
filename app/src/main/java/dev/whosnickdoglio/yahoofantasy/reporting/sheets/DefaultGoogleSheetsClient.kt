package dev.whosnickdoglio.yahoofantasy.reporting.sheets

import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char

@Inject
@ContributesBinding(AppScope::class)
internal class DefaultGoogleSheetsClient(
    private val sheets: Sheets,
    private val leagueInfo: LeagueInfo,
) : GoogleSheets {
    override suspend fun sendReport(teamReport: List<GoogleSheetsTeamReport>) {
        if (teamReport.isEmpty()) {
            println("No reports to send")
        } else {
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
}

private fun GoogleSheetsTeamReport.toList(): List<String> = listOf(
    date.format(dateFormat),
    teamName,
    violation.joinToString(", "),
    url,
)

private val dateFormat = LocalDate.Format {
    monthNumber()
    char('-')
    day()
    char('-')
    year()
}
