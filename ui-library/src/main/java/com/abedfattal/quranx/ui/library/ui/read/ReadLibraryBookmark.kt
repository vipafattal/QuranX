package com.abedfattal.quranx.ui.library.ui.read

import com.abedfattal.quranx.core.model.AyaWithInfo

interface ReadLibraryBookmark {

    fun onLibraryAyaBookmarked(ayaWithInfo: AyaWithInfo): AyaWithInfo

}