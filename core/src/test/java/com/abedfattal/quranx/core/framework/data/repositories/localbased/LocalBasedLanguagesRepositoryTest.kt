package com.abedfattal.quranx.core.framework.data.repositories.localbased

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abedfattal.quranx.core.model.Language
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.utils.onSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class LocalBasedLanguagesRepositoryTest {

    //Test data
    private val arabic = Language("Ar")
    private val english = Language("En")
    private val french = Language("Fr")
    private val localLanguages = listOf(arabic, english)
    private val remoteLanguages = listOf(arabic, english, french)

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var remoteRepository: FakeLanguageRemoteRepository
    private lateinit var localRepository: FakeLanguageLocalRepository

    private lateinit var localBasedLanguagesRepository: LocalBasedLanguagesRepository

    @Before
    fun setup() {
        remoteRepository = FakeLanguageRemoteRepository()
        localRepository = FakeLanguageLocalRepository(localLanguages)

        localBasedLanguagesRepository =
            LocalBasedLanguagesRepository(localRepository, remoteRepository)
    }


    @Test
    fun `get supported local languages returns local languages`() = runTest {
        val result =
            localBasedLanguagesRepository.getSupportLanguages(prioritizeRemote = false).onSuccess.first()!!
        Assert.assertEquals(result, localLanguages)
    }

    @Test
    fun `get supported remote languages returns remote languages`() = runTest {
        remoteRepository.response = ProcessState.Success(remoteLanguages)
        val result =
            localBasedLanguagesRepository
                .getSupportLanguages(prioritizeRemote = true)
                .onSuccess
                .first()
        Assert.assertEquals(result, remoteLanguages)
    }

    @Test
    fun `get supported remote languages returns local languages`() = runTest {
        remoteRepository.response = ProcessState.Failed("", 0)

        val result = localBasedLanguagesRepository
            .getSupportLanguages(prioritizeRemote = true)
            .first() as ProcessState.Success

        Assert.assertEquals(result.data, localLanguages)
    }

    @Test
    fun `get supported languages from remote returns fail remote languages`() = runTest {
        localRepository.deleteAllLanguages()
        remoteRepository.response = ProcessState.Failed("", 0)
        val result = localBasedLanguagesRepository
            .getSupportLanguages(prioritizeRemote = true)
            .first()
        Assert.assertEquals(result, remoteRepository.response)
    }
}