package com.abedfattal.quranx.core.framework.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abedfattal.quranx.core.framework.api.models.Quran
import com.abedfattal.quranx.core.framework.db.daos.QuranDao
import com.abedfattal.quranx.core.rules.LibraryDatabaseRule
import com.abedfattal.quranx.core.utils.ApiResponses
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalSerializationApi::class)
@RunWith(AndroidJUnit4::class)
class QuranDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dbRule = LibraryDatabaseRule()


    private val editionOne = "quran-uthmani-quran-academy"//ApiResponses.QURAN_1
    private val editionTwo = "quran-simple-clean"//ApiResponses.QURAN_2

    private val json = Json { ignoreUnknownKeys = true }
    private fun getQuranBook(bookName: String): Quran.QuranData {
        val file = ApiResponses.buildFullDir(bookName, 200)
        val inputStream = javaClass.classLoader?.getResourceAsStream(file)
        return json.decodeFromStream<Quran.QuranBook>(inputStream!!).quran
    }

    private val sut: QuranDao get() = dbRule.db.getQuranDao()

    @Before
    fun setupTestData() = runTest {
        for (file in listOf(ApiResponses.QURAN_1, ApiResponses.QURAN_2)) {
            val quran = getQuranBook(file)
            val editionId = quran.edition.identifier

            sut.addSurah(quran.surahs[0].toSurah(editionId))
            sut.addAyat(quran.surahs[0].toAyat(editionId))

            sut.addSurah(quran.surahs[1].toSurah(editionId))
            sut.addAyat(quran.surahs[1].toAyat(editionId))

            sut.addEdition(quran.edition)
        }
    }

    @Test
    fun `getAyaByEdition given edition returns one not null aya`() = runTest {

        val result_1 = sut.getAyaByEdition(1, editionOne)
        Assert.assertTrue(result_1 != null)
        assertThat(result_1!!.edition.identifier, `is`(editionOne))

        val result_2 = sut.getAyaByEdition(1, editionTwo)
        Assert.assertTrue(result_2 != null)
        assertThat(result_2!!.edition.identifier, `is`(editionTwo))

    }

    @Test
    fun `getAyatByEdition given edition returns edition list of aya`() = runTest {
        val result_1 = sut.getAyatByEdition(editionOne)
        assertThat(result_1.size, `is`(293)) // Al-Baqraa (286) + Al-Fatihaa (7)
        assertThat(result_1.distinctBy { it.edition }.size, `is`(1))

        val result_2 = sut.getAyatByEdition(editionTwo)
        assertThat(result_2.size, `is`(293)) // Al-Baqraa (286) + Al-Fatihaa (7)
        assertThat(result_2.distinctBy { it.edition }.size, `is`(1))
    }

    @Test
    fun `getAyatOfPage given edition returns edition page list of aya`() = runTest {

        val result_1 = sut.getAyatOfPage(2, editionOne)
        assertThat(result_1.size, `is`(5)) //Al-Baqraa page 2 has only 5 ayat.
        assertThat(result_1.distinctBy { it.aya.page }.size, `is`(1))
        assertThat(result_1.distinctBy { it.aya.ayaEdition }.size, `is`(1))

        val result_2 = sut.getAyatOfPage(2, editionTwo)
        assertThat(result_2.size, `is`(5)) //Al-Baqraa page 2 has only 5 ayat.
        assertThat(result_2.distinctBy { it.aya.page }.size, `is`(1))
        assertThat(result_2.distinctBy { it.aya.ayaEdition }.size, `is`(1))
    }

    @Test
    fun `getAyatAllEditions given two editions returns two ayaat`() = runTest {
        val result = sut.getAyatAllEditions(editionOne, editionTwo)
        assertThat(result.distinctBy { it.edition }, `is`(2))
        assertThat(result.size, `is`(2))
    }

    @Test
    fun `getPageAllEditions given two editions returns two page of ayat`() = runTest {
        val result = sut.getPageAllEditions(1, editionOne, editionTwo)
        assertThat(result.distinctBy { it.edition }, `is`(2))
        assertThat(result.size, `is`(14))
    }

    @Test
    fun `getAyatAllEditions given two editions returns ayat`() = runTest {
        val result = sut.getAyatAllEditions(editionOne, editionTwo)
        assertThat(result.size, `is`(2))
        // Al-Baqraa (286) + Al-Fatihaa (7) = 293
        assertThat(result.first().ayat.size, `is`(293))
        assertThat(result.last().ayat.size, `is`(293))
    }

    @Test
    fun `getJuzAllEditions given two editions returns juz ayat`() = runTest {
        val result = sut.getJuzAllEditions(1, editionOne, editionTwo)
        // Al-Baqraa (141) + Al-Fatihaa (7) = 148
        assertThat(result.size, `is`(148 * 2))
    }

    @Test
    fun `getSurahAyatAllEditions given two editions returns`() = runTest {
        val result = sut.getSurahAyatAllEditions(1, editionOne, editionTwo)
        // Al-Baqraa (141) + Al-Fatihaa (7) = 148
        assertThat(result.size, `is`(2))
    }

    @Test
    fun `getAyatOfJuz given two editions returns`() = runTest {
        val result_1 = sut.getAyatOfJuz(1, editionOne)
        assertThat(result_1.size, `is`(148))         // Al-Baqraa (141) + Al-Fatihaa (7) = 148

        val result_2 = sut.getAyatOfJuz(1, editionTwo)
        assertThat(result_2.size, `is`(148))         // Al-Baqraa (141) + Al-Fatihaa (7) = 148
    }
}