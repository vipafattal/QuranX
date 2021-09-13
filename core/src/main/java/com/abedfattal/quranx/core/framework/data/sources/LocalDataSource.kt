package com.abedfattal.quranx.core.framework.data.sources

import com.abedfattal.quranx.core.framework.data.repositories.local.LocalBookmarksRepository
import com.abedfattal.quranx.core.framework.data.repositories.local.LocalEditionsRepository
import com.abedfattal.quranx.core.framework.data.repositories.local.LocalLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.local.LocalQuranRepository
import com.abedfattal.quranx.core.framework.data.repositories.localbased.LocalBasedQuranRepository
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.Edition

/**
 * [LocalDataSource] it's your entry point to local library data repositories.
 * Since we have more control on local data more that the remote API, you may see some features that doesn't have an equivalent in [RemoteDataSource].
 * The local storing system is based on [androidx.room.RoomDatabase].
 *
 * To use local data sources you should call appropriate method such as [LocalBasedQuranRepository.downloadQuranBook], [LocalEditionsRepository.addAllEdition], and[LocalLanguagesRepository.addLanguage], etc...
 * A local data source [LocalQuranRepository] that interacts with [Aya] table to save and apply local queries on.
 * A local data source [LocalEditionsRepository] that performs local actions, such as saving and applying local queries on [Edition] table
 * A local data source [LocalLanguagesRepository] mainly, to save and list all the supported language in the API service.
 * A local data source [LocalBookmarksRepository] mainly to help implementing bookmark feature which mostly request by Quran apps users.
 *
 *  Properties initialization are all done lazy (on-user-demand), to save resources.
 */
class LocalDataSource(
    val quranRepository: LocalQuranRepository,
    val editionsRepository: LocalEditionsRepository,
    val bookmarksRepository: LocalBookmarksRepository,
    val languagesRepository: LocalLanguagesRepository,
)