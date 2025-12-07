// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.ksp)
}

metro { contributesAsInject = true }

tasks.withType(Detekt::class).configureEach {
    exclude { spec -> spec.file.path.contains("build/generated") }
}

dependencies {
    implementation(libs.google.api.client)
    implementation(libs.google.sheets)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization)
    implementation(projects.leagueInfo)
    implementation(projects.metroAnnotations)
    implementation(projects.simpleLogger)

    // https://www.slf4j.org/codes.html#noProviders
    runtimeOnly(libs.slf4j)

    testImplementation(libs.assertk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockingbird)

    kspTest(libs.mockingbird.compiler)
}
