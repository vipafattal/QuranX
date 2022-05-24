package com.abedfattal.quranx.ui.library.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.os.bundleOf
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.common.ShortcutHelper
import com.abedfattal.quranx.ui.common.models.ShortcutDetails
import com.abedfattal.quranx.ui.library.LibraryUIConstants
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ReadLibrary

class EditionShortcut(private val edition: Edition, private val context: Context) {

    private val identifier = edition.identifier
    private val name = edition.name

    fun create() {
        val shortcutDetails = ShortcutDetails(
            id = identifier,
            label = name,
            icon = R.drawable.ic_book_shortcut
        )
        ShortcutHelper.create(context, shortcutDetails, createShortcutIntent())
    }


    fun addToDynamicShortcut() {

        val shortcutDetails =
            ShortcutDetails(identifier, name, R.drawable.ic_book_shortcut)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutHelper.createDynamicShortcut(
                context,
                shortcutDetails,
                createShortcutIntent()
            )
        }
    }

    fun disableShortcut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutHelper.disableShortcut(context, identifier)
        }
    }

    private fun createShortcutIntent() =
        Intent(context, Class.forName(ReadLibrary.mainActivityPath)).apply {
            putExtras(edition.toJsonBundle())
        }
}

fun Edition.toJsonBundle(): Bundle = bundleOf(
    LibraryUIConstants.LIBRARY_BOOK_EDITION_ARG to
            toJson()
)


