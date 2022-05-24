package com.abedfattal.quranx.core.model

import androidx.core.os.LocaleListCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.abedfattal.quranx.core.framework.db.SURAHS_TABLE
import com.abedfattal.quranx.core.utils.isArabic
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*

/**
 * The base model for querying Quran surahs.
 *
 * @property number represents the surah number in Quran which can be [1-114].
 * @property name represents the Arabic surah name.
 * @property englishName represents the English surah name.
 * @property englishNameTranslation represents the surah name meaning in English.
 * @property revelationType represents revelation type of surah number can be [Medinan or Meccan].
 * @property numberOfAyahs represents the number of verses in this surah.
 * @property surah_number represents the surah number in Quran which can be [1-114].
 * @property surahEdition represents the edition id of surah.
 */
@Serializable
@Entity(tableName = SURAHS_TABLE, primaryKeys = ["surahNumberInMushaf", "surahEdition"])
data class Surah(
    @ColumnInfo(name = "surahNumberInMushaf")
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Int,
    val surahEdition: String
) : SerializableModel() {

    /**
     * Convert the current [Surah] object into json [String].
     */
    override fun toJson() = Json.encodeToString(serializer(), this)

    /**
     * @return [number] in format of 001,010,100, etc...
     */
    fun getFormattedNumber() = when (number) {
        in 1..9 -> "00$number"
        in 10..99 -> "0$number"
        else -> number.toString()
    }

    fun getLocalizedRevelation(locale: Locale): String {
        return when (revelationType) {
            "Meccan" -> if (locale.isArabic) "مكية" else revelationType
            "Medinan" -> if (locale.isArabic) "مدنية" else revelationType
            else -> ""
        }
    }

    fun getLocalizedTextNumber(locale: Locale): String {
        return "$numberOfAyahs " + if (locale.isArabic) {
            if (numberOfAyahs > 10) "آية" else "آيات"
        } else "verses"
    }

    /**
     * @return Surah name depending on the current device [Locale].
     *
     * @see [LocaleListCompat.getDefault]
     */
    fun getLocalizedName(language: Language): String =
        if (language.isArabic()) name else englishName

    fun getLocalizedName(locale: Locale): String = if (locale.isArabic)
        name else englishName

    companion object {
        /**
         * Convert current [jsonSurah] into [Surah] object.
         */
        fun fromJson(jsonSurah: String): Surah {
            return Json.decodeFromString(serializer(), jsonSurah)
        }
    }
}