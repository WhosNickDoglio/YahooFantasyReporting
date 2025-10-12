plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    application
}

application {
    mainClass = "dev.whosnickdoglio.yahoofantasy.reporting.MainKt"
}

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.AZUL
        languageVersion = JavaLanguageVersion.of(24)
    }
    explicitApi()
}

dependencies {
    implementation(libs.ksoup)
    implementation(libs.ksoup.network)
    implementation(libs.kotlinx.serialization.csv)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.date)

    testImplementation(libs.junit)
    testImplementation(libs.assertk)
}
