package com.abedfattal.quranx.core.utils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.BufferedSource
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets


private val codes = listOf(200, 400, 401, 402, 403, 404)

fun MockWebServer.enqueueResponse(endpoint: String, code: Int) {
    val source = getResponseBody(code, endpoint)

    enqueue(
        MockResponse()
            .setResponseCode(code)
            .setBody(source.readString(StandardCharsets.UTF_8))
    )
}

private fun MockWebServer.getResponseBody(
    code: Int,
    endpoint: String
): BufferedSource {
    if (code !in codes) throw IllegalArgumentException("Unhandled code:$code")
    val mockingFile = getMockingFileResponse(endpoint, code)
    return getFileBuffer(mockingFile)
}

private fun getMockingFileResponse(endpoint: String, code: Int): String {
    val responseFileName = ApiResponses.endpointsWithResponse.getValue(endpoint)
    return ApiResponses.buildFullDir(responseFileName, code)
}

private fun MockWebServer.getFileBuffer(file: String): BufferedSource {
    val inputStream = javaClass.classLoader?.getResourceAsStream(file)
        ?: throw IllegalArgumentException("Can't find resource file with:$file")
    return inputStream.source().buffer()
}

