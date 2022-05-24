package com.abedfattal.quranx.core.framework.db.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.abedfattal.quranx.core.framework.db.LibraryDatabaseRule
import com.abedfattal.quranx.core.model.Language
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
@RunWith(AndroidJUnit4::class)
class LanguagesDaoTest {


    //Test Data
    private val arabic = Language("Ar")
    private val english = Language("En")
    private val languages = listOf(arabic, english)
    private val languagesWithDuplicates = listOf(arabic, english, arabic)

    /*
    * JUnit Test Rule that swaps the background executor used by the Architecture Components
    * with a different one which executes each task synchronously.
    */
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    /*
    * Manage database instance after & before each test.
    */
    @get:Rule
    var databaseRule = LibraryDatabaseRule()

    private val dao: LanguagesDao get() = databaseRule.db.getLanguagesDao()


    @Test
    @Throws(Exception::class)
    fun getAllSupportedLanguage_firstRun_returnsEmpty() = runTest {
        val result = dao.getAllSupportedLanguage()
        MatcherAssert.assertThat(result, `is`(emptyList()))
    }

    @Test
    @Throws(Exception::class)
    fun addLanguages_withDuplicates_should_returnsValuesWithoutDuplicates() = runTest {
        dao.addLanguages(languagesWithDuplicates)
        val result = dao.getAllSupportedLanguage()
        MatcherAssert.assertThat(result, `is`(languages))
    }

    @Test
    @Throws(Exception::class)
    fun addLanguage_single_should_returnsSingleValueOne() = runTest {
        dao.addLanguage(arabic)
        val result = dao.getAllSupportedLanguage().first()
        MatcherAssert.assertThat(result, `is`(arabic))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllLanguages_should_returnsZeroValue() = runTest {
        dao.addLanguages(languages)
        dao.deleteAllLanguage()
        val result = dao.getAllSupportedLanguage()
        MatcherAssert.assertThat(result, `is`(emptyList()))
    }

    @Test
    @Throws(Exception::class)
    fun deleteLanguage_should_returnsValuesWithTheDeleted() = runTest {
        dao.addLanguages(languages)
        dao.deleteLanguage(english.code)
        val result = dao.getAllSupportedLanguage()
        MatcherAssert.assertThat(result, `is`(languages.filterNot { it.code == english.code }))
    }
}