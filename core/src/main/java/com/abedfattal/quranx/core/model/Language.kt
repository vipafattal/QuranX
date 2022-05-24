package com.abedfattal.quranx.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abedfattal.quranx.core.framework.db.LANGUAGES_TABLE
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * The base model for querying supported languages in the remote API service.
 *
 * @property code represents the language code.
 */
@Serializable
@Entity(tableName = LANGUAGES_TABLE)
data class Language(
    @PrimaryKey
    val code: String
) :SerializableModel(){
    /**
     * Convert the current [Language] object into json [String].
     */
    override fun toJson() = Json.encodeToString(serializer(), this)

    fun isArabic() = code == "ar"

    /**
     * Get the localized name of the current language [code].
     */
    fun getLanguageName(): String {
        return when (code) {
            "ar" -> "العربية"
            "en" -> "English"
            "fr" -> "Français"
            "ru" -> "Русский"
            "de" -> "Deutsch"
            "es" -> "Español"
            "tr" -> "Türkçe"
            "zh" -> "中文"
            "th" -> "ไทย"
            "ur" -> "اردو"
            "bn" -> "বাংলা"
            "bs" -> "Bosanski"
            "ug" -> "ئۇيغۇرچە"
            "fa" -> "فارسى"
            "tg" -> "Тоҷикӣ"
            "ml" -> "മലയാളം"
            "tl" -> "Tagalog"
            "id" -> "Indonesia"
            "pt" -> "Português"
            "ha" -> "Hausa"
            "sw" -> "Kiswahili"
            "cs" -> "Čeština"
            "dv" -> "ދިވެހި"
            "hi" -> "हिन्दी،हिंदी"
            "it" -> "Italiano"
            "ja" -> "日本語 (にほんご)"
            "ko" -> "한국어, 조선어"
            "ku" -> "Kurdî, كوردی"
            "nl" -> "Nederlands،Vlaams"
            "no" -> "Norsk"
            "pl" -> "Język polski،polszczyzna"
            "ro" -> "Limba română"
            "sd" -> "सिन्धी"
            "so" -> "Soomaaliga"
            "sq" -> "Shqip"
            "sv" -> "Svenska"
            "ta" -> "தமிழ்"
            "ty" -> "Reo Tahiti"
            "tt" -> "татар теле"
            "uz" -> "Oʻzbek, Ўзбек"
            "az" -> "Azərbaycan dili"
            else -> code
        }
    }
    companion object {

        /**
         * Convert current [jsonLanguage] into [Language] object.
         */
        fun fromJson(jsonLanguage: String): Language {
            return Json.decodeFromString(serializer(), jsonLanguage)
        }
    }
}