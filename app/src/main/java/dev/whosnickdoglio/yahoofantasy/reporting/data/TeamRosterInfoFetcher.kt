// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data

internal interface TeamRosterInfoFetcher {
    suspend fun fetchRosterInfo(teamId: Int): RosterInfo
}

internal data class RosterInfo(
    val name: String,
    val players: List<PlayerRowRawInfo>,
    val id: Int,
    val url: String,
)

// TODO different DTO here
internal data class PlayerRowRawInfo(
    val position: String?,
    val playerName: String?,
    val healthStatus: PlayerHealthStatus,
    val positionEligibility: List<String?>?,
    val opponent: String?,
)

internal enum class PlayerHealthStatus(val value: String) {
    HEALTHY(""),
    LONG_TERM_INJURY("INJ"),
    SHORT_TERM_INJURY("O"),
}
