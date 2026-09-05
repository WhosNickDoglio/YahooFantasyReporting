// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.data

// TODO dynamically get game key
public interface LeagueInfo {
    public val name: String
    public val sport: Sport
    public val baseUrl: String
    public val numberOfTeams: Int
    public val injuryListCount: Int
    public val spreadSheetName: String

    public data object MitchRobLeagueInfo : LeagueInfo {
        override val name: String = "mitch"
        override val sport: Sport = Sport.Basketball
        override val baseUrl: String = "${sport.url}/9615"
        override val numberOfTeams: Int = 12
        override val spreadSheetName: String = "MitchRob"
        override val injuryListCount: Int = 4
    }

    public data object Redacted : LeagueInfo {
        override val name: String = "redacted"
        override val sport: Sport = Sport.Basketball
        override val baseUrl: String = "${sport.url}/69734"
        override val numberOfTeams: Int = 12
        override val spreadSheetName: String = "Redacted"
        override val injuryListCount: Int = 4
    }

    public data object BirthdayCakeOreo : LeagueInfo {
        override val name: String = "birthday"
        override val sport: Sport = Sport.Basketball
        override val baseUrl: String = "${sport.url}/9616"
        override val numberOfTeams: Int = 10
        override val spreadSheetName: String = "Birthday"
        override val injuryListCount: Int = 4
    }
}

public enum class Sport(public val url: String) {
    Basketball("https://basketball.fantasysports.yahoo.com/nba"),
    Football("https://football.fantasysports.yahoo.com/f1/"),
}
