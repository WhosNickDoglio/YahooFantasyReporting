// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kover)
    application
}

metro { contributesAsInject = true }

application { mainClass = "dev.whosnickdoglio.yahoofantasy.reporting.MainKt" }

tasks.withType(Detekt::class).configureEach {
    exclude { spec -> spec.file.path.contains("build/generated") }
}

dependencies {
    implementation(platform(libs.okhttp.bom))
    implementation(platform(libs.retrofit.bom))
    implementation(libs.google.sheets)
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.discordApi)
    implementation(projects.googleSheetsApi)
    implementation(projects.inputWriter)
    implementation(projects.leagueInfo)
    implementation(projects.metroAnnotations)
    implementation(projects.rosterEvaluator)
    implementation(projects.simpleLogger)
    implementation(projects.yahooDataFetcher)

    // https://www.slf4j.org/codes.html#noProviders
    runtimeOnly(libs.slf4j)

    testImplementation(testFixtures(projects.leagueInfo))
    testImplementation(testFixtures(projects.yahooDataFetcher))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockingbird)

    kspTest(libs.mockingbird.compiler)
}
