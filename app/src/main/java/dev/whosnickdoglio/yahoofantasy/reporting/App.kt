package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.TeamRosterInfoFetcher
import dev.whosnickdoglio.yahoofantasy.reporting.eval.EvaluationResult
import dev.whosnickdoglio.yahoofantasy.reporting.eval.RosterEvaluator
import dev.zacsweers.metro.Inject
import kotlinx.datetime.LocalDate

@Inject
internal class App(
    private val fetcher: TeamRosterInfoFetcher,
    private val yesterday: LocalDate,
    private val leagueInfo: LeagueInfo,
    private val rosterEvaluator: RosterEvaluator,
) {

    suspend fun main() {
        println("Checking rosters for $yesterday")

//    for (i in 1..leagueInfo.numberOfTeams) {
        for (i in 1..10) {
            val rosterInfo = fetcher.fetchRosterInfo(i)
            val report = when (val evaluation = rosterEvaluator.evaluate(rosterInfo.players)) {
                EvaluationResult.SetRoster -> "Roster is set for ${rosterInfo.name}! ${rosterInfo.url}"
                is EvaluationResult.UnsetRoster -> "${rosterInfo.name} has violations: ${evaluation.violations.joinToString()} ${rosterInfo.url}"
            }
            println(report)
        }
    }
}
