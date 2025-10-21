// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
import io.gitlab.arturbosch.detekt.Detekt

// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kover)
    application
}

kover { reports { filters { excludes { classes("*\$Metro*") } } } }

metro { contributesAsInject = true }

application { mainClass = "dev.whosnickdoglio.yahoofantasy.reporting.MainKt" }

spotless { kotlin { targetExclude("**/generated/**") } }

tasks.withType(Detekt::class).configureEach {
    exclude { spec -> spec.file.path.contains("build/generated") }
}

dependencies {
    implementation(libs.google.api.client)
    implementation(libs.google.sheets)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.ksoup)
    implementation(libs.ksoup.network)

    runtimeOnly(libs.slf4j)

    testImplementation(libs.assertk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockingbird)

    kspTest(libs.mockingbird.compiler)
}
