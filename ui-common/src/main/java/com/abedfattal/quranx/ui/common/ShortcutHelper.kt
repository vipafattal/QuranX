package com.abedfattal.quranx.ui.common

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import com.abedfattal.quranx.ui.common.models.ShortcutDetails

/**
 * Created by ${User} on ${Date}
 */
object ShortcutHelper {
    fun create(context: Context, shortcutDetails: ShortcutDetails, shortcutIntent: Intent) {
        // code for adding shortcut on pre oreo device
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            createDynamicShortcut(
                context,
                shortcutDetails,
                shortcutIntent,
                true
            )
        } else {
            val shortcutIcon =
                Intent.ShortcutIconResource.fromContext(context, shortcutDetails.icon)

            @Suppress("DEPRECATION")
            val addIntent = Intent().apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
                putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutDetails.label)
                putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, shortcutIcon)
                action = "com.android.launcher.action.INSTALL_SHORTCUT"
            }

            context.sendBroadcast(addIntent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun createDynamicShortcut(
        context: Context,
        shortcutDetails: ShortcutDetails,
        shortcutIntent: Intent,
        sendToHomeScreen: Boolean = false
    ) {

        shortcutIntent.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            action = "LOCATION_SHORTCUT"
        }

        val shortcutManager = context.getSystemService(ShortcutManager::class.java)!!

        val shortcut = ShortcutInfo.Builder(context, shortcutDetails.id)
            .setIntent(shortcutIntent)
            .setIcon(Icon.createWithResource(context, shortcutDetails.icon))
            .setShortLabel(shortcutDetails.label)
            .build()

        if (shortcutManager.dynamicShortcuts.size > 3) {
            val lastShortcut = shortcutManager.dynamicShortcuts.first { it.rank == 0 }
            shortcutManager.removeDynamicShortcuts(listOf(lastShortcut.id))
            shortcutManager.dynamicShortcuts
        }
        if (sendToHomeScreen && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && shortcutManager.isRequestPinShortcutSupported)
            shortcutManager.requestPinShortcut(shortcut, null)

        shortcutManager.addDynamicShortcuts(listOf(shortcut))
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun disableShortcut(
        context: Context,
        shortcutDetailsId: String
    ) {

        context.getSystemService(ShortcutManager::class.java)!!
            .disableShortcuts(listOf(shortcutDetailsId))
    }

}


/*fun a(context: Context){
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
        val shortcutManager: ShortcutManager? = context.getSystemService(ShortcutManager::class.java)

        val shortcut: ShortcutInfo = ShortcutInfo.Builder(context, "second_shortcut")
                .setShortLabel(getString(R.string.str_shortcut_two))
                .setLongLabel(getString(R.string.str_shortcut_two_desc))
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                .setIntent(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.in")
                    )
                )
                .build()
            shortcutManager!!.dynamicShortcuts = Arrays.asList(shortcut)
    }
}*/

