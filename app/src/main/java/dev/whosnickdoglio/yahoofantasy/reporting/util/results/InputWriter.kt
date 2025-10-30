// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.util.results

import dev.whosnickdoglio.yahoofantasy.reporting.data.RosterInfo
import dev.whosnickdoglio.yahoofantasy.reporting.util.log.SimpleLogger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import java.io.File
import kotlinx.serialization.json.Json

internal fun interface InputWriter {
    suspend fun write(input: List<RosterInfo>)
}

@ContributesBinding(AppScope::class)
internal class DefaultInputWriter(private val logger: SimpleLogger) : InputWriter {
    override suspend fun write(input: List<RosterInfo>) {
        val rawDataFile = File("raw_input.json")
        rawDataFile.writeText(Json.encodeToString(input))
        logger.log("Successfully wrote raw input to ${rawDataFile.path}")
    }
}
