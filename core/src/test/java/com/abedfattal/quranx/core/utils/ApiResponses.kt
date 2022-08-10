package com.abedfattal.quranx.core.utils

import com.abedfattal.quranx.core.framework.api.QuranCloudEndpoints


object ApiResponses {
    const val QURAN_1 = "quran-book-1"
    const val QURAN_2 = "quran-book-2"
    const val languagesEdition = "editions-languages"
    const val aya = "aya"
    const val page = "page"
    const val juz = "juz"
    const val searchByLang = "search-by-language"
    const val searchByEdition = "search-by-edition"
    const val textFormatEditions = "editions-format-text"
    const val audioFormatEditions = "editions-format-audio"
    const val editionTypes = "edition-types"
    const val editions = "editions"

    @JvmField
    val endpointsWithResponse = mapOf(
        QuranCloudEndpoints.quranByEditionParam to ApiResponses.QURAN_1,
        QuranCloudEndpoints.languages to ApiResponses.languagesEdition,
        QuranCloudEndpoints.ayahByEditionAndNumberParams to ApiResponses.aya,
        QuranCloudEndpoints.pageByNumberAndEditionParams to ApiResponses.page,
        QuranCloudEndpoints.juzByNumberAndEditionParams to ApiResponses.juz,
        QuranCloudEndpoints.searchQueryByLanguageParams to ApiResponses.searchByLang,
        QuranCloudEndpoints.searchQueryByEdition to ApiResponses.searchByEdition,
        QuranCloudEndpoints.textFormatEditions to ApiResponses.textFormatEditions,
        QuranCloudEndpoints.audioFormatEditions to ApiResponses.audioFormatEditions,
        QuranCloudEndpoints.editionTypes to ApiResponses.editionTypes,
        QuranCloudEndpoints.edition to ApiResponses.editions,
    )


    fun buildFullDir(file:String,code: Int) = "api-response/$file-$code.json"

}

