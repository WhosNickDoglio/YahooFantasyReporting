package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.select.Evaluator
import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.TeamRosterInfoFetcher
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.RosterInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char

@Inject
@ContributesBinding(AppScope::class)
internal class KsoupTeamRosterInfoFetcher(
    private val date: LocalDate,
    private val leagueInfo: LeagueInfo,
) : TeamRosterInfoFetcher {

    override suspend fun fetchRosterInfo(teamId: Int): RosterInfo {
        // TODO this feels bad
        val formattedDate = date.format(LocalDate.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            day()
        })

        val url = "${leagueInfo.baseUrl}/$teamId/team?&date=$formattedDate"
        val doc = Ksoup.parseGetRequest(url)

        val table = doc.select(Evaluator.Id("statTable0"))

        val rows: List<Element> = table.select("tr")

        return RosterInfo(
            name = doc.title().substringAfter("-").substringBefore("|").trim(),
            players = rows.map { it.toPlayerRowRawInfo() }.filter { it.playerName?.isNotEmpty() == true }
                .filter { it.playerName != "Players" },
            url = url,
        )
    }
}
