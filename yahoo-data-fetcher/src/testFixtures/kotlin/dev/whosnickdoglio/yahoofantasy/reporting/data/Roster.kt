// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data

fun EmptyRosterSpot(position: String = "G"): PlayerRowRawInfo =
    Player(position = position, playerName = "(Empty)", positionEligibility = emptyArray())

fun Player(
    position: String = "Util",
    playerName: String = "Foo",
    healthStatus: PlayerHealthStatus = PlayerHealthStatus.HEALTHY,
    vararg positionEligibility: String = arrayOf("G", "PG", "SG"),
    hasOpponent: Boolean = false,
): PlayerRowRawInfo =
    PlayerRowRawInfo(
        position = position,
        playerName = playerName,
        healthStatus = healthStatus,
        positionEligibility = positionEligibility.toList(),
        opponent = if (hasOpponent) "CLE" else "",
    )

fun Player(
    position: String = "Util",
    playerName: String = "Foo",
    healthStatus: PlayerHealthStatus = PlayerHealthStatus.HEALTHY,
    positionEligibility: List<String> = listOf("G", "PG", "SG"),
    opponent: String? = "",
): PlayerRowRawInfo =
    PlayerRowRawInfo(
        position = position,
        playerName = playerName,
        healthStatus = healthStatus,
        positionEligibility = positionEligibility,
        opponent = opponent,
    )
