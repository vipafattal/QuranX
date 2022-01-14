package com.abedfattal.quranx.ui.library.ui.read.menus

import android.view.Menu
import android.view.MenuInflater
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences

class WordByWordMenu(
    menu: Menu,
    inflater: MenuInflater,
    private val onWordBywordModeChange: () -> Unit
) : ReadingMenu(R.menu.menu_word_by_word, menu, inflater) {

    override fun buildMenu() {
        findItem(R.id.wordbyword_mode).setOnMenuItemClickListener {
            LibraryPreferences.wordByWordTranslation = !LibraryPreferences.wordByWordTranslation
            onWordBywordModeChange.invoke()
            true
        }
    }

}