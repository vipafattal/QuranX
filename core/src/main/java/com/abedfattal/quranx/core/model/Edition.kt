package com.abedfattal.quranx.core.model

import androidx.core.os.LocaleListCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abedfattal.quranx.core.framework.db.EDITIONS_TABLE
import com.abedfattal.quranx.core.model.Edition.Companion.FORMAT_AUDIO
import com.abedfattal.quranx.core.model.Edition.Companion.FORMAT_TEXT
import com.abedfattal.quranx.core.model.Edition.Companion.getAllEditionsTypes
import com.abedfattal.quranx.core.utils.isArabic
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*

/**
 * The base model for querying text editions.
 *
 * @property identifier represents the edition id.
 * @property name represents the name of the edition.
 * @property englishName represents the English name of the edition.
 * @property format represents that can be [FORMAT_AUDIO] or [FORMAT_TEXT].
 * @property type represents type of the edition can be any of [getAllEditionsTypes].
 */

@Serializable
@Entity(tableName = EDITIONS_TABLE)
data class Edition(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val identifier: String,
    val language: String,
    val name: String,
    val englishName: String,
    val format: String,
    val type: String,
) : SerializableModel() {


    /**
     * Convert the current [Edition] object into json [String].
     */
    override fun toJson() = Json.encodeToString(serializer(), this)

    /**
     * @return Edition [type] name depending on the current device [Locale].
     *
     * @see [LocaleListCompat.getDefault]
     */
    fun getLocalizedType(local: Locale): String {
        @Suppress("DEPRECATION")
        return if (!local.isArabic)
            type.capitalize(Locale.ROOT)
        else
            when (type) {
                TYPE_TAFSEER -> "تفسير"
                TYPE_TRANSLATION -> "ترجمة"
                TYPE_QURAN -> "قرآن كريم"
                TYPE_VERSE_BY_VERSE -> "كلمة بكلمة"
                TYPE_TRANSLITERATION -> "لفظ الكلمات"
                else -> ""
            }
    }

    /**
     * @return Edition [format] name depending on the current device [Locale].
     *
     * @see [LocaleListCompat.getDefault]
     */
    fun getLocalizedFormat(local: Locale): String {
        @Suppress("DEPRECATION")
        return if (!local.isArabic)
            type.capitalize(Locale.ROOT)
        else
            when (format) {
                FORMAT_TEXT -> "نصي"
                FORMAT_AUDIO -> "صوتي"
                else -> ""
            }
    }

    /**
     * @return Edition name depending on the current device [Locale].
     *
     * @see [LocaleListCompat.getDefault]
     */
    fun getLocalizedName(local: Locale): String {
        @Suppress("DEPRECATION")
        return if (local.isArabic) name
        else englishName.capitalize(Locale.ROOT)
    }

    fun getDescription(locale: Locale): String {
        return "${getLocalizedName(locale)} ${getLocalizedFormat(locale)} ${getLocalizedType(locale)}"
    }

    companion object {

        fun quranEdition(
            id: String,
            name: String,
            englishName: String
        ): Edition {
            return Edition(
                identifier = id,
                language = "ar",
                name = name,
                englishName = englishName,
                FORMAT_TEXT,
                TYPE_QURAN
            )
        }

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
        const val TYPE_VERSE_BY_VERSE = "verse by verse"

        val TYPES: List<String>
            get() = listOf(
                TYPE_QURAN,
                TYPE_TAFSEER,
                TYPE_TRANSLATION,
                TYPE_TRANSLITERATION,
                TYPE_VERSE_BY_VERSE
            )

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

