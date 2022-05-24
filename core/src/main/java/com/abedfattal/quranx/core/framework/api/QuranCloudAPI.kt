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
    @GET(ApiEndpoints.languages)
    suspend fun getSupportedLanguage(): Response<Meta.SupportedLanguage>

    //Editions
    @GET(ApiEndpoints.textEditions)
    suspend fun getTextEditions(): Response<Meta.Editions>

    @GET(ApiEndpoints.audioEditions)
    suspend fun getAudioEditions(): Response<Meta.Editions>

    @GET(ApiEndpoints.editionsByType)
    suspend fun getEditionsTypes(): Response<Meta.EditionsType>

    @GET("${ApiEndpoints.editionsByTypeParam}?{type}")
    suspend fun getEditionsByType(@Path("type") type: String): Response<Meta.Editions>

    @GET(ApiEndpoints.editions)
    suspend fun getEditions(
        @Query("format") format: String,
        @Query("language") language: String,
        @Query("type") type: String,
    ): Response<Meta.Editions>

    //Quran
    @GET(ApiEndpoints.quranByEditionParam)
    suspend fun getQuranBook(@Path("edition") editionId: String): Response<Quran.QuranBook>

    @GET(ApiEndpoints.ayahByEditionAndNumberParams)
    suspend fun getAya(
        @Path("number") numberInMushaf: Int,
        @Path("edition") editionId: String,
    ): Response<Ayat.AyaData>

    @GET(ApiEndpoints.pageByNumberAndEditionParams)
    suspend fun getPage(
        @Path("page_number") page: Int,
        @Path("edition") editionId: String
    ): Response<Ayat.QuranPageData>

    @GET(ApiEndpoints.juzByNumberAndEditionParams)
    suspend fun getJuz(
        @Path("juz_number") juz: Int,
        @Path("edition") editionId: String
    ): Response<Ayat.QuranJuzData>


    //Search
    @GET(ApiEndpoints.searchQueryByLanguageParams)
    suspend fun searchAllQuran(
        @Path("query") query: String,
        @Path("language_code") language: String,
    ): Response<Search.QuranResults>

    @GET(ApiEndpoints.searchQueryByEdition)
    suspend fun searchQuranByEdition(
        @Path("query") query: String,
        @Path("edition") editionId: String,
    ): Response<Search.QuranResults>


}
