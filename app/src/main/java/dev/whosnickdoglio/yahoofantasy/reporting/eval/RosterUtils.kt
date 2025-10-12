package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo

internal fun PlayerRowRawInfo.hasGameToday(): Boolean = opponent?.isNotEmpty() == true

internal fun PlayerRowRawInfo.isEmptyStartingSpot(): Boolean {
    val isEmpty = playerName?.contains("Empty") == true
    val isStarting = isStarting()
    return isEmpty && isStarting
}

internal fun PlayerRowRawInfo.isStarting(): Boolean = !position.equals("BN") && !isOnInjuryList()

internal fun PlayerRowRawInfo.isOnInjuryList(): Boolean = position?.contains("IL") == true
