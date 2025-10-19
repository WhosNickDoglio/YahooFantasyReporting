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

    val badPlayerNames = listOf("", "Players")

    if (playerName in badPlayerNames) return null

    return PlayerRowRawInfo(
        position = elements.getOrNull(0),
        playerName = firstPassPlayerNameSanitization?.replace(playerHealthStatus.value, ""),
        healthStatus = playerHealthStatus,
        positionEligibility = playerName?.sanitizePlayerPositionEligibility(),
        // Going to need to do more work here
        opponent = childElementsList().findOpponent(), // TODO might be 6 or 3
    )
}

internal fun List<Element>.findOpponent(): String {
    val possibilities =
        listOfNotNull(getOrNull(3), getOrNull(5), getOrNull(6))
            .map { element -> element.text() }
            .filterNot { text -> text.any { char -> char.isDigit() } }
            .filterNot { text -> text.contains("\uE061") }
            .filter { text ->
                text.isEmpty() ||
                    text.all { char -> if (char.isLetter()) char.isUpperCase() else true }
            }

    return possibilities.single()
}

internal fun String?.removeTeamAbbreviations(): String? {
    var mutableString = this
    nbaTeamAbbreviations.forEach { abbreviation ->
        if (mutableString?.contains(abbreviation) == true) {
            mutableString = this?.replace(abbreviation, "")
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

internal fun String?.sanitizePlayerHealthStatus(): String? =
    this?.replace("GTD", "")?.replace("INJ", "")?.replace("OUT", "")

internal fun String?.getPlayerHealthStatus(): PlayerHealthStatus =
    when {
        this?.contains("GTD") == true -> PlayerHealthStatus.GAME_TIME_DECISION
        this?.contains("INJ") == true -> PlayerHealthStatus.LONG_TERM_INJURY
        this?.contains("OUT") == true -> PlayerHealthStatus.SHORT_TERM_INJURY
        else -> PlayerHealthStatus.HEALTHY
    }

internal fun String?.sanitizePlayerPositionEligibility(): List<String> {
    val containsNumbers = this?.any { it.isDigit() }
    val firstPass = this?.substringAfterLast("-")?.trim().orEmpty()

    return if (containsNumbers == false) {
        firstPass.split(",")
    } else {
        val indexOfFirstNumber = firstPass.indexOfFirst { it.isDigit() }
        val final = firstPass.take(indexOfFirstNumber).split(",")
        final
    }
}
