// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.select.Evaluator
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.RosterInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.TeamRosterInfoFetcher
import dev.whosnickdoglio.yahoofantasy.reporting.util.coroutines.CoroutineDispatcherProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import java.time.LocalDate
import kotlinx.coroutines.withContext

@ContributesBinding(AppScope::class)
internal class KsoupTeamRosterInfoFetcher(
    private val date: LocalDate,
    private val leagueInfo: LeagueInfo,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : TeamRosterInfoFetcher {

    override suspend fun fetchRosterInfo(teamId: Int): RosterInfo =
        withContext(coroutineDispatcherProvider.io) {
            val url = "${leagueInfo.baseUrl}/$teamId/team?&date=$date"
            val doc = Ksoup.parseGetRequest(url)
            val table = doc.select(Evaluator.Id("statTable0"))
            val rows: List<Element> = table.select("tr")

            return@withContext RosterInfo(
                name = doc.title().substringAfterLast("-").substringBefore("|").trim(),
                players = rows.mapNotNull { it.toPlayerRowRawInfo() },
                url = url,
            )
        }
}
