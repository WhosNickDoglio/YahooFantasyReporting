package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import dev.whosnickdoglio.yahoofantasy.reporting.data.FakeLeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import org.junit.Test

class InjuredPlayerOnBenchWithOpenInjuryListSpotCheckerTest {

    @Test
    fun `empty injury list with injured player on bench returns violation`() {
        val checker = InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(FakeLeagueInfo())
        val result = checker.check(
            Roster(
                Player(position = "BN", healthStatus = PlayerHealthStatus.INJURED)
            )
        )

        assertThat(result).isEqualTo(Violation.IL_PLAYER_ON_BENCH_WITH_OPEN_IL_SPOT)
    }

    @Test
    fun `full injury list with injured player on bench returns no violation`() {
        val checker = InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(FakeLeagueInfo())
        val result = checker.check(
            Roster(
                Player(healthStatus = PlayerHealthStatus.HEALTHY),
                Player(healthStatus = PlayerHealthStatus.INJURED),
                Player(position = "IL", healthStatus = PlayerHealthStatus.INJURED),
                Player(position = "IL", healthStatus = PlayerHealthStatus.INJURED),
                Player(position = "IL", healthStatus = PlayerHealthStatus.INJURED),
                Player(position = "IL", healthStatus = PlayerHealthStatus.INJURED),
            )
        )

        assertThat(result).isNull()
    }

    @Test
    fun `full injury list with no injured bench players returns no violations`() {
        val checker = InjuredPlayerOnBenchWithOpenInjuryListSpotChecker(FakeLeagueInfo())
        val result = checker.check(
            Roster(
                Player(healthStatus = PlayerHealthStatus.HEALTHY),
                Player(healthStatus = PlayerHealthStatus.HEALTHY),
                Player(position = "IL", healthStatus = PlayerHealthStatus.INJURED),
                Player(position = "IL", healthStatus = PlayerHealthStatus.INJURED),
                Player(position = "IL", healthStatus = PlayerHealthStatus.INJURED),
                Player(position = "IL", healthStatus = PlayerHealthStatus.INJURED),
            )
        )

        assertThat(result).isNull()
    }
}
