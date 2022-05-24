package com.abedfattal.quranx.core.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abedfattal.quranx.core.framework.db.DOWNLOAD_STATE_TABLE
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


/** @suppress **/
@Keep
@Serializable
@Entity(tableName = DOWNLOAD_STATE_TABLE)
data class DownloadState(
    @PrimaryKey
    @ColumnInfo(name = "download_state_id")
    val id: String,
    @ColumnInfo(name = "download_state")
    val state: Int,
):SerializableModel() {
    /**
     * Convert the current [DownloadState] object into json [String].
     */
    override fun toJson() = Json.encodeToString(serializer(), this)

   companion object {
       const val STATE_DOWNLOADED = 0

       /**
        * Convert current [jsonState] into [DownloadState] object.
        */
       fun fromJson(jsonState: String): DownloadState {
           return Json.decodeFromString(serializer(), jsonState)
       }
   }
}