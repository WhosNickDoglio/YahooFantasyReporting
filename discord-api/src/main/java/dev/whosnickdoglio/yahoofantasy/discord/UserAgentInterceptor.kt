// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.discord

import dev.zacsweers.metro.Inject
import okhttp3.Interceptor
import okhttp3.Response

// User-Agent: DiscordBot ($url, $versionNumber)
@Inject
public class UserAgentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request().newBuilder().addHeader("User-Agent", "DiscordBot (test.com, 1)").build()
        )
}
