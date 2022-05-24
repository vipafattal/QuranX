package com.abedfattal.quranx.ui.common

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

object Fonts {


    fun getNormalFont(context: Context, languageCode: String): Typeface {
        return ResourcesCompat.getFont(context, R.font.normal_regular)!!
    }

    fun getNormalFont(): Typeface {
        return ResourcesCompat.getFont(CommonUI.context, R.font.normal_regular)!!
    }


    fun getLocalizedTitleFont(context: Context, languageCode: String): Typeface {
        return ResourcesCompat.getFont(context, R.font.normal_regular)!!
    }

    fun getTitleFonts(): Typeface {
        return ResourcesCompat.getFont(CommonUI.context, R.font.normal_regular)!!

    }

    /*  fun getTranslationFont(context: Context, languageCode: String): Typeface {
          @FontRes
          val fontId: Int = when (languageCode) {
              "ar" -> AR_TRANSLATION_FONT
              "en" -> EN_TRANSLATION_FONT
              else *//*NotImplementedLanguage*//* -> EN_TRANSLATION_FONT
        }
        return ResourcesCompat.getFont(context, fontId)!!
    }*/
}