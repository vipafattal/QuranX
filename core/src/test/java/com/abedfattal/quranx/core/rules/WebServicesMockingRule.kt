package com.abedfattal.quranx.core.rules

import com.abedfattal.quranx.core.framework.api.QuranCloudAPI
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebServicesMockingRule(val server: MockWebServer = MockWebServer()) : TestWatcher() {

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()


    val api: QuranCloudAPI = Retrofit.Builder()
        .baseUrl(server.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuranCloudAPI::class.java)

    override fun finished(description: Description?) {
        super.finished(description)
        server.shutdown()
    }
}