package com.abedfattal.quranx.core.framework.api


import com.abedfattal.quranx.core.framework.api.models.Ayat
import com.abedfattal.quranx.core.framework.api.models.Meta
import com.abedfattal.quranx.core.framework.api.models.Quran
import com.abedfattal.quranx.core.framework.api.models.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** @suppress */
interface QuranCloudAPI {

    //Languages
    @GET("edition/language")
    suspend fun getSupportedLanguage(): Response<Meta.SupportedLanguage>


    //Editions
    @GET("edition?format=text")
    suspend fun getTextEditions(): Response<Meta.Editions>

    @GET("edition?format=audio")
    suspend fun getAudioEditions(): Response<Meta.Editions>

    @GET("edition/type")
    suspend fun getEditionsTypes(): Response<Meta.EditionsType>

    @GET("edition/type/{type}")
    suspend fun getEditionsByType(@Path("type") type: String): Response<Meta.Editions>

    @GET("edition")
    suspend fun getEditions(
        @Query("format") format: String,
        @Query("language") language: String,
        @Query("type") type: String,
    ): Response<Meta.Editions>

    //Quran
    @GET("quran/{edition}?fontHack=true")
    suspend fun getQuranBook(@Path("edition") editionId: String): Response<Quran.QuranBook>

    @GET("ayah/{number}/{edition}?fontHack=true")
    suspend fun getAya(
        @Path("number") numberInMushaf: Int,
        @Path("edition") editionId: String,
    ): Response<Ayat.AyaData>

    @GET("page/{page_number}/{edition}?fontHack=true")
    suspend fun getPage(
        @Path("page_number") page: Int,
        @Path("edition") editionId: String
    ): Response<Ayat.QuranPageData>

    @GET("juz/{juz_number}/{edition}?fontHack=true")
    suspend fun getJuz(
        @Path("juz_number") juz: Int,
        @Path("edition") editionId: String
    ): Response<Ayat.QuranJuzData>


    //Search
    @GET("search/{query}/all/{language_code}?fontHack=true")
    suspend fun searchAllQuran(
        @Path("query") query: String,
        @Path("language_code") language: String,
    ): Response<Search.QuranResults>

    @GET("search/{query}/all/{edition}?fontHack=true")
    suspend fun searchQuranByEdition(
        @Path("query") query: String,
        @Path("edition") editionId: String,
    ): Response<Search.QuranResults>


}
