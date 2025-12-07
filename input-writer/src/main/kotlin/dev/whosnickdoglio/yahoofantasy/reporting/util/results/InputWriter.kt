// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.util.results

import dev.whosnickdoglio.yahoofantasy.reporting.data.LeagueInfo
import dev.whosnickdoglio.yahoofantasy.reporting.data.RosterInfo
import dev.whosnickdoglio.yahoofantasy.reporting.util.log.SimpleLogger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import java.io.File
import kotlinx.serialization.json.Json

public fun interface InputWriter {
    public suspend fun write(input: List<RosterInfo>)
}

@ContributesBinding(AppScope::class)
public class DefaultInputWriter(
    private val logger: SimpleLogger,
    private val leagueInfo: LeagueInfo,
) : InputWriter {
    override suspend fun write(input: List<RosterInfo>) {
        val jobId = System.getenv("GITHUB_RUN_ID")
        val rawDataFile = File("${leagueInfo.name}_raw_input_$jobId.json")
        rawDataFile.writeText(Json.encodeToString(input))
        logger.log("Successfully wrote raw input to ${rawDataFile.absolutePath}")
    }
}
