// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.nodes.Document
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

public fun interface HtmlDocumentFetcher {
    public suspend fun fetchDocument(url: String): Document
}

// TODO make internal when Metro supports it (likely with Kotlin 2.3.20)
//  https://github.com/ZacSweers/metro/issues/98
@ContributesBinding(AppScope::class)
public class HtmlDocumentFetcherImpl : HtmlDocumentFetcher {
    override suspend fun fetchDocument(url: String): Document = Ksoup.parseGetRequest(url)
}
