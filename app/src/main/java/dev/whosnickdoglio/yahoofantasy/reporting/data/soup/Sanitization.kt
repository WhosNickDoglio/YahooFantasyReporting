package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import com.fleeksoft.ksoup.nodes.Element
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerHealthStatus
import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo

private val TeamAbbreviations = listOf(
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

internal fun Element.toPlayerRowRawInfo(): PlayerRowRawInfo {
    val elements = childElementsList().map { it.text() }
    val playerName = elements.getOrNull(1)
    val firstPassPlayerNameSanitization = playerName.sanitizePlayerName()
    val playerHealthStatus = playerName.getPlayerHealthStatus()

    return PlayerRowRawInfo(
        position = elements.getOrNull(0),
        playerName = firstPassPlayerNameSanitization?.replace(playerHealthStatus.value, ""),
        healthStatus = playerHealthStatus,
        positionEligibility = playerName?.sanitizePlayerPositionEligibility(),
        opponent = elements.getOrNull(6), // TODO might be 6 or 3
    )
}
// TODO remove team abbreviations
internal fun String?.removeTeamAbbreviations(): String?  {
    var mutableString = this
    TeamAbbreviations.forEach { abbreviation ->
        if (mutableString?.contains(abbreviation) == true) {
            mutableString = this?.replace(abbreviation, "")
        }
    }
    return mutableString
}

internal fun String?.sanitizePlayerName(): String? = this?.substringBeforeLast("-")
    // Remove nonsense
    ?.replace("No New Player Notes", "", ignoreCase = true)
    ?.replace("New Player Note", "", ignoreCase = true)
    ?.replace("Player Note", "", ignoreCase = true)
    ?.replace("Video Forecast", "", ignoreCase = true)
    ?.sanitizePlayerHealthStatus()
    ?.removeTeamAbbreviations()
    ?.trim()

internal fun String?.sanitizePlayerHealthStatus(): String? = this?.replace("GTD", "")?.replace("INJ", "")

internal fun String?.getPlayerHealthStatus(): PlayerHealthStatus = when {
    this?.contains("GTD") == true -> PlayerHealthStatus.GAME_TIME_DECISION
    this?.contains("INJ") == true -> PlayerHealthStatus.INJURED
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
