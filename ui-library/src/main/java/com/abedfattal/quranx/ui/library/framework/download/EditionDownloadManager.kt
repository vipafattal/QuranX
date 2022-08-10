package com.abedfattal.quranx.ui.library.framework.download

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.abedfattal.quranx.core.model.Edition
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditionDownloadManager(
    private val context: Context,
    lifecycle: Lifecycle
) {

    private lateinit var editionsDownloadService: EditionsDownloadService.ServiceCommunicator
    private val intent = Intent(context, EditionsDownloadService::class.java)

    init {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_START -> onStart()
                    Lifecycle.Event.ON_DESTROY -> onDestroy()
                    else -> Unit
                }
            }
        })
    }

    fun downloadEdition(edition: Edition) {
        editionsDownloadService.addToDownloads(edition)
    }

    fun cancelDownloading(edition: Edition) = GlobalScope.launch {   editionsDownloadService.cancelDownload(edition) }

    private fun onStart() {
        context.startService(intent)
        context.bindService(intent, connection, Context.BIND_IMPORTANT)
    }

    private fun onDestroy() {
        try {
            context.unbindService(connection)
        } catch (ex: IllegalArgumentException) {
            print(ex.stackTrace)
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {}
        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
            if (service !is EditionsDownloadService.ServiceCommunicator) return
            editionsDownloadService = service

        }
    }


}
