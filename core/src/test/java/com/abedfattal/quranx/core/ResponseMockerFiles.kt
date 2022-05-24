package com.abedfattal.quranx.core.framework.api

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets


private val codes = listOf(200, 400, 401, 402, 403, 404)

fun MockWebServer.getResponse(endpoint: String, code: Int) {
    val responseFile = when (endpoint) {
        ApiEndpoints.languages -> getLanguageResponse(code)
        else -> throw IllegalArgumentException("Unknown endpoint:$endpoint")
    }
    enqueueResponse(responseFile, code)
}

private fun getLanguageResponse(code: Int): String {
    val editionLanguages = "editions-languages-$code"

    return when (code) {
        in codes -> editionLanguages
        else -> throw IllegalArgumentException("Unknown code:$code")
    }
}

private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName.json")

    val source = inputStream?.let { inputStream.source().buffer() }
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}

