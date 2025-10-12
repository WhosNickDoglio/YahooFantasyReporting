package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.soup.PlayerHealthStatus
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject


@Inject
@ContributesIntoSet(AppScope::class)
internal class HealthyPlayerOnInjuryListCheck : RosterChecker {
    override fun check(roster: List<PlayerRowRawInfo>): Violation? {
        val healthyPlayersOnInjuryList =
            roster.filter { it.position == "IL" && it.healthStatus == PlayerHealthStatus.HEALTHY }

        return if (healthyPlayersOnInjuryList.isNotEmpty()) {
            Violation.HEALTHY_ON_IL
        } else {
            null
        }
    }
}
