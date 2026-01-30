// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.kover)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(libs.eithernet)
    api(libs.eithernet.retrofit)
    api(libs.kotlinx.coroutines.core)
    api(projects.leagueInfo)
    api(projects.metroAnnotations)

    implementation(platform(libs.okhttp.bom))
    implementation(platform(libs.retrofit.bom))
    implementation(libs.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization)
}
