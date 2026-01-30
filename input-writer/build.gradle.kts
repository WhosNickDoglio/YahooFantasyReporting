// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.kover)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(projects.leagueInfo)
    api(projects.simpleLogger)
    api(projects.yahooDataFetcher)

    implementation(libs.kotlinx.serialization)
}
