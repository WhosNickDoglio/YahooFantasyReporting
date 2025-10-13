package dev.whosnickdoglio.yahoofantasy.reporting.data

// TODO dynamically get game key
internal sealed interface LeagueInfo {
    val name: String
    val baseUrl: String
    val numberOfTeams: Int
    val spreadSheetInfo: SpreadSheetInfo

    data object MitchRobLeagueInfo : LeagueInfo {
        override val name: String = "Mitch Rob"
        override val baseUrl: String = "https://basketball.fantasysports.yahoo.com/nba/197286"
        override val numberOfTeams: Int = 10
        override val spreadSheetInfo: SpreadSheetInfo = SpreadSheetInfo(
            id = "11LQZQF2CDX4lmChkryV00aD6I2XTi3Fp4BmqNVwtuEI",
            range = "MitchRob!A1:E1"
        )
    }
}


internal data class SpreadSheetInfo(
    val id: String,
    val range: String,
)
