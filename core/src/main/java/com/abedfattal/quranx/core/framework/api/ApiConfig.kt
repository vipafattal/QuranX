package com.abedfattal.quranx.core.framework.api

/** @suppress */

const val QURAN_CLOUD_BASE_URL = "http://api.alquran.cloud/v1/"

object ApiEndpoints {

    //Languages
    const val languages = "edition/language"

    //Editions
    const val editions = "edition"
    const val textEditions = "$editions?format=text"
    const val audioEditions = "$editions?format=audio"
    const val editionsByType = "$editions/type"
    const val editionsByTypeParam = "$editionsByType?{type}"

    //Quran
    private const val withFontHack = "?fontHack=true"
    const val quranByEditionParam = "quran/{edition}$withFontHack"
    const val ayahByEditionAndNumberParams = "ayah/{number}/{edition}$withFontHack"
    const val pageByNumberAndEditionParams = "page/{page_number}/{edition}$withFontHack"
    const val juzByNumberAndEditionParams = "juz/{juz_number}/{edition}$withFontHack"
    const val searchQueryByLanguageParams = "search/{query}/all/{language_code}$withFontHack"
    const val searchQueryByEdition = "search/{query}/all/{edition}$withFontHack"

}