package com.abedfattal.quranx.core.framework.api.models

import com.abedfattal.quranx.core.model.Edition
import com.google.gson.annotations.SerializedName

/** @suppress */

object Meta {

    data class Editions(
        @SerializedName("data") var editions: List<Edition>,
        val code: Int,
        val status: String
    )

    data class EditionsType(
        @SerializedName("data") var editionsType: List<String>,
        val code: Int,
        val status: String
    )

    data class SupportedLanguage(
        @SerializedName("data")
        val languages: List<String>,
        val code: Int, val status: String
    )
}