package com.abedfattal.quranx.tajweed.rules

import android.text.Spannable
import com.abedfattal.quranx.tajweed.rules.model.AyaSplitWithTajweed
import com.abedfattal.quranx.tajweed.rules.model.RulesTypeList
import com.abedfattal.quranx.tajweed.parser.Tajweed

/**
 * [TajweedRules] the main class to list tajweed rules e.g. when user clicks on aya you could
 * list all rules in the aya.
 *
 * It only supports the text edition of `quran-tajweed`.
 *
 * @property tajweed needed to get user's (aka the developer) color [com.quranx.tajweedparser.model.MetaColors] of tajweed rules
 * and to extract the rules of text.
 */
class TajweedRules(private val tajweed: Tajweed) {

    private val rulesTypeList = RulesTypeList(tajweed.metaColors)

    /**
     * @return A list [AyaSplitWithTajweed] of all supported tajweed rules in the `quran-tajweed` edition.
     */
    fun getAllRules() = rulesTypeList.rulesMap.values

    /**
     * @return A list [AyaSplitWithTajweed] of only tajweed rules that exists in [ayaText] `quran-tajweed` edition.
     * Note that [ayaText] must be derivative from `quran-tajweed` otherwise it won't work.
     */
    fun getRulesOfAya(ayaText: String): List<AyaSplitWithTajweed> {

        val rulesInAya = mutableListOf<AyaSplitWithTajweed>()

        tajweed.onEachSplit(ayaText) { meta, ayahSplit ->
            rulesTypeList.rulesMap[meta]?.let { rule ->
                rulesInAya += AyaSplitWithTajweed(ayahSplit, rule)
            }
        }
        return rulesInAya
    }
}