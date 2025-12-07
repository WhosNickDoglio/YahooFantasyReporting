// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.reporting.util.log

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

public fun interface SimpleLogger {
    public fun log(string: String)
}

// TODO make internal when Metro supports it (likely with Kotlin 2.3.20)
//  https://github.com/ZacSweers/metro/issues/98
@ContributesBinding(AppScope::class)
public class DefaultSimpleLogger : SimpleLogger {
    override fun log(string: String) {
        println(string)
    }
}
