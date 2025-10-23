// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo

internal fun PlayerRowRawInfo.hasGameToday(): Boolean = opponent?.isNotEmpty() == true

internal fun PlayerRowRawInfo.isAvailableStartingSpot(): Boolean {
    val isEmpty = playerName?.contains("Empty") == true
    val isStarting = isStarting()
    return isStarting && (isEmpty || !hasGameToday())
}

internal fun PlayerRowRawInfo.isStarting(): Boolean = !position.equals("BN") && !isOnInjuryList()

internal fun PlayerRowRawInfo.isOnInjuryList(): Boolean = position?.contains("IL") == true

private val guardEligiblePositions = listOf("PG", "SG")
private val forwardEligiblePositions = listOf("SF", "PF")

internal fun PlayerRowRawInfo.fullPositionalEligibility(): List<String> = buildList {
    val defaultEligibility = positionEligibility?.filterNotNull().orEmpty()
    addAll(defaultEligibility)

    if (guardEligiblePositions.any { defaultEligibility.contains(it) }) {
        add("G")
    }

    if (forwardEligiblePositions.any { defaultEligibility.contains(it) }) {
        add("F")
    }

    // free for all, any position
    add("Util")
}
