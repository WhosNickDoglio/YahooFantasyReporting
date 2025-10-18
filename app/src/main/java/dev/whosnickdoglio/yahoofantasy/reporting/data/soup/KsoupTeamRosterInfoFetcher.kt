package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.select.Evaluator
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.TeamRosterInfoFetcher
import dev.whosnickdoglio.yahoofantasy.reporting.data.RosterInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

@ContributesBinding(AppScope::class)
internal class KsoupTeamRosterInfoFetcher(
    private val date: LocalDate,
    private val leagueInfo: LeagueInfo,
) : TeamRosterInfoFetcher {

    override suspend fun fetchRosterInfo(teamId: Int): RosterInfo {
        val url = "${leagueInfo.baseUrl}/$teamId/team?&date=${date.format(LocalDate.Formats.ISO)}"
        val doc = Ksoup.parseGetRequest(url)
        val table = doc.select(Evaluator.Id("statTable0"))
        val rows: List<Element> = table.select("tr")

        return RosterInfo(
            name = doc.title().substringAfterLast("-").substringBefore("|").trim(),
            players = rows.map { it.toPlayerRowRawInfo() }.filter { it.playerName?.isNotEmpty() == true }
                .filter { it.playerName != "Players" },
            url = url,
        )
    }
}
