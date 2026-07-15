// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.kover)
    alias(libs.plugins.kotlin.serialization)
    `java-test-fixtures`
}

dependencies {
    api(projects.leagueInfo)
    api(projects.metroAnnotations)
    api(libs.kotlinx.coroutines.core)

    implementation(libs.ksoup)
    implementation(libs.ksoup.network)

    testFixturesImplementation(libs.ksoup)

    testImplementation(testFixtures(projects.leagueInfo))
    testImplementation(libs.assertk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
