package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.di.AppDependencyGraph
import dev.zacsweers.metro.createGraphFactory
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
public suspend fun main(args: Array<String>) {
    val leagueInfo = LeagueInfo.MitchRobLeagueInfo
    // 2025-10-24
    // 2025-10-21
//    val yesterday = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    val yesterday = LocalDate(year = 2025, month = 10, day = 24)
    val graph = createGraphFactory<AppDependencyGraph.Factory>().create(yesterday, leagueInfo)
    graph.app.main()
}
