package com.abedfattal.quranx.core.framework.data

import com.abedfattal.quranx.core.framework.api.QURAN_CLOUD_BASE_URL
import com.abedfattal.quranx.core.framework.data.DataSources.remoteDataSource
import com.abedfattal.quranx.core.framework.data.repositories.RepositoriesBuilder
import com.abedfattal.quranx.core.framework.data.sources.LocalBasedDataSource
import com.abedfattal.quranx.core.framework.data.sources.LocalDataSource
import com.abedfattal.quranx.core.framework.data.sources.RemoteDataSource

/**
 * [DataSources] it's your entry point to access library data repositories.
 * Properties initialization are all done lazy (on-user-demand), to save resources.
 *
 * Have a look at sample for basic idea how to construct a [DataSources].
 *
 *  @sample com.abedfattal.quranx.sample.core.viewmodels.LanguageViewModel
 *
 * @property RemoteDataSource that interacts with cloud API which is based on [QURAN_CLOUD_BASE_URL].
 * @property remoteDataSource performs local actions, such as downloading quran, querying supported languages...
 * @property LocalBasedDataSource performs local queries as first priority, by means if the queries don't local result it'll call remote methods to get data...
 *
 */
object DataSources {

    /**
     * Use it to access [RemoteDataSource] repositories, whenever you want to execute queries directly from the cloud API [QURAN_CLOUD_BASE_URL].
     */
    val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSource(
            RepositoriesBuilder.remoteQuranRepository,
            RepositoriesBuilder.remoteEditionsRepository,
            RepositoriesBuilder.remoteLanguagesRepository,
        )
    }

    /**
     * Use it to access [LocalDataSource] repositories, whenever you want to execute local queries on the database.
     */
    val localDataSource: LocalDataSource by lazy {
        LocalDataSource(
            RepositoriesBuilder.localQuranRepository,
            RepositoriesBuilder.localEditionsRepository,
            RepositoriesBuilder.localBookmarksRepository,
            RepositoriesBuilder.localLanguagesRepository,
        )
    }


    /**
     * Use it to access [LocalBasedDataSource] repositories, whenever you want to get data remote source if data not available in the local database.
     */
    val localBasedDataSource: LocalBasedDataSource by lazy {
        LocalBasedDataSource(
            RepositoriesBuilder.localBasedQuranRepository,
            RepositoriesBuilder.localBasedEditionsRepository,
            RepositoriesBuilder.localBasedLanguagesRepository,
        )
    }
}