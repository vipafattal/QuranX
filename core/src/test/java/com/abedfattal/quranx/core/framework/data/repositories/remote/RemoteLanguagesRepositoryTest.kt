package com.abedfattal.quranx.core.framework.data.repositories.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abedfattal.quranx.core.framework.api.QuranCloudAPI
import com.abedfattal.quranx.core.framework.api.QuranCloudEndpoints
import com.abedfattal.quranx.core.model.Language
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.rules.WebServicesMockingRule
import com.abedfattal.quranx.core.utils.enqueueResponse
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteLanguagesRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    //Testing data
    private val languages = listOf("en", "ar", "fr", "tr").map { Language(it) }
    private val languagesProcessSuccess = ProcessState.Success(languages)

    //Mock server
    private val server: MockWebServer = MockWebServer()

    //Subject under test.
    private lateinit var sut: RemoteLanguagesRepository

    @Before
    fun setup() {
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()


        val api: QuranCloudAPI = Retrofit.Builder()
            .baseUrl(server.url("/")) //Base url directed to fake server
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuranCloudAPI::class.java)


        sut = RemoteLanguagesRepository(api) //Web api.
    }

    @After
    fun finish() {
        server.shutdown()
    }

    @Test
    fun `getSupportedLanguage should fetch languages correctly given 200 response`() = runTest {
        //When the response code is 200.
        server.enqueueResponse(QuranCloudEndpoints.languages, 200)

        /*
          Then result should have two values first is ProcessState.Loading and the last
          is ProcessState.Success that contains languages.
         */
        val result = sut.getSupportedLanguage().take(2).toList()

        Assert.assertTrue(result.first() is ProcessState.Loading<List<Language>>)
        Assert.assertEquals(result.last(), languagesProcessSuccess)
    }

    @Test
    fun `getSupportedLanguage should fail fetch languages given 404 response`() = runTest {
        server.enqueueResponse(QuranCloudEndpoints.languages, 404)

        val result = sut.getSupportedLanguage().take(2).toList()

        Assert.assertTrue(result.first() is ProcessState.Loading<List<Language>>)
        Assert.assertTrue(result.last() is ProcessState.Failed<List<Language>>)
    }
}