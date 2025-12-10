// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.reporting.sheets.GoogleSheets
import dev.whosnickdoglio.yahoofantasy.reporting.util.coroutines.IoDispatcher
import dev.whosnickdoglio.yahoofantasy.reporting.util.log.SimpleLogger
import dev.zacsweers.metro.Inject
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

@Inject
public class App(
    private val simpleLogger: SimpleLogger,
    @param:IoDispatcher private val coroutineContext: CoroutineContext,
    private val sheets: GoogleSheets,
    private val threeDaysAgo: LocalDate,
) {

    public suspend operator fun invoke(): Unit =
        withContext(coroutineContext) {
            val sheetsData = sheets.retrieveReports()

            val recentReports = sheetsData.filter { it.date.isAfter(threeDaysAgo) }

            val grouped = recentReports.groupBy { it.teamId }

            val repeatOffenders = grouped.filter { (_, list) -> list.size > 1 }

            simpleLogger.log("Public shaming! These people did not set their lineup yesterday")
            repeatOffenders.forEach { simpleLogger.log(it.value.first().teamName) }
        }
}
