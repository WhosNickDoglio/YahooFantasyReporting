package dev.whosnickdoglio.yahoofantasy.reporting.sheets

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
internal data class TeamReport(
    val teamName: String,
    val url: String,
    val date: LocalDate,
)
