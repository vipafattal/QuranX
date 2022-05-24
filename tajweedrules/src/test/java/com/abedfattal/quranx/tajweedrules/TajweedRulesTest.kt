package com.abedfattal.quranx.tajweedrules

import com.abedfattal.quranx.tajweedprocessor.Tajweed
import junit.framework.TestCase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

class TajweedRulesTest : TestCase() {

   private lateinit var tajweedRules: TajweedRules
    private val ayaText = "ٱلْحَمْدُ لِلَّهِ رَبِّ [h:4[ٱ]لْعَ[n[ـٰ]لَم[p[ِي]نَ"
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