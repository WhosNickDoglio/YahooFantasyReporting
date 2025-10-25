// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document

class FakeHtmlDocumentFetcher(private val fetchData: (url: String) -> String) :
    HtmlDocumentFetcher {
    override suspend fun fetchDocument(url: String): Document = Ksoup.parse(fetchData(url))
}
