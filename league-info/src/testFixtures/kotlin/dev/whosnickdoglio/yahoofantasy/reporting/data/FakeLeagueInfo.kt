// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.data

data class FakeLeagueInfo(
    override val name: String = "Mitch Rob",
    override val baseUrl: String = "example.com",
    override val sport: Sport = Sport.Basketball,
    override val numberOfTeams: Int = 12,
    override val injuryListCount: Int = 4,
    override val spreadSheetName: String = "MitchRob",
) : LeagueInfo
