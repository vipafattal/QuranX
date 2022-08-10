package com.abedfattal.quranx.core.framework.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abedfattal.quranx.core.framework.db.LANGUAGES_TABLE
import com.abedfattal.quranx.core.model.Language

/** @suppress */

@Dao
interface LanguagesDao {
    @Query("select * from $LANGUAGES_TABLE order by code ASC")
    suspend fun getAllSupportedLanguage(): List<Language>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLanguagesList(languages: List<Language>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLanguage(languages: Language)

    @Query("delete from $LANGUAGES_TABLE where code = :languageCode")
    suspend fun deleteLanguage(languageCode: String)

    @Query("delete from $LANGUAGES_TABLE")
    suspend fun deleteAllLanguages()
}