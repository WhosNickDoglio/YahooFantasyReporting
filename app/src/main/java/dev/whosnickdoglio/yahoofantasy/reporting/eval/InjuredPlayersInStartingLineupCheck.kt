package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.soup.PlayerHealthStatus
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@ContributesIntoSet(AppScope::class)
internal class InjuredPlayersInStartingLineupCheck: RosterChecker {
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val injuredPlayersStarting =
            roster.filter { it.isStarting() && it.healthStatus == PlayerHealthStatus.INJURED }

        return if (injuredPlayersStarting.isNotEmpty()) {
            Violation.IL_IN_STARTING_LINEUP
        } else {
            null
        }
    }
}
