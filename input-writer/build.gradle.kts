// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.kover)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.serialization)
}

metro { contributesAsInject = true }

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization)
    implementation(projects.leagueInfo)
    implementation(projects.simpleLogger)
    implementation(projects.yahooDataFetcher)
}
