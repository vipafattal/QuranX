package com.abedfattal.quranx.core.framework.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abedfattal.quranx.core.framework.db.LibraryDatabase
import com.abedfattal.quranx.core.framework.db.daos.LanguagesDao
import com.abedfattal.quranx.core.model.Language
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)

class LanguagesDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    //Test Data
    private val arabic = Language("Ar")
    private val english = Language("En")
    private val languages = listOf(arabic, english)
    private val languagesWithDuplicates = listOf(arabic, english, arabic)

    //Database object
    private lateinit var db: LibraryDatabase

    //Subject under test
    //Dao: where all database objects CRUD happens.
    private val sut: LanguagesDao get() = db.getLanguagesDao()


    @Before
    fun setup() {
        //To test database we usually create an database store on memory (not on disk).
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LibraryDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    //Cleaning database after each test.
    @After
    @Throws(IOException::class)
    fun destroy() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun `after adding languages getAllSupportedLanguage should returns same languages`() = runTest {
        sut.addLanguagesList(languages)
        val result = sut.getAllSupportedLanguage()
        assertThat(result, `is`(languages))
    }

    @Test
    @Throws(Exception::class)
    fun `after addLanguagesList with duplicates should returns values without duplicates`() = runTest {
        sut.addLanguagesList(languagesWithDuplicates)
        val result = sut.getAllSupportedLanguage()
        assertThat(result, `is`(languages))
    }

    @Test
    @Throws(Exception::class)
    fun `after deleteLanguageByCode should returns values without the deleted one`() = runTest {
        sut.addLanguagesList(languages)
        sut.deleteLanguage(english.code)
        val result = sut.getAllSupportedLanguage()
        assertThat(result, `is`(languages.filterNot { it.code == english.code }))
    }

    @Test
    @Throws(Exception::class)
    fun `after deleteAllLanguages should returns empty list`() = runTest {
        sut.addLanguagesList(languages)
        sut.deleteAllLanguages()
        val result = sut.getAllSupportedLanguage()
        assertThat(result, `is`(emptyList()))
    }
}