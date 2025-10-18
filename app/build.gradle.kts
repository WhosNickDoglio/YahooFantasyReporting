plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    application
}

metro {
    contributesAsInject = true
}

application {
    mainClass = "dev.whosnickdoglio.yahoofantasy.reporting.MainKt"
}

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.AZUL
        languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get())
    }
    explicitApi()
}

dependencies {
    implementation(libs.ksoup)
    implementation(libs.ksoup.network)
    implementation(libs.kotlinx.date)
    implementation(libs.google.sheets)
    implementation(libs.google.ouath)
    implementation(libs.google.api.client)
    implementation(libs.slf4j)

    testImplementation(libs.junit)
    testImplementation(libs.assertk)
}
