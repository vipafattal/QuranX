package com.abedfattal.quranx.core.framework.db.daos

import androidx.room.*
import com.abedfattal.quranx.core.framework.db.AYAT_TABLE
import com.abedfattal.quranx.core.framework.db.EDITIONS_TABLE
import com.abedfattal.quranx.core.framework.db.SURAHS_TABLE
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.core.model.AyatWithEdition
import com.abedfattal.quranx.core.model.Surah

/** @suppress */
@Dao
interface QuranDao {
    //Surahs dao
    @Query("select * from $SURAHS_TABLE where surahEdition = :edition order by surahNumberInMushaf asc")
    suspend fun getSurahsByEdition(edition: String): List<Surah>

    @Query("select * from $SURAHS_TABLE where surahEdition = :editionId and surahNumberInMushaf = :surahNumber")
    suspend fun getSurahByEdition(editionId: String, surahNumber: Int): Surah?

    @Query("select * from $AYAT_TABLE  where ayaEdition =:editionId and surah_number = :surahNumber")
    suspend fun getSurahAyatByEdition(editionId: String, surahNumber: Int): List<Aya>

    @Query("select * from $AYAT_TABLE  where ayaEdition =:editionId and ayaNumberInMushaf = :number")
    suspend fun getAyaByEdition(number: Int, editionId: String): Aya?

    @Transaction
    @Query("select * from $EDITIONS_TABLE  where id in (:editions)")
    suspend fun getAyatAllEditions(vararg editions: String): List<AyatWithEdition>

    @Query("select * from $AYAT_TABLE  where ayaEdition = :edition")
    suspend fun getAyatEdition(edition: String): List<AyaWithInfo>

    @Transaction
    @Query("select * from $EDITIONS_TABLE join $AYAT_TABLE on id = ayaEdition where id in (:editions) and juz = :juz")
    suspend fun getJuzAllEditions(juz: Int, vararg editions: String): List<AyatWithEdition>

    @Transaction
    @Query("select * from $EDITIONS_TABLE join $AYAT_TABLE on id = ayaEdition where id in (:editions) and surah_number = :surahNumber")
    suspend fun getSurahAllEditions(
        surahNumber: Int,
        vararg editions: String
    ): List<AyatWithEdition>

    @Transaction
    @Query("select * from $EDITIONS_TABLE join $AYAT_TABLE on id = ayaEdition where id in (:editions) and page = :page")
    suspend fun getPageAllEditions(page: Int, vararg editions: String): List<AyatWithEdition>

    @Transaction
    @Query("select * from $EDITIONS_TABLE join $AYAT_TABLE on id = ayaEdition where id in (:editions) and ayaNumberInMushaf = :ayaNumberInMushaf")
    suspend fun getAyaAllEditions(
        ayaNumberInMushaf: Int,
        vararg editions: String
    ): List<AyatWithEdition>

    @Transaction
    @Query("select * from $EDITIONS_TABLE join $AYAT_TABLE on id = ayaEdition where id in (:editions) and surah_number = :surahNumber and numberInSurah = :ayaNumberInSurah")
    suspend fun getAyaAllEditions(
        ayaNumberInSurah: Int,
        surahNumber: Int,
        vararg editions: String
    ): List<AyatWithEdition>

    @Transaction
    @Query("select * from $AYAT_TABLE where page = :page and ayaEdition = :editionId order by ayaNumberInMushaf ASC")
    suspend fun getAyatOfPage(page: Int, editionId: String): List<AyaWithInfo>

    @Query("select * from $AYAT_TABLE where juz = :juz and ayaEdition = :editionId order by ayaNumberInMushaf ASC")
    suspend fun getAyatOfJuz(juz: Int, editionId: String): List<AyaWithInfo>

    @Transaction
    @Query("select * from $AYAT_TABLE where ayaNumberInMushaf between :startNumber AND :endNumber and ayaEdition = :editionId order by numberInSurah ASC")
    suspend fun getAyatByNumberInMushafRange(
        startNumber: Int,
        endNumber: Int,
        editionId: String,

        ): List<AyaWithInfo>

    @Transaction
    @Query("select * from $AYAT_TABLE where ayaNumberInMushaf = :ayaNumber and ayaEdition = :editionId limit 1")
    suspend fun getAyaByNumberInQuran(ayaNumber: Int, editionId: String): AyaWithInfo?

    @Transaction
    @Query("select * from $AYAT_TABLE where ayaEdition = :editionId and text like :query")
    suspend fun searchQuranByEdition(query: String, editionId: String): List<AyaWithInfo>

    @Transaction
    @Query("select * from $EDITIONS_TABLE join $AYAT_TABLE on id = ayaEdition where language =:language and text like :query")
    suspend fun searchAllQuran(query: String, language: String): List<AyatWithEdition>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAya(aya: Aya)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAyat(ayat: List<Aya>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSurah(surah: Surah)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSurahs(surah: List<Surah>)


    @Query("delete from $AYAT_TABLE where ayaEdition = :editionId")
    suspend fun deleteAyat(editionId: String)

}