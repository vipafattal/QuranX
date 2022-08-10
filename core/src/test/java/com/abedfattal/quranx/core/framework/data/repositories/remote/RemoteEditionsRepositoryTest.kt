package com.abedfattal.quranx.core.framework.data.repositories.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abedfattal.quranx.core.framework.api.QuranCloudEndpoints
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.rules.WebServicesMockingRule
import com.abedfattal.quranx.core.utils.enqueueResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteEditionsRepositoryTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var apiMockingRule = WebServicesMockingRule()

    //Subject under test.
    private lateinit var sut: RemoteEditionsRepository

    @Before
    fun setup() {
        sut = RemoteEditionsRepository(apiMockingRule.api)
    }


    @Test
    fun `getTextEditions Should fetch correctly given 200 response`() = runTest {
        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.textFormatEditions, 200)
        val result = sut.getTextEditions().take(2).toList()

        Assert.assertTrue(result.first() is ProcessState.Loading)
        Assert.assertTrue(result.last() is ProcessState.Success)

        val editions = (result.last() as ProcessState.Success).data!!
        Assert.assertTrue(editions.size == 119)
    }

    @Test
    fun `getAudioEditions Should fetch correctly given 200 response`() = runTest {
        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.audioFormatEditions, 200)
        val result = sut.getAudioEditions().take(2).toList()

        Assert.assertTrue(result.first() is ProcessState.Loading)
        Assert.assertTrue(result.last() is ProcessState.Success)

        val editions = (result.last() as ProcessState.Success).data!!
        Assert.assertTrue(editions.size == 26)
    }

    @Test
    fun `getAllEditionsType Should fetch correctly given 200 response`() = runTest {
        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.editionTypes, 200)
        val result = sut.getAllEditionsType().take(2).toList()

        Assert.assertTrue(result.first() is ProcessState.Loading)
        Assert.assertTrue(result.last() is ProcessState.Success)

        val editionTypes = (result.last() as ProcessState.Success).data!!
        Assert.assertTrue(editionTypes.size == 5)
    }

    @Test
    fun `getEditions Should fetch correctly given 200 response`() = runTest {
        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.edition, 200)
        val result = sut.getEditions("","","").take(2).toList()

        Assert.assertTrue(result.first() is ProcessState.Loading)
        Assert.assertTrue(result.last() is ProcessState.Success)

        val editions = (result.last() as ProcessState.Success).data!!
        Assert.assertTrue(editions.size == 14)
    }
}