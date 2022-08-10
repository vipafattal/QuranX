package com.abedfattal.quranx.core.framework.data.repositories.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abedfattal.quranx.core.framework.api.QuranCloudEndpoints
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.rules.WebServicesMockingRule
import com.abedfattal.quranx.core.utils.enqueueResponse
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RemoteQuranRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var apiMockingRule = WebServicesMockingRule()

    private lateinit var sut: RemoteQuranRepository

    @Before
    fun setup() {
        sut = RemoteQuranRepository(apiMockingRule.api)
    }


    @Test
    fun `getQuranBook should fetch quran book correctly given 200 response`() = runTest {
        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.quranByEditionParam, 200)
        val bookProcess = sut.getQuranBook("edition").take(2).toList()

        Assert.assertTrue(bookProcess.first() is ProcessState.Loading)
        Assert.assertTrue(bookProcess.last() is ProcessState.Success)

        val quranData = (bookProcess.last() as ProcessState.Success).data!!
        assertThat(quranData.surahs.size, `is`(114))
        assertThat(quranData.surahs.map { it.ayat.size }.sumOf { it }, `is`(6236))
    }


    @Test
    fun `getAya should fetch aya correctly given 200 response`() = runTest {
        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.ayahByEditionAndNumberParams, 200)
        val bookProcess = sut.getAya(10, "").take(2).toList()

        Assert.assertTrue(bookProcess.first() is ProcessState.Loading)
        Assert.assertTrue(bookProcess.last() is ProcessState.Success)

        val quranData = (bookProcess.last() as ProcessState.Success).data!!
        Assert.assertTrue(quranData.text.isNotBlank())
    }


    @Test
    fun `getAya should fail fetch aya given 404 response`() = runTest {

        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.ayahByEditionAndNumberParams, 404)
        val bookProcess = sut.getAya(10, "").take(2).toList()

        Assert.assertTrue(bookProcess.first() is ProcessState.Loading)
        Assert.assertTrue(bookProcess.last() is ProcessState.Failed)
    }

    @Test
    fun `getPage should fetch page correctly given 200 response`() = runTest {
        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.pageByNumberAndEditionParams, 200)
        val bookProcess = sut.getPage(-1, "").take(2).toList()

        Assert.assertTrue(bookProcess.first() is ProcessState.Loading)
        Assert.assertTrue(bookProcess.last() is ProcessState.Success)

        val pageData = (bookProcess.last() as ProcessState.Success).data!!
        Assert.assertTrue(pageData.isNotEmpty())
    }


    @Test
    fun `getPage should fail fetch page given 404 response`() = runTest {

        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.pageByNumberAndEditionParams, 404)
        val bookProcess = sut.getPage(-1, "").take(2).toList()

        Assert.assertTrue(bookProcess.first() is ProcessState.Loading)
        Assert.assertTrue(bookProcess.last() is ProcessState.Failed)
    }

    @Test
    fun `getJuz should fetch juz correctly given 200 response`() = runTest {
        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.juzByNumberAndEditionParams, 200)
        val bookProcess = sut.getJuz(-1, "").take(2).toList()

        Assert.assertTrue(bookProcess.first() is ProcessState.Loading)
        Assert.assertTrue(bookProcess.last() is ProcessState.Success)

        val pageData = (bookProcess.last() as ProcessState.Success).data!!
        Assert.assertTrue(pageData.isNotEmpty())
    }

    @Test
    fun `getJuz should fail fetch juz given 404 response`() = runTest {

        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.juzByNumberAndEditionParams, 404)
        val bookProcess = sut.getJuz(-1, "").take(2).toList()

        Assert.assertTrue(bookProcess.first() is ProcessState.Loading)
        Assert.assertTrue(bookProcess.last() is ProcessState.Failed)
    }

    @Test
    fun `searchAllQuranByLanguage should fetch correctly with results given 200 response`() = runTest {


        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.searchQueryByLanguageParams, 200)


        val bookProcess = sut.searchAllQuranByLanguage("", "").take(2).toList()


        Assert.assertTrue(bookProcess.first() is ProcessState.Loading)
        Assert.assertTrue(bookProcess.last() is ProcessState.Success)

        val searchResult = (bookProcess.last() as ProcessState.Success).data!!
        Assert.assertTrue(searchResult.isNotEmpty())
    }

    @Test
    fun `searchQuranByEdition should fetch correctly with results given 200 response`() = runTest {


        apiMockingRule.server.enqueueResponse(QuranCloudEndpoints.searchQueryByEdition, 200)
        val bookProcess = sut.searchQuranByEdition("", "").take(2).toList()

        Assert.assertTrue(bookProcess.first() is ProcessState.Loading)
        Assert.assertTrue(bookProcess.last() is ProcessState.Success)

        val searchResult = (bookProcess.last() as ProcessState.Success).data!!
        Assert.assertTrue(searchResult.isNotEmpty())
    }


}