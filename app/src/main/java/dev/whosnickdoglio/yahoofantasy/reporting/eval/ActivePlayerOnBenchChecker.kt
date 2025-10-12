package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@ContributesIntoSet(AppScope::class)
internal class ActivePlayerOnBenchChecker : RosterChecker {
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val emptyStartingSpots = roster.filter { it.isEmptyStartingSpot() }
        val hasBenchPlayersWhoCanStart =
            roster.filter { it.hasGameToday() && it.healthStatus == PlayerHealthStatus.HEALTHY && it.position == "BN" }

        val openPositions = emptyStartingSpots.map { it.position }

        val canMoveBenchPlayerToOpenPosition =
            hasBenchPlayersWhoCanStart.any { benchPlayer -> benchPlayer.positionEligibility?.any { position -> position in openPositions } == true }

        // account for Util not being in positionEligibility
        val benchPlayersEligiblePositions = hasBenchPlayersWhoCanStart.flatMap { it.positionEligibility.orEmpty() }
        // TODO need to figure out a good way to check if we can move things around to make it work

        return if (canMoveBenchPlayerToOpenPosition) {
            Violation.ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT
        } else {
            null
        }
    }
}
