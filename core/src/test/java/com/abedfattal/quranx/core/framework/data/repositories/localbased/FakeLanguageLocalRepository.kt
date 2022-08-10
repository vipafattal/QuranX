package com.abedfattal.quranx.core.framework.data.repositories.localbased

import com.abedfattal.quranx.core.framework.data.repositories.local.ILocalLanguagesRepository
import com.abedfattal.quranx.core.model.Language

class FakeLanguageLocalRepository(initialData: List<Language>) : ILocalLanguagesRepository {

    private val languages: MutableList<Language> = initialData.toMutableList()

    override suspend fun getAllSupportedLanguage(): List<Language> = languages

    override suspend fun addLanguages(languages: List<Language>) {
        this.languages.addAll(languages)
    }

    override suspend fun addLanguage(language: Language) {
        languages.add(language)
    }

    override suspend fun deleteLanguage(languageCode: String) {
        languages.removeIf { it.code == languageCode }
    }

    override suspend fun deleteAllLanguages() = languages.clear()

}