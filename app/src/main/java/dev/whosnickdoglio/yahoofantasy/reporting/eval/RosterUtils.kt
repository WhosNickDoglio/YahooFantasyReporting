package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo

internal fun PlayerRowRawInfo.hasGameToday(): Boolean = opponent?.isNotEmpty() == true

internal fun PlayerRowRawInfo.isAvailableStartingSpot(): Boolean {
    val isEmpty = playerName?.contains("Empty") == true
    val isStarting = isStarting()
    val hasNoOpponent = opponent == null
    return isStarting && (isEmpty || hasNoOpponent)
}

internal fun PlayerRowRawInfo.isStarting(): Boolean = !position.equals("BN") && !isOnInjuryList()

internal fun PlayerRowRawInfo.isOnInjuryList(): Boolean = position?.contains("IL") == true
