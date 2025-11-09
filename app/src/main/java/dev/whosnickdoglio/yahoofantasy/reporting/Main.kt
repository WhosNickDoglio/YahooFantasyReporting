// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting

import com.github.ajalt.clikt.command.main

public suspend fun main(args: Array<String>) {
    CheckRosterCommand().main(args)
}
