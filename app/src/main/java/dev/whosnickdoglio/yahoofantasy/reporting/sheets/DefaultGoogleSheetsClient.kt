package dev.whosnickdoglio.yahoofantasy.reporting.sheets

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject

@Inject
@ContributesBinding(AppScope::class)
internal class DefaultGoogleSheetsClient : GoogleSheets {
    override suspend fun sendReport(teamReport: GoogleSheetsTeamReport) {
        println("TODO setup Google Sheets API")
    }
}
