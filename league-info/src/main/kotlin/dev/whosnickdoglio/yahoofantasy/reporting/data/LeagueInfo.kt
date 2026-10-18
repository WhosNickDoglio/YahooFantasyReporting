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

    public val spreadSheetId: String
        // Defaults to our basketball spreadsheet
        get() = "1JjpGWRH7ZOK771X022kP3JEy3EvT4hJgjEMCsHmiXjI"

    public data object JHart : LeagueInfo {
        override val name: String = "jhart"
        override val sport: Sport = Sport.Basketball
        override val baseUrl: String = "${sport.url}/1090"
        override val numberOfTeams: Int = 12
        override val spreadSheetName: String = "JHart"
        override val injuryListCount: Int = 4
    }

    public data object Redacted : LeagueInfo {
        override val name: String = "redacted"
        override val sport: Sport = Sport.Basketball
        override val baseUrl: String = "${sport.url}/56582"
        override val numberOfTeams: Int = 12
        override val spreadSheetName: String = "Redacted"
        override val injuryListCount: Int = 4
    }

    public data object BirthdayCakeOreo : LeagueInfo {
        override val name: String = "birthday"
        override val sport: Sport = Sport.Basketball
        override val baseUrl: String = "${sport.url}/1088"
        override val numberOfTeams: Int = 10
        override val spreadSheetName: String = "Birthday"
        override val injuryListCount: Int = 4
    }

    public data object PeoplesRepublic : LeagueInfo {
        override val name: String = "peoples"
        override val sport: Sport = Sport.Football
        override val baseUrl: String = "${sport.url}/256972"
        override val numberOfTeams: Int = 12
        override val injuryListCount: Int = 2
        override val spreadSheetName: String = "2026-2027 Season"
        override val spreadSheetId: String = "1gI9gSdXA574QB8hK3MthEgM27OQe3bNtNbatUtbr_rs"
    }
}

public enum class Sport(public val url: String) {
    Basketball("https://basketball.fantasysports.yahoo.com/nba"),
    Football("https://football.fantasysports.yahoo.com/f1/"),
}
