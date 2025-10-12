package dev.whosnickdoglio.yahoofantasy.reporting.data

// TODO dynamically get game key
internal sealed interface LeagueInfo {
    val name: String
    val baseUrl: String
    val numberOfTeams: Int

    data object MitchRobLeagueInfo : LeagueInfo {
        override val name: String = "Mitch Rob"
        override val baseUrl: String = "https://basketball.fantasysports.yahoo.com/nba/197286"
        override val numberOfTeams: Int = 12
    }
}
