package com.abedfattal.quranx.core.framework.data.repositories.local

import com.abedfattal.quranx.core.framework.db.daos.LanguagesDao
import com.abedfattal.quranx.core.model.Language

/**
 * Use [LocalLanguagesRepository] mainly, to save and list all the supported language in the API service.
 *
 * @property languagesDao represents the data access object for [Language] table.
 */
class LocalLanguagesRepository internal constructor(private val languagesDao: LanguagesDao) :
    ILocalLanguagesRepository {

    /**
     * @return [Language] list of the saved languages in database. If no languages is add to the database the list will be empty.
     */
     override suspend fun getAllSupportedLanguage(): List<Language> {
        return languagesDao.getAllSupportedLanguage()
    }

    /**
     * Add a list of [Language] to the database table. To add a single language see [addLanguage].
     */
     override suspend fun addLanguages(languages: List<Language>) {
        return languagesDao.addLanguagesList(languages)
    }

    /**
     * Add a single [Language] to the database table. To add a list of languages see [addLanguages].
     */
     override suspend fun addLanguage(language: Language) {
        return languagesDao.addLanguage(language)
    }

    /**
     * Removes a single [Language] from the database table. To remove all languages see [deleteAllLanguages].
     */
     override suspend fun deleteLanguage(languageCode: String) {
        languagesDao.deleteLanguage(languageCode)
    }

    /**
     * Removes all [Language] from the database table. To remove a single language see [deleteLanguage].
     */
     override suspend fun deleteAllLanguages() {
        languagesDao.deleteAllLanguages()
    }
}