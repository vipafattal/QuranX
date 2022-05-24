package com.abedfattal.quranx.ui.library

import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest

object ReadLibraryDeeplinkNavigation {

        const val bookmarks = "bookmarks_fragment"
        const val books = "books_fragment"

        fun navigate(page:String): NavDeepLinkRequest {
            return NavDeepLinkRequest.Builder
                .fromUri("android-app://com.abed.ui.library/$page".toUri())
                .build()
        }
    }