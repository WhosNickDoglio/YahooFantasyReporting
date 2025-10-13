package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.TeamRosterInfoFetcher
import dev.whosnickdoglio.yahoofantasy.reporting.eval.EvaluationResult
import dev.whosnickdoglio.yahoofantasy.reporting.eval.RosterEvaluator
import dev.whosnickdoglio.yahoofantasy.reporting.sheets.GoogleSheets
import dev.whosnickdoglio.yahoofantasy.reporting.sheets.GoogleSheetsTeamReport
import dev.zacsweers.metro.Inject
import kotlinx.datetime.LocalDate

@Inject
internal class App(
    private val fetcher: TeamRosterInfoFetcher,
    private val yesterday: LocalDate,
    private val leagueInfo: LeagueInfo,
    private val rosterEvaluator: RosterEvaluator,
    private val googleSheets: GoogleSheets,
) {

    suspend fun main() {
        println("Checking rosters for $yesterday")
        val reports = mutableListOf<GoogleSheetsTeamReport>()
        for (i in 1..leagueInfo.numberOfTeams) {
            val rosterInfo = fetcher.fetchRosterInfo(teamId = i)
            val result = rosterEvaluator.evaluate(rosterInfo.players)
            println(
                when (result) {
                    is EvaluationResult.SetRoster -> "Roster is set for ${rosterInfo.name}! ${rosterInfo.url}"
                    is EvaluationResult.UnsetRoster -> "${rosterInfo.name} has violations: ${result.violations.joinToString()} ${rosterInfo.url}"
                }
            )

            if (result is EvaluationResult.UnsetRoster) {
                reports.add(
                    GoogleSheetsTeamReport(
                        date = yesterday,
                        teamName = rosterInfo.name,
                        violation = emptyList(),
                        url = rosterInfo.url,
                    )
                )
            }
        }
        println("Reporting to Google Sheets...")
        googleSheets.sendReport(reports)
    }
}
