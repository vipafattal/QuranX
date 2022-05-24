package com.abedfattal.quranx.core.framework.data.repositories.local

import com.abedfattal.quranx.core.model.Language

interface ILocalLanguagesRepository {
    /**
     * @return [Language] list of the saved languages in database. If no languages is add to the database the list will be empty.
     */
    suspend fun getAllSupportedLanguage(): List<Language>

    /**
     * Add a list of [Language] to the database table. To add a single language see [addLanguage].
     */
    suspend fun addLanguages(languages: List<Language>)

    /**
     * Add a single [Language] to the database table. To add a list of languages see [addLanguages].
     */
    suspend fun addLanguage(language: Language)

    /**
     * Removes a single [Language] from the database table. To remove all languages see [deleteAllLanguages].
     */
    suspend fun deleteLanguage(languageCode: String)

    /**
     * Removes all [Language] from the database table. To remove a single language see [deleteLanguage].
     */
    suspend fun deleteAllLanguages()
}