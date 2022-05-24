package com.abedfattal.quranx.core.framework.data.repositories.remote

import com.abedfattal.quranx.core.framework.api.ApiEndpoints
import com.abedfattal.quranx.core.framework.api.QuranCloudAPI
import com.abedfattal.quranx.core.framework.api.getResponse
import com.abedfattal.quranx.core.model.Language
import com.abedfattal.quranx.core.model.ProcessState
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteLanguagesRepositoryTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuranCloudAPI::class.java)

    //Testing data
    private val languages = listOf("en", "ar", "fr", "tr").map { Language(it) }
    private val languagesProcessSuccess = ProcessState.Success(languages)

    private val remoteLanguagesRepository = RemoteLanguagesRepository(api)

    @After
    fun shutdownServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch languages correctly given 200 response`() = kotlinx.coroutines.test.runTest {

        //When the response code is 200.
        mockWebServer.getResponse(ApiEndpoints.languages, 200)

        //Then result should have two values first is Loading the last is Success contains languages.
        val result = remoteLanguagesRepository.getSupportedLanguage().take(2).toList()


        Assert.assertTrue(result.first() is ProcessState.Loading<List<Language>>)
        Assert.assertEquals(result.last(), languagesProcessSuccess)
    }

    @Test
    fun `should fetch languages fail given 404 response`() = kotlinx.coroutines.test.runTest {
        mockWebServer.getResponse(ApiEndpoints.languages, 404)

        val result = remoteLanguagesRepository.getSupportedLanguage().take(2).toList()

        Assert.assertTrue(result.first() is ProcessState.Loading<List<Language>>)
        Assert.assertTrue(result.last() is ProcessState.Failed<List<Language>>)
    }
}