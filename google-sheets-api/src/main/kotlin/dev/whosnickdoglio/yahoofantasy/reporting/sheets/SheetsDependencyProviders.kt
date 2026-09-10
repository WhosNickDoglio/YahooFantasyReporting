// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.sheets

import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.auth.Credentials
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Qualifier
import java.io.File

@ContributesTo(AppScope::class)
@BindingContainer
public object SheetsDependencyProviders {

    private const val APPLICATION_NAME = "Yahoo Fantasy Reporting"

    @Provides public fun provideGsonFactory(): GsonFactory = GsonFactory.getDefaultInstance()

    @Provides public fun provideHttpTransport(): NetHttpTransport = NetHttpTransport()

    @Provides
    @GoogleCredentialsSecret
    public fun provideGoogleSecret(): String {
        val secret = System.getenv("GOOGLE_CREDENTIALS")

        // TODO make this less bad
        return if (secret != null) {
            secret
        } else {
            val filePath = System.getenv("GOOGLE_SERVICE_ACCOUNT_JSON_PATH")
            File(filePath).readText()
        }
    }

    @Provides
    public fun provideCredentials(@GoogleCredentialsSecret secret: String): Credentials =
        GoogleCredentials.fromStream(secret.byteInputStream())
            .createScoped(listOf(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE))

    @Provides
    public fun provideInitializer(credentials: Credentials): HttpCredentialsAdapter =
        HttpCredentialsAdapter(credentials)

    @Provides
    public fun provideSheetsService(
        transport: NetHttpTransport,
        gsonFactory: GsonFactory,
        credentialsAdapter: HttpCredentialsAdapter,
    ): Sheets =
        Sheets.Builder(transport, gsonFactory, credentialsAdapter)
            .setApplicationName(APPLICATION_NAME)
            .build()
}

@Qualifier private annotation class GoogleCredentialsSecret
