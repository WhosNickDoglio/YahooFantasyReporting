package dev.whosnickdoglio.yahoofantasy.reporting.eval

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import org.junit.Test

class InjuredPlayersInStartingLineupCheckerTest {

    private val checker = InjuredPlayersInStartingLineupChecker()

    @Test
    fun `starting lineup is empty`() {
        val result = checker.check(Roster(starters = buildList {
            addAll(List(10) {
                EmptyRosterSpot()
            })
        }))
        assertThat(result).isNull()
    }

    @Test
    fun `starting lineup only has healthy players on it`() {
        val result = checker.check(Roster(injuryList = buildList {
            add(Player(position = "PG"))
            add(Player(position = "G"))
            add(Player(position = "Util"))
            add(Player(position = "C"))
        }))
        assertThat(result).isNull()
    }

    @Test
    fun `starting lineup  has a injured player on it`() {
        val result = checker.check(Roster(injuryList = buildList {
            add(Player(position = "PG", healthStatus = PlayerHealthStatus.INJURED))
            add(Player(position = "G"))
            add(Player(position = "Util"))
            add(Player(position = "C"))
        }))
        assertThat(result).isEqualTo(Violation.IL_IN_STARTING_LINEUP)
    }

}
