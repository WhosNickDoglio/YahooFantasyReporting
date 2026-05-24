// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.mockingbird)
    alias(libs.plugins.kover)
    application
}

application { mainClass = "dev.whosnickdoglio.yahoofantasy.reporting.MainKt" }

dependencies {
    implementation(platform(libs.okhttp.bom))
    implementation(platform(libs.retrofit.bom))
    implementation(libs.google.sheets)
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.discordApi)
    implementation(projects.googleSheetsApi)
    implementation(projects.leagueInfo)
    implementation(projects.metroAnnotations)
    implementation(projects.simpleLogger)
    implementation(projects.yahooDataFetcher)

    // https://www.slf4j.org/codes.html#noProviders
    runtimeOnly(libs.slf4j)

    testImplementation(testFixtures(projects.leagueInfo))
    testImplementation(testFixtures(projects.yahooDataFetcher))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
