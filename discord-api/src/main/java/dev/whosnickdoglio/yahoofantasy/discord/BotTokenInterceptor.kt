// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.discord

import dev.zacsweers.metro.Inject
import okhttp3.Interceptor
import okhttp3.Response

@Inject
public class BotTokenInterceptor(@param:BotToken private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request().newBuilder().addHeader("Authorization", "Bot $token").build())
}
