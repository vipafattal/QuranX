package com.abedfattal.quranx.tajweedpasrser

import junit.framework.TestCase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

class TajweedTest : TestCase() {
    private val rawAyah = "ٱلْحَمْدُ لِلَّهِ رَبِّ [h:4[ٱ]لْعَ[n[ـٰ]لَم[p[ِي]نَ"

    fun testGetStyledAyahWords_checkingWordsCount() {
        val wordsOfAya =Tajweed().getStyledAyahWords(rawAyah)
        MatcherAssert.assertThat(wordsOfAya.size, CoreMatchers.`is`(4))
    }

}