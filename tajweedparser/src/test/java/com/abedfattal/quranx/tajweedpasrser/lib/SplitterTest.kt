package com.abedfattal.quranx.tajweedpasrser.lib

import com.abedfattal.quranx.tajweedpasrser.Tajweed
import junit.framework.TestCase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

class SplitterTest : TestCase() {
    private val rawAyah = "ٱلْحَمْدُ لِلَّهِ رَبِّ [h:4[ٱ]لْعَ[n[ـٰ]لَم[p[ِي]نَ"

    fun testGetAyahSplits_checkingSplits() {
        val splits = Splitter.getAyahSplits(rawAyah)
        MatcherAssert.assertThat(splits.size, CoreMatchers.`is`(13))
    }

    fun testGetAyahWordSplits_checkingSplits() {
        val splits = Splitter.getAyahWordSplits(rawAyah)
        MatcherAssert.assertThat(splits.size, CoreMatchers.`is`(13))
    }

    fun testDoOnSplits_checkingRule() {
        val splits = Splitter.getAyahSplits(rawAyah)
        val tajweedRulesOfAya = mutableListOf<String>()

        Splitter.doOnSplits(splits) { meta, ayahSplit ->
            if(meta!=null)
                tajweedRulesOfAya += ayahSplit
        }

        MatcherAssert.assertThat(tajweedRulesOfAya.size, CoreMatchers.`is`(3))
    }
}