package com.abedfattal.quranx.tajweedprocessor

import junit.framework.TestCase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

class TajweedTest : TestCase() {
    private val rawAyah = "ٱلَّذِينَ يُؤْمِنُونَ بِ[h:13[ٱ]لْغَيْبِ وَيُقِيمُونَ [h:14[ٱ][l[ل]صَّلَ[s[و][n[ٲ]ةَ وَمِ[g[مّ]َا رَزَ[q:15[قْ]نَ[n[ـٰ]هُمْ يُ[f:16[نف]ِق[p[ُو]نَ"

    fun testGetStyledAyahWords_checkingWordsCount() {
        val wordsOfAya = Tajweed().getStyledAyahWords(rawAyah)
        MatcherAssert.assertThat(wordsOfAya.size, CoreMatchers.`is`(4))
    }
}