// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting

import dev.whosnickdoglio.yahoofantasy.discord.AllowedMentions
import dev.whosnickdoglio.yahoofantasy.discord.DiscordService
import dev.whosnickdoglio.yahoofantasy.discord.MessageBody
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
    private val discordApi: DiscordService,
) {

    public suspend operator fun invoke(): Unit =
        withContext(coroutineContext) {
            val sheetsData = sheets.retrieveReports()

            val recentReports = sheetsData.filter { it.date.isAfter(threeDaysAgo) }

            val grouped = recentReports.groupBy { it.teamId }

            val repeatOffenders = grouped.filter { (_, list) -> list.size > 1 }

            val teamNames = repeatOffenders.values.joinToString("\n") { "- ${it.first().teamName}" }

            if (repeatOffenders.isEmpty()) {
                simpleLogger.log("Everyone set their lineup last night!")
            } else {
                val report = buildString {
                    appendLine("**Public Shaming:**")
                    appendLine(
                        "Mitch believes the following managers did not set their lineup last night"
                    )
                    appendLine(teamNames)
                    appendLine("Please set your lineup before games start tonight!")
                }
                    .trimIndent()

                val response =
                    discordApi.sendMessage(
                        id = "TODO",
                        body = MessageBody(report, AllowedMentions("everyone")),
                    )
                simpleLogger.log(response.toString())
            }
        }
}
