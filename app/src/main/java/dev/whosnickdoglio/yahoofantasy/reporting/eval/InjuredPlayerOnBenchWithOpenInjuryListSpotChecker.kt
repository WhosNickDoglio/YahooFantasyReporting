package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
internal class InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(private val leagueInfo: LeagueInfo) : RosterChecker {
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val injuredPlayersOnBench =
            roster.filter { rosterSpot -> rosterSpot.position == "BN" && rosterSpot.healthStatus == PlayerHealthStatus.INJURED }

        val injuryListSpotsTaken = roster.filter { rosterSpot -> rosterSpot.isOnInjuryList() }

        val hasOpenInjuryListSpots = injuryListSpotsTaken.size < leagueInfo.injuryListCount

        if (injuredPlayersOnBench.isNotEmpty() && hasOpenInjuryListSpots) {
            return Violation.IL_PLAYER_ON_BENCH_WITH_OPEN_IL_SPOT
        }

        return null
    }
}
