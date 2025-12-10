// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.discord

import retrofit2.http.POST
import retrofit2.http.Path

public interface DiscordService {

    // 780074134063415301
    // TODO error handling
    @POST("channels/{channel_id}/messages")
    public suspend fun sendMessage(
        @Path("channel_id") id: String,
        content: String,
        allowMentions: Boolean,
    )
}
