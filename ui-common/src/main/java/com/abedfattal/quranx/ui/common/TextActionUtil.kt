package com.abedfattal.quranx.ui.common

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import com.abedfattal.quranx.ui.common.extensions.stringer


object TextActionUtil {

    fun copyToClipboard(
        activity: Activity,
        appName: String,
        text: String,
        @StringRes copyingMessage: Int
    ) {
        val cm = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(appName, text)
        cm.setPrimaryClip(clip)
        Toast.makeText(
            activity, copyingMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun shareText(
        context: Context,
        googlePlayLink: String,
        text: String,
        @StringRes shareTitle: Int
    ) {
        val shareableText =
            text + stringer(R.string.download_app_via) + "  " + googlePlayLink
        shareViaIntent(context, shareableText, shareTitle)
    }


    private fun shareViaIntent(context: Context, text: String, @StringRes titleResId: Int) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(intent, context.getString(titleResId)))
    }
}