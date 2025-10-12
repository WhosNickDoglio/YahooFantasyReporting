package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@ContributesIntoSet(AppScope::class)
internal class InjuredPlayerOnBenchWithOpenInjuryListSpotChecker : RosterChecker {
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val injuredPlayersOnBench =
            roster.filter { rosterSpot -> rosterSpot.position == "BN" && rosterSpot.healthStatus == PlayerHealthStatus.INJURED }

        // TODO probably need RosterInformation here
        val hasOpenInjuryListSpots = false

        if (injuredPlayersOnBench.isNotEmpty() && hasOpenInjuryListSpots) {
            return Violation.IL_PLAYER_ON_BENCH_WITH_OPEN_IL_SPOT
        }

        return null
    }
}
