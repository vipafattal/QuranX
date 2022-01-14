package com.abedfattal.quranx.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abedfattal.quranx.core.framework.data.repositories.local.LocalBookmarksRepository
import com.abedfattal.quranx.core.framework.db.BOOKMARKS_TABLE
import com.abedfattal.quranx.core.utils.DateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import java.util.*

/**
 * The base model for querying bookmarked verses.
 * @see [LocalBookmarksRepository].
 *
 * @property editionId represents the verse edition id.
 * @property ayaNumber represents verse number in Quran ([Aya.number]).
 */
@Serializable
@Entity(tableName = BOOKMARKS_TABLE)
data class Bookmark(
    @PrimaryKey
    @ColumnInfo(name = "bookmark_id")
    val id: String = "",
    @ColumnInfo(name = "bookmark_editionId")
    val editionId: String = "",
    @ColumnInfo(name = "bookmark_ayaNumber")
    val ayaNumber: Int = -1,
    @ColumnInfo(name = "bookmark_date")
    @Serializable(with = DateSerializer::class)
    val date: Date? = null,
    @ColumnInfo(name = "bookmark_type")
    val type: String = "",
    @ColumnInfo(name = "bookmark_is_dirty")
    var isDirty: Boolean = true,
    @ColumnInfo(name = "bookmark_is_deleted")
    val isDeleted: Boolean = false,
    @ColumnInfo(name = "bookmark_user_id")
    var userId: String = "",
    @Serializable(with = DateSerializer::class)
    var lastUpdate: Date? = null,
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

