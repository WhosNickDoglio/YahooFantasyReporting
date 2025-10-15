package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.di.AppDependencyGraph
import dev.zacsweers.metro.createGraphFactory
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
public suspend fun main(args: Array<String>) {
    val leagueInfo = when (val league = args.first()) {
        "mitch" -> LeagueInfo.MitchRobLeagueInfo
        "redacted" -> LeagueInfo.Redacted
        "birthday" -> LeagueInfo.BirthdayCakeOreo
        else -> error("Unknown league $league")
    }

    val yesterday = Clock.System.now().toLocalDateTime(TimeZone.UTC).date.minus(DatePeriod(
        years = 0,
        months = 0,
        days = 1
    ))
    val graph = createGraphFactory<AppDependencyGraph.Factory>().create(
        yesterday = yesterday,
        leagueInfo = leagueInfo
    )
    graph.app.main()
}
