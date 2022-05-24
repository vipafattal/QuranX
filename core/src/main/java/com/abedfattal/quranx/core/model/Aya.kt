package com.abedfattal.quranx.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.abedfattal.quranx.core.framework.db.AYAT_TABLE
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * The base model for querying verses.
 * @property number represents verse number in Quran which can be [1-6236].
 * @property numberInSurah represents verse number in surah.
 * @property text represents verse text.
 * @property juz represents juz number in Quran which can be [1-30].
 * @property page represents juz number in Quran which can be [1-604].
 * @property hizbQuarter represents hizb qurarter number in Quran.
 * @property surah_number represents the surah number in Quran which can be [1-114].
 * @property ayaEdition represents the edition id of verse text.
 */
@Serializable
@Entity(
    tableName = AYAT_TABLE,
    primaryKeys = ["ayaNumberInMushaf", "ayaEdition"]
)
data class Aya(
    @ColumnInfo(name = "ayaNumberInMushaf")
    val number: Int,
    var text: String,
    val numberInSurah: Int,
    var juz: Int = -1,
    var page: Int = -1,
    var hizbQuarter: Int = -1,
    var surah_number: Int = 0,
    var ayaEdition: String = ""
) : SerializableModel(){

    /**
     * Convert the current [Aya] object into json [String].
     */
    override fun toJson() = Json.encodeToString(serializer(), this)


    companion object {

        /**
         * Convert current [jsonSurah] into [Aya] object.
         */
        fun fromJson(jsonAya: String): Aya {
            return Json.decodeFromString(serializer(), jsonAya)
        }

        /**
         * Sajud verses number in Quran.
         */
        @JvmField
        val SAJDA_LIST: List<Int> =
            listOf(
                1160,
                1722,
                1951,
                2138,
                2308,
                2613,
                2672,
                2915,
                3185,
                3518,
                3994,
                4256,
                4846,
                5905,
                6125
            )
    }


}
