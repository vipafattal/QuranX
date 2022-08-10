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

    //Testing data
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

    //Subject under test.
    private lateinit var localBasedLanguagesRepository: LocalBasedLanguagesRepository

    @Before
    fun setup() {
        remoteRepository = FakeLanguageRemoteRepository()
        localRepository = FakeLanguageLocalRepository(localLanguages)

        localBasedLanguagesRepository =
            LocalBasedLanguagesRepository(localRepository, remoteRepository)
    }


    @Test
    fun `getSupportLanguages When remote is #not# prioritized returns local languages`() = runTest {
        val result =
            localBasedLanguagesRepository.getSupportLanguages(prioritizeRemote = false).onSuccess.first()!!
        Assert.assertEquals(result, localLanguages)
    }

    @Test
    fun `getSupportLanguages When remote is prioritized returns remote languages`() = runTest {
        remoteRepository.response = ProcessState.Success(remoteLanguages)
        val result =
            localBasedLanguagesRepository
                .getSupportLanguages(prioritizeRemote = true)
                .onSuccess
                .first()
        Assert.assertEquals(result, remoteLanguages)
    }

    @Test
    fun `getSupportLanguages When remote is prioritized and fail returns local languages`() = runTest {
        remoteRepository.response = ProcessState.Failed("", 0)

        val result = localBasedLanguagesRepository
            .getSupportLanguages(prioritizeRemote = true)
            .first() as ProcessState.Success

        Assert.assertEquals(result.data, localLanguages)
    }

    @Test
    fun `getSupportLanguages When no local or remote data returns failed`() = runTest {
        localRepository.deleteAllLanguages()
        remoteRepository.response = ProcessState.Failed("", 0)

        val result = localBasedLanguagesRepository
            .getSupportLanguages(prioritizeRemote = true)
            .first()

        Assert.assertEquals(result, remoteRepository.response)
    }
}