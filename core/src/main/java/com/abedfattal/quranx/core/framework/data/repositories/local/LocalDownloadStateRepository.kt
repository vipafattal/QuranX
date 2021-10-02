package com.abedfattal.quranx.core.framework.data.repositories.local

import com.abedfattal.quranx.core.framework.db.daos.DownloadStateDao
import com.abedfattal.quranx.core.model.DownloadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


/** @suppress **/
internal class LocalDownloadStateRepository(private val downloadStateDao: DownloadStateDao) {


    suspend fun getAllDownloadStates(): List<DownloadState> {
        return downloadStateDao.getAllDownloadState()
    }

    suspend fun getDownloadState(): DownloadState? {
        return downloadStateDao.getDownloadState()
    }


    suspend fun addDownloadState(
        id: String,
        state: Int
    ) {
        downloadStateDao.addDownloadState(DownloadState(id, state))
    }

    suspend fun removeDownloadState(
        id: String,
    ) {
        downloadStateDao.remoteState(id)
    }
}