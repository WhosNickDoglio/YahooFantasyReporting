// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    application
}

buildConfig {
    packageName("dev.whosnickdoglio.yahoofantasy.reporting")
    useKotlinOutput {
        topLevelConstants = true
    }
    buildConfigField(
        type = "String",
        name = "GOOGLE_CREDENTIALS",
        value = providers.environmentVariable("GOOGLE_CREDENTIALS").orElse("\"\""),
    )
}

metro { contributesAsInject = true }

application { mainClass = "dev.whosnickdoglio.yahoofantasy.reporting.MainKt" }

dependencies {
    implementation(libs.google.api.client)
    implementation(libs.google.sheets)
    implementation(libs.ksoup)
    implementation(libs.ksoup.network)
    runtimeOnly(libs.slf4j)

    testImplementation(libs.assertk)
    testImplementation(libs.junit)
}
