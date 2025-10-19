// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.log

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

internal fun interface SimpleLogger {
    fun log(string: String)
}

@ContributesBinding(AppScope::class)
internal class DefaultSimpleLogger: SimpleLogger {
    override fun log(string: String) {
        println(string)
    }
}
