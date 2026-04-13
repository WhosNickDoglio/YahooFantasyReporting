// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.util.log

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

public fun interface SimpleLogger {
    public fun log(string: String)
}

@ContributesBinding(AppScope::class)
internal class DefaultSimpleLogger : SimpleLogger {
    override fun log(string: String) {
        println(string)
    }
}
