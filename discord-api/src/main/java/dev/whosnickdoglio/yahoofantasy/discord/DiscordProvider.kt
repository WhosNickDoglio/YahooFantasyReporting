// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.discord

import com.slack.eithernet.integration.retrofit.ApiResultCallAdapterFactory
import com.slack.eithernet.integration.retrofit.ApiResultConverterFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Qualifier
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

@ContributesTo(AppScope::class)
public interface DiscordProvider {

    @BotToken @Provides public fun provideBotToken(): String = System.getenv("BOT_TOKEN")

    @Provides
    public fun provideOkhttpClient(
        botTokenInterceptor: BotTokenInterceptor,
        userAgentInterceptor: UserAgentInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(userAgentInterceptor)
            .addNetworkInterceptor(botTokenInterceptor)
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

    @Provides
    public fun provideDiscordService(client: Lazy<OkHttpClient>): DiscordService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory { client.value.newCall(it) }
            .addConverterFactory(ApiResultConverterFactory)
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .addConverterFactory(
                Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
            .create()
}

private const val BASE_URL = "https://discord.com/api/"

@Qualifier internal annotation class BotToken
