package com.brilliancesoft.readlib.ui.read.processor.tajweed

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.widget.TextView
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.tajweedprocessor.Tajweed
import com.abedfattal.quranx.ui.library.models.TouchedWord
import com.abedfattal.quranx.ui.library.ui.read.processor.word.touch.CustomClickableSpan
import com.abedfattal.quranx.ui.library.utils.QURAN_TAJWEED_ID

class TajweedSpannable(val context: Context, val aya: Aya, val tajweed: Tajweed) {
    init {
        require(aya.ayaEdition == QURAN_TAJWEED_ID)
    }

    inline fun colorWithTouchListener(crossinline onWordTouch: (TouchedWord) -> Unit): Spannable {
        val spanText = SpannableStringBuilder()
        aya.text.split(' ').forEachIndexed { index, word ->

            val tajweedSpannableWord = tajweed.getStyledWords(if (index != 0) " $word" else word)

            tajweedSpannableWord.setSpan(object : CustomClickableSpan() {
                override fun onClick(widget: TextView, x: Int, y: Int) {
                    val touchedWord = TouchedWord(index, word, aya)
                    onWordTouch(touchedWord)
                }
            }, 0, tajweedSpannableWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            spanText.append(tajweedSpannableWord)
        }
        return spanText
    }

}