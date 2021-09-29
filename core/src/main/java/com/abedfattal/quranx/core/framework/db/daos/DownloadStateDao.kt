package com.abedfattal.quranx.core.framework.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abedfattal.quranx.core.framework.db.DOWNLOAD_STATE_TABLE
import com.abedfattal.quranx.core.model.DownloadState
import kotlinx.coroutines.flow.Flow

/** @suppress */
@Dao
interface DownloadStateDao {

    @Query("select * from $DOWNLOAD_STATE_TABLE")
    fun listenToDownloadState(): Flow<List<DownloadState>>

    @Query("select * from $DOWNLOAD_STATE_TABLE")
    suspend fun getAllDownloadState(): List<DownloadState>

    @Query("select * from $DOWNLOAD_STATE_TABLE")
    suspend fun getDownloadState(): DownloadState?

    @Insert
    suspend fun addDownloadState(state: DownloadState)

    @Query("delete from $DOWNLOAD_STATE_TABLE where download_state_id == :id")
    suspend fun remoteState(
        id: String
    )
}