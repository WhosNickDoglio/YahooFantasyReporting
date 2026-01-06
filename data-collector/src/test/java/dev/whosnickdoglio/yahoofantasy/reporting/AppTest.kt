// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting

import com.anthonycr.mockingbird.core.fake
import com.anthonycr.mockingbird.core.verify
import com.anthonycr.mockingbird.core.verifyComplete
import dev.whosnickdoglio.yahoofantasy.reporting.data.EmptyRosterSpot
import dev.whosnickdoglio.yahoofantasy.reporting.data.FakeLeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.Player
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.RosterInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.TeamRosterInfoFetcher
import dev.whosnickdoglio.yahoofantasy.reporting.eval.ActivePlayerOnBenchChecker
import dev.whosnickdoglio.yahoofantasy.reporting.eval.RosterEvaluator
import dev.whosnickdoglio.yahoofantasy.reporting.sheets.GoogleSheets
import dev.whosnickdoglio.yahoofantasy.reporting.sheets.GoogleSheetsTeamReport
import dev.whosnickdoglio.yahoofantasy.reporting.util.log.SimpleLogger
import dev.whosnickdoglio.yahoofantasy.reporting.util.results.InputWriter
import java.time.LocalDate
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AppTest {

    private val sheetsClient: GoogleSheets = fake()

    private val logger: SimpleLogger = fake()

    private val writer: InputWriter = fake()

    @Suppress("LongParameterList")
    private fun TestApp(
        fetcher: TeamRosterInfoFetcher = FakeTeamRosterInfoFetcher(),
        date: LocalDate = LocalDate.of(2025, 11, 12),
        leagueInfo: LeagueInfo = FakeLeagueInfo(),
        rosterEvaluator: RosterEvaluator = RosterEvaluator(emptySet()),
        googleSheets: GoogleSheets = sheetsClient,
        simpleLogger: SimpleLogger = logger,
        inputWriter: InputWriter = writer,
    ): App =
        App(fetcher, date, leagueInfo, rosterEvaluator, googleSheets, simpleLogger, inputWriter)

    @Test
    fun `google sheets api call is not made when there are no violations`() = runTest {
        val app = TestApp(fetcher = FakeTeamRosterInfoFetcher())
        app()
        sheetsClient.verifyComplete()
    }

    @Test
    fun `google sheets api call is made when there are violations`() = runTest {
        val app =
            TestApp(
                fetcher =
                    FakeTeamRosterInfoFetcher(
                        response = { id ->
                            if (id == 12) {
                                Info(
                                    id = 12,
                                    players =
                                        listOf(
                                            EmptyRosterSpot(position = "PG"),
                                            Player(position = "BN", opponent = "CLE"),
                                        ),
                                )
                            } else {
                                Info()
                            }
                        }
                    ),
                rosterEvaluator =
                    RosterEvaluator(rosterCheckers = setOf(ActivePlayerOnBenchChecker())),
            )
        app()
        verify(sheetsClient) {
            sheetsClient.sendReport(
                listOf(
                    GoogleSheetsTeamReport(
                        date = LocalDate.of(2025, 11, 12),
                        teamName = "foo",
                        healthyOnInjuryList = 0,
                        activePlayerOnBenchWithOpenStartingSpot = 1,
                        injuredPlayerInStartingLineup = 0,
                        injuredPlayerOnBenchWithOpenInjuryListSpot = 0,
                        teamId = 12,
                        url = "foo.com",
                    )
                )
            )
        }
    }

    @Test
    fun `logger calls are made when there are no violations`() = runTest {
        val app = TestApp(fetcher = FakeTeamRosterInfoFetcher())
        app()
        verify(logger) {
            logger.log("Checking rosters in Mitch Rob for 2025-11-12")
            (1..FakeLeagueInfo().numberOfTeams).forEach { _ ->
                logger.log("Roster is set for foo! foo.com")
            }
            logger.log("No violations found 2025-11-12")
        }
    }

    @Test
    fun `logger calls are made when there are violations`() = runTest {
        val app =
            TestApp(
                fetcher =
                    FakeTeamRosterInfoFetcher(
                        response = { id ->
                            if (id == 12) {
                                Info(
                                    id = 12,
                                    players =
                                        listOf(
                                            EmptyRosterSpot(position = "PG"),
                                            Player(position = "BN", opponent = "CLE"),
                                        ),
                                )
                            } else {
                                Info()
                            }
                        }
                    ),
                rosterEvaluator =
                    RosterEvaluator(rosterCheckers = setOf(ActivePlayerOnBenchChecker())),
            )
        app()
        verify(logger) {
            logger.log("Checking rosters in Mitch Rob for 2025-11-12")
            (1..<FakeLeagueInfo().numberOfTeams).forEach { _ ->
                logger.log("Roster is set for foo! foo.com")
            }
            logger.log(
                "foo has violations: ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT foo.com"
            )
            logger.log("Reporting to Google Sheets...")
        }
    }

    @Test
    fun `input writer is called once when the app is run`() = runTest {
        val app = TestApp(fetcher = FakeTeamRosterInfoFetcher())
        app()
        verify(writer) { writer.write(any()) }
    }
}

private class FakeTeamRosterInfoFetcher(
    private val response: (teamId: Int) -> RosterInfo = { Info() }
) : TeamRosterInfoFetcher {
    override suspend fun fetchRosterInfo(teamId: Int): RosterInfo = response(teamId)
}

private fun Info(
    name: String = "foo",
    players: List<PlayerRowRawInfo> = emptyList(),
    id: Int = 0,
    url: String = "foo.com",
): RosterInfo = RosterInfo(name, players, id, url)
