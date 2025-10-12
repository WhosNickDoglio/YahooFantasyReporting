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
        for (i in 1..leagueInfo.numberOfTeams) {
            val rosterInfo = fetcher.fetchRosterInfo(i)
            val evaluation = rosterEvaluator.evaluate(rosterInfo.players)
            println(
                when (evaluation) {
                    is EvaluationResult.SetRoster -> "Roster is set for ${rosterInfo.name}! ${rosterInfo.url}"
                    is EvaluationResult.UnsetRoster -> "${rosterInfo.name} has violations: ${evaluation.violations.joinToString()} ${rosterInfo.url}"
                }
            )

            if (evaluation is EvaluationResult.UnsetRoster) {
                println("Reporting to Google Sheets...")
                googleSheets.sendReport(
                    GoogleSheetsTeamReport(
                        date = yesterday,
                        teamName = rosterInfo.name,
                        violation = evaluation.violations,
                        url = rosterInfo.url,
                    )
                )
            }
        }
    }
}
