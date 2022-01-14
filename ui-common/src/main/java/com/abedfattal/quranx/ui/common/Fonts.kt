package com.abedfattal.quranx.ui.common

import android.content.Context
import android.graphics.Typeface
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

object Fonts {

    //Arabic fonts.
    @JvmField
    val AR_NORMAL_FONT = R.font.ar_ge_flow

    @JvmField
    val AR_TRANSLATION_FONT = R.font.ar_ge_flow

    @JvmField
    val EN_NORMAL_FONT = R.font.open_sans_regular

    @JvmField
    val EN_TRANSLATION_FONT = R.font.open_sans_regular

    fun getNormalFont(context: Context, languageCode: String): Typeface {
        @FontRes
        val fontId: Int = when (languageCode) {
            "ar" -> AR_NORMAL_FONT
            "en" -> EN_NORMAL_FONT
            else /*NotImplementedLanguage*/ -> EN_NORMAL_FONT
        }
        return ResourcesCompat.getFont(context, fontId)!!
    }

    fun getTranslationFont(context: Context, languageCode: String): Typeface {
        @FontRes
        val fontId: Int = when (languageCode) {
            "ar" -> AR_TRANSLATION_FONT
            "en" -> EN_TRANSLATION_FONT
            else /*NotImplementedLanguage*/ -> EN_TRANSLATION_FONT
        }
        return ResourcesCompat.getFont(context, fontId)!!
    }
}