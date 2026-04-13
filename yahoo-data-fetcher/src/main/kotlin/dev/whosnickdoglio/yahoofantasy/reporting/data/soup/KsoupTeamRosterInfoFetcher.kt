// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import com.fleeksoft.ksoup.select.Evaluator
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.RosterInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.TeamRosterInfoFetcher
import dev.whosnickdoglio.yahoofantasy.reporting.util.coroutines.IoDispatcher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

@ContributesBinding(AppScope::class)
internal class KsoupTeamRosterInfoFetcher(
    private val date: LocalDate,
    private val leagueInfo: LeagueInfo,
    @param:IoDispatcher private val ioCoroutineContext: CoroutineContext,
    private val htmlDocumentFetcher: HtmlDocumentFetcher,
) : TeamRosterInfoFetcher {

    override suspend fun fetchRosterInfo(teamId: Int): RosterInfo =
        withContext(ioCoroutineContext) {
            val url = "${leagueInfo.baseUrl}/$teamId/team?&date=$date"
            val doc = htmlDocumentFetcher.fetchDocument(url)
            val table = doc.select(Evaluator.Id("statTable0"))
            val rows = table.select("tr")

            var opponentIndex: Int? = null

            return@withContext RosterInfo(
                name = doc.title().substringAfterLast("-").substringBefore("|").trim(),
                players =
                    rows.mapNotNull { element ->
                        element.toPlayerRowRawInfo(
                            setOpponentIndex = { index -> opponentIndex = index },
                            getOpponentIndex = { opponentIndex ?: -1 },
                        )
                    },
                id = teamId,
                url = url,
            )
        }
}
