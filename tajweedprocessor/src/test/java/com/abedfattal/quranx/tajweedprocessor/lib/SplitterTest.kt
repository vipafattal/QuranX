package com.abedfattal.quranx.tajweedprocessor.lib

import junit.framework.TestCase
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert


class SplitterTest : TestCase() {
    private val rawAyah = "۞ إِ[g[نّ]َ [h:80[ٱ]للَّهَ لَا يَسْتَحْى[o[ِۦٓ] أ[a:133[َن ي]َضْرِبَ مَثَل[a:134[اً م]َّا بَعُوضَ[f:135[ةً ف]َمَا فَوْقَهَا‌ۚ فَأَ[g[مّ]َا [h:136[ٱ]لَّذِينَ ءَامَنُو[s[اْ] فَيَعْلَمُونَ أَ[g[نّ]َهُ [h:137[ٱ]لْحَقُّ م[u:22[ِن ر]َّبِّهِمْ‌ۖ وَأَ[g[مّ]َا [h:138[ٱ]لَّذِينَ كَفَرُو[s[اْ] فَيَقُولُونَ مَاذ[o[َآ] أَرَادَ [h:139[ٱ]للَّهُ بِهَ[n[ـٰ]ذَا مَثَل[a:140[اً‌ۘ ي]ُضِلُّ بِه[n[ِۦ] كَثِي[a:141[رًا و]َيَهْدِى بِه[n[ِۦ] كَثِي[a:142[رًا‌ۚ و]َمَا يُضِلُّ بِه[o[ِۦٓ] إِلَّا [h:143[ٱ]لْفَ[n[ـٰ]سِق[p[ِي]نَ"

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

    fun testGetAyahWordSplits_words() {

        Splitter.getAyahWordSplits(rawAyah).onEach { meta ->
           print(meta)
        }

        MatcherAssert.assertThat(5, CoreMatchers.`is`(3))
    }
}