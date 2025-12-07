// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.kover)
}

metro { contributesAsInject = true }

dependencies {
    api(libs.google.api.client)
    api(libs.google.sheets)
    api(projects.leagueInfo)
    api(projects.metroAnnotations)

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.assertk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
