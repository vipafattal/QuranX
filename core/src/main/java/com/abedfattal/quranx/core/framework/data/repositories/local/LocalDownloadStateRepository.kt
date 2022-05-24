package com.abedfattal.quranx.core.framework.data.repositories.local

import com.abedfattal.quranx.core.framework.db.daos.DownloadStateDao
import com.abedfattal.quranx.core.model.DownloadState


/** @suppress **/
internal class LocalDownloadStateRepository(private val downloadStateDao: DownloadStateDao) {


    suspend fun getAllDownloadStates(): List<DownloadState> {
        return downloadStateDao.getAllDownloadState()
    }

    suspend fun getDownloadState(id:String): DownloadState? {
        return downloadStateDao.getDownloadState(id)
    }


    suspend fun addDownloadState(
        id: String,
        state: Int
    ) {
        downloadStateDao.addDownloadState(DownloadState(id, state))
    }
    suspend fun addOrUpdateDownloadState(
        id: String,
        state: Int
    ) {
        downloadStateDao.addOrUpdateDownloadState(DownloadState(id, state))
    }
    suspend fun removeDownloadState(
        id: String,
    ) {
        downloadStateDao.removeState(id)
    }
}