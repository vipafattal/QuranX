package com.abedfattal.quranx.ui.library.framework.download

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import com.abedfattal.quranx.core.model.DownloadingProcess
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.common.R
import com.abedfattal.quranx.ui.library.ReadLibrary
import java.util.*

class EditionDownloadNotification internal constructor(
    private val downloadService: EditionsDownloadService
) {

    private val context: Context = downloadService.applicationContext
    private val intent = Intent(context, EditionsDownloadService::class.java)
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return

         val channel = NotificationChannel(
            DOWNLOAD_NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.books_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

    }


    fun showNotification(
        editionJob: EditionDownloadJob
    ) {
        val edition = editionJob.edition
        createNotification(
            edition,
            editionJob.downloadState
        )
    }

    private val foregroundNotification = NotificationCompat.Builder(
        context,
        DOWNLOAD_NOTIFICATION_CHANNEL_ID
    ).apply {
        setContentTitle(context.getString(R.string.downloading))
    }.build()

    private fun createNotification(
        edition: Edition,
        state: DownloadingProcess<*>
    ) {
        val editionName = edition.getLocalizedName(Locale.getDefault())
        val processState = context.getString(state.stateName)

        val notification = NotificationCompat.Builder(
            context,
            DOWNLOAD_NOTIFICATION_CHANNEL_ID
        ).apply {
            setGroup(NOTIFICATION_GROUP_ID)
            setContentIntent(getDownloadIntent(edition, state))
            setContentTitle("$processState: $editionName")
            setAutoCancel(state.isProcessCompleted)
            setContentText(edition.getLocalizedName(Locale.getDefault()))
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    if (state is DownloadingProcess.Failed)
                        context.getString(state.friendlyMsg)
                    else
                        edition.getDescription(Locale.getDefault())
                )
            )
            setProgress(0, 0, !state.isProcessCompleted)
            setOngoing(!state.isProcessCompleted)


            if (state !is DownloadingProcess.InProgress || state !is DownloadingProcess.Saving)
                setOngoing(!state.isProcessCompleted)


            setSmallIcon(
                if (state is DownloadingProcess.Failed) R.drawable.ic_error
                else R.drawable.ic_download
            )
        }.build()

        if (!state.isProcessCompleted) startForegroundService(context)
        notificationManager.notify(edition.identifier, 1, notification)
    }

    fun cancelNotification(edition: Edition) {
        NotificationManagerCompat.from(context).cancel(edition.identifier, 1)
    }

    private fun getDownloadIntent(edition: Edition, state: DownloadingProcess<*>): PendingIntent {
        val intent = Intent().apply {
            putExtra(
                DOWNLOAD_NOTIFICATION_ARG, bundleOf(
                    DOWNLOAD_EDITION_ARG to edition.toJson(),
                    IS_DOWNLOAD_STATE_SUCCESS_ARG to (state is DownloadingProcess.Success)
                )
            )
            component = ComponentName(context, ReadLibrary.mainActivityPath)
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
        else PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }

    private fun startForegroundService(context: Context) {
        //When creating a foreground service first we call context start service
        if (Build.VERSION.SDK_INT >= 26) context.startForegroundService(intent)
        else context.startService(intent)
        //next we have to show notification that is tells user what is happening.
        downloadService.startForeground(1, foregroundNotification)
    }

    companion object {
        private const val DOWNLOAD_NOTIFICATION_CHANNEL_ID = "books_download_channel"

        const val NOTIFICATION_GROUP_ID = "editions_download_group"
        const val DOWNLOAD_NOTIFICATION_ARG = "download_notification_helper"
        const val DOWNLOAD_EDITION_ARG = "editionStart"
        const val IS_DOWNLOAD_STATE_SUCCESS_ARG = "download_state_arg"
    }

}