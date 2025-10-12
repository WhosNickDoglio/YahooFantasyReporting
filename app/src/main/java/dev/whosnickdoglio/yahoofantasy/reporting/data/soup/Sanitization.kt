package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import com.fleeksoft.ksoup.nodes.Element
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo

internal fun Element.toPlayerRowRawInfo(): PlayerRowRawInfo {
    val elements = childElementsList().map { it.text() }
    val playerName = elements.getOrNull(1)
    val firstPassPlayerNameSanitization = playerName?.substringBeforeLast("-")
        // TODO break this out for better testing
        // Remove nonsense
        ?.replace("No New Player Notes", "", ignoreCase = true)?.replace("New Player Note", "", ignoreCase = true)
        ?.replace("Player Note ", "", ignoreCase = true)?.replace("Video Forecast", "", ignoreCase = true)
    // TODO remove team abbreviations

    val playerHealthStatus = when {
        firstPassPlayerNameSanitization?.contains("GTD") == true -> PlayerHealthStatus.GAME_TIME_DECISION
        firstPassPlayerNameSanitization?.contains("INJ") == true -> PlayerHealthStatus.INJURED
        else -> PlayerHealthStatus.HEALTHY
    }

    return PlayerRowRawInfo(
        position = elements.getOrNull(0),
        playerName = firstPassPlayerNameSanitization?.replace(playerHealthStatus.value, ""),
        healthStatus = playerHealthStatus,
        positionEligibility = playerName?.sanitizePlayerPositionEligibility(), // TODO
        opponent = elements.getOrNull(3),
    )
}

internal fun String?.sanitizePlayerPositionEligibility(): List<String> {
    val containsNumbers = this?.any { it.isDigit() }
    val firstPass = this?.substringAfterLast("-")?.trim().orEmpty()

    return if (containsNumbers == false) {
        firstPass.split(",")
    } else {
        val indexOfFirstNumber = firstPass.indexOfFirst { it.isDigit() }
        val final = firstPass.substring(0, indexOfFirstNumber).split(",")
        final
    }
}
