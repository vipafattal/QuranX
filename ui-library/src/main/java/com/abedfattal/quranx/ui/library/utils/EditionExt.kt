package com.abedfattal.quranx.ui.library.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.os.bundleOf
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.common.CommonUI
import com.abedfattal.quranx.ui.common.ShortcutHelper
import com.abedfattal.quranx.ui.common.models.ShortcutDetails
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ReadLibrary
import com.abedfattal.quranx.ui.library.ui.LibraryPagerFragment

class EditionShortcut(private val edition: Edition, private val context: Context) {

    private val identifier = edition.id
    private val name = edition.name

    fun create() {
        val shortcutDetails = ShortcutDetails(identifier, name, R.drawable.ic_shortcut_book)
        ShortcutHelper.create(context, shortcutDetails, createShortcutIntent())
    }


    fun addToDynamicShortcut() {
        val shortcutDetails = ShortcutDetails(identifier, name, R.drawable.ic_shortcut_book)

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

    private fun createShortcutIntent() = Intent(context, Class.forName(ReadLibrary.appInfo.mainActivityPath)).apply {
        putExtras(edition.toJsonBundle())
    }
}

fun Edition.toJsonBundle(): Bundle = bundleOf(
        LibraryPagerFragment.LIBRARY_BOOK_EDITION_ARG to
        toJson()
    )



val QURAN_UTHMANI = Edition(
    id = "quran-uthmani-quran-academy",
    language = "ar",
    name = "القرآن الكريم برسم العثماني",
    englishName = "Quran Uthmani",
    type = Edition.TYPE_QURAN,
    format = Edition.FORMAT_TEXT
)

@JvmField
val OLD_QURAN_UTHMANI = Edition(
    id = "quran-unicode",
    language = "ar",
    name = "القرآن الكريم برسم العثماني",
    englishName = "Quran Uthmani",
    type = Edition.TYPE_QURAN,
    format = Edition.FORMAT_TEXT
)

@JvmField
val QURAN_SIMPLE = Edition(
    id = "quran-simple-clean",
    language = "ar",
    name = "القرآن الكريم البسيط (بدون تشكيل)",
    englishName = "Quran Uthmani (punctuation-free)",
    type = Edition.TYPE_QURAN,
    format = Edition.FORMAT_TEXT
)

@JvmField
val QURAN_TAJWEED = Edition(
    id = "quran-tajweed",
    language = "ar",
    name = "قرآن مجود (ملون)",
    englishName = "Quran Mujawad (Colored rules)",
    type = Edition.TYPE_QURAN,
    format = Edition.FORMAT_TEXT
)

const val QURAN_TAJWEED_ID = "quran-tajweed"
const val EN_WORD_BY_WORD = "quran-wordbyword-2"

@JvmField
val SUPPORTED_QURAN_EDITIONS = listOf(QURAN_UTHMANI, QURAN_TAJWEED, QURAN_SIMPLE,)

@JvmField
val QURAN_SUPPORTED_EDITIONS_IDS = listOf("quran-simple-clean","quran-tajweed","quran-uthmani-quran-academy","quran-wordbyword-2")

@JvmField
val BLACKLISTED_TRANSLATION = listOf("quran-buck")