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

internal fun PlayerRowRawInfo.fullPositionalEligibility(): List<String> = buildList {
    addAll(positionEligibility?.filterNotNull().orEmpty())
    // free for all, any position
    add("Util")
}
