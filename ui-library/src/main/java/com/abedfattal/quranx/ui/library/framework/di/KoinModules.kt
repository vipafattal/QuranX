package com.abedfattal.quranx.ui.library.framework.di

import com.abedfattal.quranx.ui.library.ui.bookmarks.BookmarksViewModel
import com.abedfattal.quranx.ui.library.ui.manage.ManageLibraryViewModel
import com.abedfattal.quranx.ui.library.ui.read.ReadLibraryViewModel
import com.abedfattal.quranx.ui.library.ui.settings.TranslationQuranViewModel
import com.abedfattal.quranx.ui.library.ui.settings.textSize.TextSizeViewModel
import com.abedfattal.quranx.ui.library.ui.simpleLoading.LoadingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal object KoinModules {

    private val viewModelFactories = module {
        viewModel { ReadLibraryViewModel() }
        viewModel { ManageLibraryViewModel() }
        viewModel { TranslationQuranViewModel() }
        viewModel { TextSizeViewModel() }
        viewModel { LoadingViewModel() }
        viewModel { BookmarksViewModel() }
    }


    val modules = listOf(viewModelFactories)

}
