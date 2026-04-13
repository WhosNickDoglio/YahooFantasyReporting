// Copyright (C) 2026 Nicholas Doglio
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

@ContributesBinding(AppScope::class)
internal class HtmlDocumentFetcherImpl : HtmlDocumentFetcher {
    override suspend fun fetchDocument(url: String): Document = Ksoup.parseGetRequest(url)
}
