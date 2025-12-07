// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.kover)
    alias(libs.plugins.kotlin.serialization)
    `java-test-fixtures`
}

metro { contributesAsInject = true }

dependencies {
    api(libs.kotlinx.coroutines.core)
    api(projects.leagueInfo)
    api(projects.metroAnnotations)

    implementation(libs.ksoup)
    implementation(libs.ksoup.network)

    testFixturesImplementation(libs.ksoup)

    testImplementation(testFixtures(projects.leagueInfo))
    testImplementation(libs.assertk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
