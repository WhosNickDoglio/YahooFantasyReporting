package dev.whosnickdoglio.yahoofantasy.reporting.data

internal interface TeamRosterInfoFetcher {
    suspend fun fetchRosterInfo(teamId: Int): RosterInfo
}

internal data class RosterInfo(
    val name: String, val players: List<PlayerRowRawInfo>, val url: String
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
    HEALTHY(""), GAME_TIME_DECISION("GTD"), INJURED("INJ")
}
