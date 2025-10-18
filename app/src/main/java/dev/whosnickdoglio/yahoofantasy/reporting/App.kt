package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.TeamRosterInfoFetcher
import dev.whosnickdoglio.yahoofantasy.reporting.eval.EvaluationResult
import dev.whosnickdoglio.yahoofantasy.reporting.eval.RosterEvaluator
import dev.whosnickdoglio.yahoofantasy.reporting.eval.Violation
import dev.whosnickdoglio.yahoofantasy.reporting.log.SimpleLogger
import dev.whosnickdoglio.yahoofantasy.reporting.sheets.GoogleSheets
import dev.whosnickdoglio.yahoofantasy.reporting.sheets.GoogleSheetsTeamReport
import dev.zacsweers.metro.Inject
import java.time.LocalDate

@Inject
internal class App(
    private val fetcher: TeamRosterInfoFetcher,
    private val yesterday: LocalDate,
    private val leagueInfo: LeagueInfo,
    private val rosterEvaluator: RosterEvaluator,
    private val googleSheets: GoogleSheets,
    private val logger: SimpleLogger,
) {

    suspend fun main() {
        logger.log("Checking rosters for $yesterday")
        val reports = mutableListOf<GoogleSheetsTeamReport>()
        for (i in 1..leagueInfo.numberOfTeams) {
            val rosterInfo = fetcher.fetchRosterInfo(teamId = i)
            val result = rosterEvaluator.evaluate(rosterInfo.players)
            logger.log(
                when (result) {
                    is EvaluationResult.SetRoster -> "Roster is set for ${rosterInfo.name}! ${rosterInfo.url}"
                    is EvaluationResult.UnsetRoster -> "${rosterInfo.name} has violations: ${result.violations.joinToString()} ${rosterInfo.url}"
                }
            )

            if (result is EvaluationResult.UnsetRoster) {
                val violations = result.violations
                reports.add(
                    GoogleSheetsTeamReport(
                        date = yesterday,
                        teamName = rosterInfo.name,
                        healthyOnInjuryList = violations.contains(Violation.HEALTHY_ON_IL),
                        activePlayerOnBenchWithOpenStartingSpot = violations.contains(Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT),
                        injuredPlayerInStartingLineup = violations.contains(Violation.IL_IN_STARTING_LINEUP),
                        injuredPlayerOnBenchWithOpenInjuryListSpot = violations.contains(Violation.IL_PLAYER_ON_BENCH_WITH_OPEN_IL_SPOT),
                        url = rosterInfo.url,
                    )
                )
            }
        }
        if (reports.isNotEmpty()) {
            logger.log("Reporting to Google Sheets...")
            googleSheets.sendReport(reports)
        } else {
            logger.log("No violations found $yesterday")
        }
    }
}
