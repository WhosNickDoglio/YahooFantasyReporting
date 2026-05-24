// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.TeamRosterInfoFetcher
import dev.whosnickdoglio.yahoofantasy.reporting.eval.EvaluationResult
import dev.whosnickdoglio.yahoofantasy.reporting.eval.RosterEvaluator
import dev.whosnickdoglio.yahoofantasy.reporting.eval.Violation
import dev.whosnickdoglio.yahoofantasy.reporting.sheets.GoogleSheets
import dev.whosnickdoglio.yahoofantasy.reporting.sheets.GoogleSheetsTeamReport
import dev.whosnickdoglio.yahoofantasy.reporting.sheets.toInt
import dev.whosnickdoglio.yahoofantasy.reporting.util.log.SimpleLogger
import dev.zacsweers.metro.Inject
import java.time.LocalDate

@Suppress("LongParameterList")
@Inject
internal class App(
    private val fetcher: TeamRosterInfoFetcher,
    private val yesterday: LocalDate,
    private val leagueInfo: LeagueInfo,
    private val rosterEvaluator: RosterEvaluator,
    private val googleSheets: GoogleSheets,
    private val logger: SimpleLogger,
) {

    suspend operator fun invoke() {
        logger.log("Checking rosters in ${leagueInfo.name} for $yesterday")

        val rosterInfo =
            (1..leagueInfo.numberOfTeams).toList().map { id -> fetcher.fetchRosterInfo(id) }

        val reports =
            rosterInfo
                .map { info -> Pair(info, rosterEvaluator.evaluate(info.players)) }
                .onEach { (info, result) ->
                    logger.log(
                        when (result) {
                            is EvaluationResult.SetRoster ->
                                "Roster is set for ${info.name}! ${info.url}"
                            is EvaluationResult.UnsetRoster ->
                                "${info.name} has violations: ${result.violations.joinToString()} ${info.url}"
                        }
                    )
                }
                .filter { pair -> pair.second is EvaluationResult.UnsetRoster }
                .map { (info, result) ->
                    require(result is EvaluationResult.UnsetRoster)
                    val violations = result.violations
                    GoogleSheetsTeamReport(
                        date = yesterday,
                        teamName = info.name,
                        healthyOnInjuryList = violations.contains(Violation.HEALTHY_ON_IL).toInt(),
                        activePlayerOnBenchWithOpenStartingSpot =
                            violations
                                .contains(
                                    Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT
                                )
                                .toInt(),
                        injuredPlayerInStartingLineup =
                            violations.contains(Violation.IL_IN_STARTING_LINEUP).toInt(),
                        injuredPlayerOnBenchWithOpenInjuryListSpot =
                            violations
                                .contains(Violation.IL_PLAYER_ON_BENCH_WITH_OPEN_IL_SPOT)
                                .toInt(),
                        teamId = info.id,
                        url = info.url,
                    )
                }

        if (reports.isNotEmpty()) {
            logger.log("Reporting to Google Sheets...")
            googleSheets.sendReport(reports)
        } else {
            logger.log("No violations found $yesterday")
        }
    }
}
