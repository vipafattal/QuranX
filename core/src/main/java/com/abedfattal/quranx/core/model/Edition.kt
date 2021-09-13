package com.abedfattal.quranx.core.model

import androidx.core.os.LocaleListCompat
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abedfattal.quranx.core.framework.db.EDITIONS_TABLE
import com.abedfattal.quranx.core.utils.isArabic
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*

/**
 * The base model for querying text editions.
 *
 * @property id represents the edition id.
 * @property name represents the name of the edition.
 * @property englishName represents the English name of the edition.
 * @property format represents that can be [FORMAT_AUDIO] or [FORMAT_TEXT].
 * @property type represents type of the edition can be any of [getAllEditionsTypes].
 */
@Serializable
@Entity(tableName = EDITIONS_TABLE)
data class Edition(
    @PrimaryKey
    @SerializedName("identifier")
    val id: String,
    val language: String,
    val name: String,
    val englishName: String,
    val format: String,
    val type: String,
) {

    /**
     * Convert the current [Edition] object into json [String].
     */
    fun toJson() = Json.encodeToString(serializer(), this)

    /**
     * @return Edition [type] name depending on the current device [Locale].
     *
     * @see [LocaleListCompat.getDefault]
     */
    fun getLocalizedType(local: Locale): String {
        return if (!local.isArabic)
            type
        else
            when (type) {
                TYPE_TAFSEER -> "تفسير"
                TYPE_TRANSLATION -> "ترجمة"
                TYPE_QURAN -> "قرآن كريم"
                else -> ""
            }
    }

    /**
     * @return Edition [format] name depending on the current device [Locale].
     *
     * @see [LocaleListCompat.getDefault]
     */
    fun getLocalizedFormat(local: Locale): String {
        return if (!local.isArabic)
            type
        else
            when (format) {
                FORMAT_TEXT -> "نصي"
                FORMAT_AUDIO -> "صوتي"
                else -> ""
            }
    }


    companion object {

        /**
         * Convert current [jsonEdition] into [Edition] object.
         */
        fun fromJson(jsonEdition: String): Edition {
            return Json.decodeFromString(serializer(), jsonEdition)
        }

        //Formats
        const val FORMAT_TEXT = "text"
        const val FORMAT_AUDIO = "audio"

        //Edition types
        const val TYPE_QURAN = "quran"
        const val TYPE_TAFSEER = "tafsir"
        const val TYPE_TRANSLATION = "translation"
        const val TYPE_TRANSLITERATION = "transliteration"
        const val TYPE_VERSE_BY_VERSE = "versebyverse"

        //A list of all Edition types
        fun getAllEditionsTypes() = listOf(
            TYPE_QURAN,
            TYPE_TAFSEER,
            TYPE_TRANSLATION,
            TYPE_TRANSLITERATION,
            TYPE_VERSE_BY_VERSE,
        )
    }
}

