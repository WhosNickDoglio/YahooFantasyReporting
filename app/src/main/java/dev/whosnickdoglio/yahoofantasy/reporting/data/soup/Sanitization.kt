// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import com.fleeksoft.ksoup.nodes.Element
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo

private val nbaTeamAbbreviations =
    listOf(
        "ATL",
        "BOS",
        "BKN",
        "CHA",
        "CHI",
        "CLE",
        "DAL",
        "DEN",
        "DET",
        "GSW",
        "HOU",
        "IND",
        "LAC",
        "LAL",
        "MEM",
        "MIA",
        "MIL",
        "MIN",
        "NOP",
        "NYK",
        "OKC",
        "ORL",
        "PHI",
        "PHX",
        "PHO",
        "POR",
        "SAC",
        "SAS",
        "TOR",
        "UTA",
        "WAS",
    )

internal fun Element.toPlayerRowRawInfo(): PlayerRowRawInfo? {
    val elements = childElementsList().map { it.text() }
    val playerName = elements.getOrNull(1)
    val firstPassPlayerNameSanitization = playerName.sanitizePlayerName()
    val playerHealthStatus = playerName.getPlayerHealthStatus()

    val badPlayerNames = listOf("", "Players", "Starting Lineup Totals")

    if (playerName in badPlayerNames) return null

    return PlayerRowRawInfo(
        position = elements.getOrNull(0),
        playerName = firstPassPlayerNameSanitization?.replace(playerHealthStatus.value, ""),
        healthStatus = playerHealthStatus,
        positionEligibility = playerName?.sanitizePlayerPositionEligibility(),
        // Going to need to do more work here
        opponent = childElementsList().findOpponent(),
    )
}

internal fun List<Element>.findOpponent(): String {
    if (map { it.text() }.any { text -> text.contains("(Empty)") }) return ""

    val possibilities =
        listOfNotNull(getOrNull(3), getOrNull(4), getOrNull(5), getOrNull(6))
            .map { element -> element.text() }
            .filterNot { text -> text.any { char -> char.isDigit() } }
            .filterNot { text -> text.contains("\uE061") }
            .filterNot { text -> text.contains("-") }
            .filter { text ->
                text.isEmpty() ||
                    text.all { char -> if (char.isLetter()) char.isUpperCase() else true }
            }

    val opponent =
        possibilities.singleOrNull()
            ?: error("Unable to determine opponent, found ${possibilities.joinToString(", ")}")

    return opponent
}

internal fun String?.removeTeamAbbreviations(): String? {
    var mutableString = this
    nbaTeamAbbreviations.forEach { abbreviation ->
        if (mutableString?.contains(abbreviation) == true) {
            mutableString = mutableString.replace(abbreviation, "")
        }
    }
    return mutableString
}

internal fun String?.sanitizePlayerName(): String? =
    this?.substringBeforeLast("-")
        // Remove nonsense
        ?.replace("No New Player Notes", "", ignoreCase = true)
        ?.replace("New Player Note", "", ignoreCase = true)
        ?.replace("Player Note", "", ignoreCase = true)
        ?.replace("Video Forecast", "", ignoreCase = true)
        ?.sanitizePlayerHealthStatus()
        ?.removeTeamAbbreviations()
        ?.trim()

internal fun String?.sanitizePlayerHealthStatus(): String? {
    var mutableString = this

    // https://stackoverflow.com/a/77368797/8217056
    fun String.replaceLast(oldValue: String, newValue: String): String {
        val lastIndex = lastIndexOf(oldValue)
        if (lastIndex == -1) {
            return this
        }
        val prefix = substring(0, lastIndex)
        val suffix = substring(lastIndex + oldValue.length)
        return "$prefix$newValue$suffix"
    }

    PlayerHealthStatus.entries.forEach { status ->
        mutableString =
            if (
                status == PlayerHealthStatus.SHORT_TERM_INJURY &&
                    mutableString?.last() == status.value.first()
            ) {
                mutableString.replaceLast(status.value, "")
            } else if (status != PlayerHealthStatus.SHORT_TERM_INJURY) {
                mutableString?.replace(status.value, "")
            } else {
                mutableString
            }
    }

    return mutableString
}

internal fun String?.getPlayerHealthStatus(): PlayerHealthStatus =
    PlayerHealthStatus.entries
        // cannot include the healthy in this check as
        // we determine healthy by the absence of other status'.
        .filterNot { it == PlayerHealthStatus.HEALTHY }
        .firstOrNull { status -> this?.contains(status.value) == true }
        ?: PlayerHealthStatus.HEALTHY

internal fun String?.sanitizePlayerPositionEligibility(): List<String> {
    val containsNumbers = this?.any { it.isDigit() }
    val firstPass = this?.substringAfterLast("-")?.trim().orEmpty()

    val positionsString =
        if (containsNumbers == true) {
            val indexOfFirstNumber = firstPass.indexOfFirst { it.isDigit() }
            if (indexOfFirstNumber != -1) {
                firstPass.take(indexOfFirstNumber)
            } else {
                firstPass
            }
        } else {
            firstPass
        }

    return positionsString.split(",").map { it.trim() }
}
