package com.abedfattal.quranx.ui.library.ui.settings

import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.common.CommonUI
import com.abedfattal.quranx.ui.common.preferences.AppPreferences
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.settings.SettingsPreferencesConstant.TranslationQuranEditionKey

object LibraryPreferences {

    private val appPreferences: AppPreferences = CommonUI.userPreferences

    fun getTranslationQuranEdition(): Edition? {
        val jsonEdition = appPreferences.getStr(TranslationQuranEditionKey, "")
        return if (jsonEdition.isNotEmpty())
            Edition.fromJson(jsonEdition)
        else null
    }

    fun getFontSize(): Int =
        appPreferences.getInt(
            SettingsPreferencesConstant.SelectedTextSizeKey,
            R.string.medium_font_size
        )

    fun isArabicNumbers(): Boolean {
        return appPreferences.getBoolean(SettingsPreferencesConstant.ArabicNumbersKey)
    }

    fun setTranslationQuranEdition(edition: Edition?) {
        val jsonEdition = edition?.toJson() ?: ""
        appPreferences.put(TranslationQuranEditionKey, jsonEdition)
    }

    fun saveFontSize(sizeName: Int) {
        appPreferences.put(SettingsPreferencesConstant.SelectedTextSizeKey, sizeName)
    }


    fun setIsArabicNumbers(newValue: Boolean) {
        appPreferences.put(SettingsPreferencesConstant.ArabicNumbersKey, newValue)
    }

    fun setDisplayAyaWithTafseer(newValue: Boolean) {
        appPreferences.put(SettingsPreferencesConstant.TranslationWithAyaKey, newValue)
    }

    fun isDisplayAyaWithTafseer(): Boolean =
        appPreferences.getBoolean(SettingsPreferencesConstant.TranslationWithAyaKey, true)

    var wordByWordTranslation: Boolean
        get() = appPreferences.getBoolean("wordbyword-mode", true)
        set(value) = appPreferences.put("wordbyword-mode", value)

    var needToDownloadEditions: List<String>
        get() = appPreferences.getStr("need-to-download-books", "").split(",")
        set(value) = appPreferences.put("need-to-download-books", value.joinToString(","))

}