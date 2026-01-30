// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.kover)
}

dependencies {
    api(projects.leagueInfo)
    api(projects.yahooDataFetcher)

    implementation(projects.metroAnnotations)

    testImplementation(testFixtures(projects.leagueInfo))
    testImplementation(testFixtures(projects.yahooDataFetcher))
    testImplementation(libs.assertk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
