package com.abedfattal.quranx.core.framework.data.repositories

import com.abedfattal.quranx.core.framework.data.repositories.local.LocalBookmarksRepository
import com.abedfattal.quranx.core.framework.data.repositories.local.LocalEditionsRepository
import com.abedfattal.quranx.core.framework.data.repositories.local.LocalLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.local.LocalQuranRepository
import com.abedfattal.quranx.core.framework.data.repositories.localbased.LocalBasedEditionsRepository
import com.abedfattal.quranx.core.framework.data.repositories.localbased.LocalBasedLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.localbased.LocalBasedQuranRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteEditionsRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteQuranRepository
import com.abedfattal.quranx.core.framework.db.daos.BookmarksDao
import com.abedfattal.quranx.core.framework.db.daos.EditionsDao
import com.abedfattal.quranx.core.framework.db.daos.LanguagesDao
import com.abedfattal.quranx.core.framework.db.daos.QuranDao
import com.abedfattal.quranx.core.framework.di.Dependencies

internal object RepositoriesBuilder {
    //Database Dao's
    private val quranDao: QuranDao by lazy { Dependencies.db.getQuranDao() }
    private val editionsDao: EditionsDao by lazy { Dependencies.db.getEditionsDao() }
    private val LanguagesDao: LanguagesDao by lazy { Dependencies.db.getLanguagesDao() }
    private val bookmarksDao: BookmarksDao by lazy { Dependencies.db.getBookmarksDao() }

    //LocalDataSource Repo's
    val localQuranRepository by lazy { LocalQuranRepository(quranDao) }
    val localEditionsRepository by lazy { LocalEditionsRepository(editionsDao) }
    val localBookmarksRepository by lazy { LocalBookmarksRepository(bookmarksDao) }
    val localLanguagesRepository by lazy { LocalLanguagesRepository(LanguagesDao) }

    //RemoteDataSource Repo's
    val remoteQuranRepository by lazy { RemoteQuranRepository(Dependencies.api) }
    val remoteLanguagesRepository by lazy { RemoteLanguagesRepository(Dependencies.api) }
    val remoteEditionsRepository by lazy { RemoteEditionsRepository(Dependencies.api) }


    //LocalBasedDataSource Repo's
    val localBasedQuranRepository by lazy {
        LocalBasedQuranRepository(
            remoteQuranRepository,
            localQuranRepository, localEditionsRepository,
        )
    }
    val localBasedEditionsRepository by lazy {
        LocalBasedEditionsRepository(
            remoteEditionsRepository,
            localEditionsRepository,
        )
    }
    val localBasedLanguagesRepository by lazy {
        LocalBasedLanguagesRepository(
            remoteLanguagesRepository,
            localLanguagesRepository,
        )
    }


}