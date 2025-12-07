// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data

// https://stackoverflow.com/a/53018129
internal fun getResourceAsText(path: String): String =
    object {}.javaClass.getResource(path)?.readText().orEmpty()
