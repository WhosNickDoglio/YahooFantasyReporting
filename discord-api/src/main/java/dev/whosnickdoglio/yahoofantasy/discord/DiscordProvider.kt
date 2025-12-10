// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.discord

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

@ContributesTo(AppScope::class)
public interface DiscordProvider {

    @Provides public fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    public fun provideDiscordService(client: Lazy<OkHttpClient>): DiscordService =
        Retrofit.Builder()
            .baseUrl("")
            .callFactory { client.value.newCall(it) }
            .addConverterFactory(
                Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
            .create()
}
