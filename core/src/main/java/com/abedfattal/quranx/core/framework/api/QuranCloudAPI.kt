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
    @GET(QuranCloudEndpoints.languages)
    suspend fun getSupportedLanguage(): Response<Meta.SupportedLanguage>

    //Editions
    @GET(QuranCloudEndpoints.textFormatEditions)
    suspend fun getTextEditions(): Response<Meta.Editions>

    @GET(QuranCloudEndpoints.audioFormatEditions)
    suspend fun getAudioEditions(): Response<Meta.Editions>

    @GET(QuranCloudEndpoints.editionTypes)
    suspend fun getEditionsTypes(): Response<Meta.EditionsType>

    @GET("${QuranCloudEndpoints.editionsByTypeParam}?{type}")
    suspend fun getEditionsByType(@Path("type") type: String): Response<Meta.Editions>

    @GET(QuranCloudEndpoints.edition)
    suspend fun getEditions(
        @Query("format") format: String,
        @Query("language") language: String,
        @Query("type") type: String,
    ): Response<Meta.Editions>

    //Quran
    @GET(QuranCloudEndpoints.quranByEditionParam)
    suspend fun getQuranBook(@Path("edition") editionId: String): Response<Quran.QuranBook>

    @GET(QuranCloudEndpoints.ayahByEditionAndNumberParams)
    suspend fun getAya(
        @Path("number") numberInMushaf: Int,
        @Path("edition") editionId: String,
    ): Response<Ayat.AyaData>

    @GET(QuranCloudEndpoints.pageByNumberAndEditionParams)
    suspend fun getPage(
        @Path("page_number") page: Int,
        @Path("edition") editionId: String
    ): Response<Ayat.QuranPageData>

    @GET(QuranCloudEndpoints.juzByNumberAndEditionParams)
    suspend fun getJuz(
        @Path("juz_number") juz: Int,
        @Path("edition") editionId: String
    ): Response<Ayat.QuranJuzData>


    //Search
    @GET(QuranCloudEndpoints.searchQueryByLanguageParams)
    suspend fun searchAllQuran(
        @Path("query") query: String,
        @Path("language_code") language: String,
    ): Response<Search.QuranResults>

    @GET(QuranCloudEndpoints.searchQueryByEdition)
    suspend fun searchQuranByEdition(
        @Path("query") query: String,
        @Path("edition") editionId: String,
    ): Response<Search.QuranResults>


}
