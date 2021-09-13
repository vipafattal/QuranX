package com.abedfattal.quranx.tajweed

import com.abedfattal.quranx.tajweed.parser.Tajweed
import com.abedfattal.quranx.tajweed.rules.TajweedRules
import junit.framework.TestCase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

class TajweedRulesTest : TestCase() {

    lateinit var tajweedRules: TajweedRules
    public override fun setUp() {
        tajweedRules = TajweedRules(Tajweed())
    }

    fun testGetRulesOfAya() {
        val ayaText =
            "ٱلْحَمْدُ لِلَّهِ رَبِّ [h:4[ٱ]لْعَ[n[ـٰ]لَم[p[ِي]نَ"

        val rulesOfAya = tajweedRules.getRulesOfAya(ayaText)
        MatcherAssert.assertThat(rulesOfAya.size, CoreMatchers.`is`(3))
    }
}