// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kover)
    alias(libs.plugins.kotlin.serialization)
    `java-test-fixtures`
}

kover { reports { filters { excludes { classes("*\$Metro*") } } } }

metro { contributesAsInject = true }

tasks.withType(Detekt::class).configureEach {
    exclude { spec -> spec.file.path.contains("build/generated") }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization)
    implementation(libs.ksoup)
    implementation(libs.ksoup.network)
    implementation(projects.leagueInfo)
    implementation(projects.metroAnnotations)
    implementation(projects.simpleLogger)

    // https://www.slf4j.org/codes.html#noProviders
    runtimeOnly(libs.slf4j)

    testFixturesImplementation(libs.ksoup)

    testImplementation(testFixtures(projects.leagueInfo))
    testImplementation(libs.assertk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockingbird)

    kspTest(libs.mockingbird.compiler)
}
