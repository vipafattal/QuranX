package com.abedfattal.quranx.core.framework.di

import androidx.room.Room
import com.abedfattal.quranx.core.QuranXCore
import com.abedfattal.quranx.core.framework.api.QURAN_CLOUD_BASE_URL
import com.abedfattal.quranx.core.framework.api.QuranCloudAPI
import com.abedfattal.quranx.core.framework.db.LibraryDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/** @suppress */
internal object Dependencies {

    val api: QuranCloudAPI by lazy {

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(65, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        Retrofit.Builder()
            .baseUrl(QURAN_CLOUD_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(QuranCloudAPI::class.java)
    }


    val db: LibraryDatabase by lazy {
        val databaseBuilder = Room.databaseBuilder(
            QuranXCore.app,
            LibraryDatabase::class.java,
            QuranXCore.configuration.databaseName
        )
        QuranXCore.configuration.prepackagedDatabase?.let { databaseBuilder.createFromAsset(it) }
        databaseBuilder.build()
    }

}
