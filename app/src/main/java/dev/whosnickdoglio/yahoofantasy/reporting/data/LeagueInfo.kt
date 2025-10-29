// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data

// TODO dynamically get game key
internal interface LeagueInfo {
    val name: String
    val baseUrl: String
    val numberOfTeams: Int
    val injuryListCount: Int
    val spreadSheetName: String

    data object MitchRobLeagueInfo : LeagueInfo {
        override val name: String = "Mitch Rob"
        override val baseUrl: String = "https://basketball.fantasysports.yahoo.com/nba/9615"
        override val numberOfTeams: Int = 12
        override val spreadSheetName: String = "MitchRob"
        override val injuryListCount: Int = 4
    }

    data object Redacted : LeagueInfo {
        override val name: String = "Redacted"
        override val baseUrl: String = "https://basketball.fantasysports.yahoo.com/nba/69734"
        override val numberOfTeams: Int = 12
        override val spreadSheetName: String = "Redacted"
        override val injuryListCount: Int = 4
    }

    data object BirthdayCakeOreo : LeagueInfo {
        override val name: String = "Birthday Cake"
        override val baseUrl: String = "https://basketball.fantasysports.yahoo.com/nba/9616"
        override val numberOfTeams: Int = 10
        override val spreadSheetName: String = "Birthday"
        override val injuryListCount: Int = 4
    }
}

internal data class SpreadSheetInfo(val range: String)
