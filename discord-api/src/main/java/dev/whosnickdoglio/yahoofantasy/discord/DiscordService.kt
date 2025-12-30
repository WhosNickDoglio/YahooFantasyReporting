// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.yahoofantasy.discord

import com.slack.eithernet.ApiResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

public interface DiscordService {

    // https://discord.com/developers/docs/resources/message#create-message-limitations
    // 780074134063415301
    @POST("channels/{channel_id}/messages")
    public suspend fun sendMessage(
        @Path("channel_id") id: String,
        @Body body: MessageBody,
    ): ApiResult<Unit, Unit>
}

@Serializable
public data class MessageBody(
    val content: String,
    @SerialName("allowed_mentions") val allowMentions: AllowedMentions,
)

@Serializable
public data class AllowedMentions(val parse: List<String>) {
    public constructor(vararg type: String) : this(type.toList())
}

public enum class AllowedMentionsType(public val type: String) {
    USERS("users"),
    ROLES("roles"),
    EVERYONE("everyone"),
}
