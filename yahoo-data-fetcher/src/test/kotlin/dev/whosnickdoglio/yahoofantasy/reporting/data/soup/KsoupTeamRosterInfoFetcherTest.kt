// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.whosnickdoglio.yahoofantasy.reporting.data.FakeLeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.Player
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.RosterInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.getResourceAsText
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

// TODO add more tests
class KsoupTeamRosterInfoFetcherTest {

    private fun TestScope.createKsoupTeamFetcher(
        data: (url: String) -> String,
        date: LocalDate = LocalDate.of(2025, 10, 24),
        leagueInfo: LeagueInfo = FakeLeagueInfo(),
        ioContext: CoroutineContext = this.coroutineContext,
        documentFetcher: HtmlDocumentFetcher = FakeHtmlDocumentFetcher(data),
    ): KsoupTeamRosterInfoFetcher =
        KsoupTeamRosterInfoFetcher(date, leagueInfo, ioContext, documentFetcher)

    @Test
    fun `data fetched matches up with the expected roster info`() = runTest {
        val fetcher = createKsoupTeamFetcher(data = { _ -> getResourceAsText("/mitch-amen.html") })
        val roster = fetcher.fetchRosterInfo(1)

        assertThat(roster)
            .isEqualTo(
                RosterInfo(
                    name = "Can I get an Amen?",
                    players =
                        listOf(
                            Player(
                                position = "PG",
                                playerName = "Lonzo Ball",
                                positionEligibility = listOf("PG", "SG"),
                            ),
                            Player(
                                position = "SG",
                                playerName = "Gary Trent Jr.",
                                positionEligibility = listOf("SG", "SF"),
                            ),
                            Player(
                                position = "G",
                                playerName = "Devin Vassell",
                                positionEligibility = listOf("SG", "SF"),
                            ),
                            Player(
                                position = "SF",
                                playerName = "Jimmy Butler III",
                                positionEligibility = listOf("SF", "PF"),
                                opponent = "DEN",
                            ),
                            Player(
                                position = "PF",
                                playerName = "Paolo Banchero",
                                positionEligibility = listOf("PF", "C"),
                            ),
                            Player(
                                position = "F",
                                playerName = "Santi Aldama",
                                positionEligibility = listOf("PF", "C"),
                            ),
                            Player(
                                position = "C",
                                playerName = "Isaiah Hartenstein",
                                positionEligibility = listOf("C"),
                                opponent = "@IND",
                            ),
                            Player(
                                position = "Util",
                                playerName = "Amen Thompson",
                                positionEligibility = listOf("PG", "SG", "SF"),
                            ),
                            Player(
                                position = "Util",
                                playerName = "Zaccharie Risacher",
                                healthStatus = PlayerHealthStatus.GAME_TIME_DECISION,
                                positionEligibility = listOf("SF", "PF"),
                            ),
                            Player(
                                position = "Util",
                                playerName = "Kyle Filipowski",
                                positionEligibility = listOf("PF", "C"),
                            ),
                            Player(
                                position = "BN",
                                playerName = "Quentin Grimes",
                                positionEligibility = listOf("SG", "SF"),
                            ),
                            Player(
                                position = "BN",
                                playerName = "Yves Missi",
                                healthStatus = PlayerHealthStatus.GAME_TIME_DECISION,
                                positionEligibility = listOf("C"),
                            ),
                            Player(
                                position = "BN",
                                playerName = "Luka Dončić",
                                positionEligibility = listOf("PG", "SG"),
                            ),
                            Player(
                                position = "IL+",
                                playerName = "Coby White",
                                healthStatus = PlayerHealthStatus.LONG_TERM_INJURY,
                                positionEligibility = listOf("PG", "SG"),
                            ),
                            Player(
                                position = "IL+",
                                playerName = "Jalen Williams",
                                healthStatus = PlayerHealthStatus.LONG_TERM_INJURY,
                                positionEligibility = listOf("SF", "PF"),
                                opponent = "@IND",
                            ),
                            Player(
                                position = "IL+",
                                playerName = "Scoot Henderson",
                                healthStatus = PlayerHealthStatus.LONG_TERM_INJURY,
                                "PG",
                            ),
                        ),
                    id = 1,
                    url = "example.com/1/team?&date=2025-10-24",
                )
            )
    }
}
