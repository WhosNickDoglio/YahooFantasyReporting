// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo


// TODO do I need this
internal fun Roster(
    vararg players: PlayerRowRawInfo = emptyArray(),
): List<PlayerRowRawInfo> = buildList {
        addAll(players.toSet())
    }

internal fun EmptyRosterSpot(
    position: String = "G"
): PlayerRowRawInfo = Player(
    position = position,
    playerName = "(Empty)",
    positionEligibility = null,
)

internal fun Player(
    position: String = "Util",
    playerName: String = "Foo",
    healthStatus: PlayerHealthStatus = PlayerHealthStatus.HEALTHY,
    positionEligibility: List<String>? = listOf("G", "PG", "SG"),
    opponent: String? = null,
): PlayerRowRawInfo = PlayerRowRawInfo(
    position,
    playerName,
    healthStatus,
    positionEligibility,
    opponent
)
