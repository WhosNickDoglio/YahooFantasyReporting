// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.data.soup

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.parseFile
import java.io.File

class FakeHtmlDocumentFetcher(private val data: File) : HtmlDocumentFetcher {
    override suspend fun fetchDocument(url: String): Document = Ksoup.parseFile(data)
}
