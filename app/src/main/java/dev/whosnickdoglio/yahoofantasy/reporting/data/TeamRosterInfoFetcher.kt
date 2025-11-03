// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data

import kotlinx.serialization.Serializable

internal interface TeamRosterInfoFetcher {
    suspend fun fetchRosterInfo(teamId: Int): RosterInfo
}

@Serializable
internal data class RosterInfo(
    val name: String,
    val players: List<PlayerRowRawInfo>,
    val id: Int,
    val url: String,
)

// TODO different DTO here
@Serializable
internal data class PlayerRowRawInfo(
    val position: String?,
    val playerName: String?,
    val healthStatus: PlayerHealthStatus,
    val positionEligibility: List<String?>?,
    val opponent: String?,
)

internal enum class PlayerHealthStatus(val value: String) {
    HEALTHY(""),
    GAME_TIME_DECISION("GTD"),
    LONG_TERM_INJURY("INJ"),
    SHORT_TERM_INJURY("O"),
}
