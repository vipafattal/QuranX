package com.abedfattal.quranx.core.framework.data.sources

import com.abedfattal.quranx.core.framework.api.QURAN_CLOUD_BASE_URL
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteEditionsRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteLanguagesRepository
import com.abedfattal.quranx.core.framework.data.repositories.remote.RemoteQuranRepository
import com.abedfattal.quranx.core.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Whenever you want to execute queries directly from the cloud API [QURAN_CLOUD_BASE_URL].
 * It interacts with API server to provide [Edition], [Language], [Aya].
 *
 * Note that whenever you called an remote method will get a result of [Flow], which wraps the state connection with [ProcessState].
 *
 * An API [RemoteQuranRepository] depended repository, that performs queries to get verses Quran see [Aya], [AyatWithEdition].
 * An API [RemoteEditionsRepository] depended repository, that performs queries to get available Quran editions see [Edition].
 * An API [RemoteLanguagesRepository] depended repository, mainly to get supported languages by API.
 */
class RemoteDataSource internal constructor(
    val quranRepository: RemoteQuranRepository,
    val editionsRepository: RemoteEditionsRepository,
    val languagesRepository: RemoteLanguagesRepository,
)