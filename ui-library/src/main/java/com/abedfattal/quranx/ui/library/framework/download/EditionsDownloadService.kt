package com.abedfattal.quranx.ui.library.framework.download

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.annotation.Keep
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.DownloadingProcess
import com.abedfattal.quranx.core.model.Edition
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Keep
class EditionsDownloadService : Service() {

    private val serviceBinder = ServiceCommunicator()
    private val downloadNotification by lazy { EditionDownloadNotification(this) }
    private val downloadingProcessQueue: MutableMap<String, EditionDownloadJob> = mutableMapOf()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    fun addEdition(edition: Edition) {
        if (!downloadingProcessQueue.containsKey(edition.identifier)) downloadEdition(edition)
    }

    private fun downloadEdition(edition: Edition) {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {

            val inProgressDownloadCounts =
                downloadingProcessQueue.values.filter { it.downloadState is DownloadingProcess.InProgress }.size

            if (inProgressDownloadCounts < 3)
                DataSources.localBasedDataSource.quranRepository.downloadQuranBook(edition)
                    .collect { process ->

                        val editionJob = EditionDownloadJob(edition, this, process)
                        addProcessEdition(editionJob)

                        when (process) {
                            is DownloadingProcess.Success, is DownloadingProcess.Failed -> processDownloadCompleted(
                                edition
                            )
                            else -> Unit
                        }
                    }
            else
                addProcessEdition(EditionDownloadJob(edition, this, DownloadingProcess.Pending()))
        }
    }


    private suspend fun processDownloadCompleted(edition: Edition) {
        removeProcessEdition(edition)

        val editionInQueue =
            downloadingProcessQueue.values.firstOrNull { it.downloadState is DownloadingProcess.Pending }

        if (editionInQueue == null) releaseProcess()
        else downloadEdition(editionInQueue.edition)

    }


    private suspend fun cancelDownload(e: Edition) {
        val editionJob: EditionDownloadJob? = downloadingProcessQueue[e.identifier]
        require(editionJob != null && editionJob.downloadState !is DownloadingProcess.Saving)
        editionJob.job.coroutineContext.cancel()
        downloadNotification.cancelNotification(e)
        removeProcessEdition(e)
    }


    private suspend fun addProcessEdition(editionJob: EditionDownloadJob) {
        downloadingProcessQueue[editionJob.edition.identifier] = editionJob
        _downloadingProcessLiveData.emit(downloadingProcessQueue.values.toList())
        downloadNotification.showNotification(editionJob)
    }

    private suspend fun removeProcessEdition(e: Edition) {
        downloadingProcessQueue.remove(e.identifier)
        _downloadingProcessLiveData.emit(downloadingProcessQueue.values.toList())
        if (downloadingProcessQueue.isEmpty())
            stopForeground(true)
    }

    private fun releaseProcess() = stopSelf()

    override fun onBind(intent: Intent?): IBinder {
        return serviceBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if (downloadingProcessQueue.isEmpty()) releaseProcess()
        return super.onUnbind(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
        releaseProcess()
    }


    inner class ServiceCommunicator : Binder() {

        fun addToDownloads(edition: Edition) {
            addEdition(edition)
        }

        suspend fun cancelDownload(edition: Edition) {
            this@EditionsDownloadService.cancelDownload(edition)
        }
    }


    companion object {

        private val _downloadingProcessLiveData: MutableStateFlow<List<EditionDownloadJob>> =
            MutableStateFlow(
                listOf()
            )

        val downloadingProcess: Flow<List<EditionDownloadJob>>
            get() = _downloadingProcessLiveData

    }
}
