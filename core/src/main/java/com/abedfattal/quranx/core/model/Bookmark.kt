package com.abedfattal.quranx.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.abedfattal.quranx.core.framework.db.BOOKMARKS_TABLE
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import com.abedfattal.quranx.core.framework.data.repositories.local.LocalBookmarksRepository
/**
 * The base model for querying bookmarked verses.
 * @see [LocalBookmarksRepository].
 *
 * @property editionId represents the verse edition id.
 * @property ayaNumber represents verse number in Quran ([Aya.number]).
 */
@Serializable
@Entity(tableName = BOOKMARKS_TABLE, primaryKeys = ["bookmark_editionId", "bookmark_ayaNumber"])
data class Bookmark(
    @ColumnInfo(name = "bookmark_editionId")
    val editionId: String,
    @ColumnInfo(name = "bookmark_ayaNumber")
    val ayaNumber: Int,
) {
    /**
     * Convert the current [Bookmark] object into json [String].
     */
    fun toJson() = Json.encodeToString(serializer(), this)

    companion object {
        /**
         * Convert current [jsonBookmark] into [Bookmark] object.
         */
        fun fromJson(jsonBookmark: String): Bookmark {
            return Json.decodeFromString(serializer(), jsonBookmark)
        }
    }
}

