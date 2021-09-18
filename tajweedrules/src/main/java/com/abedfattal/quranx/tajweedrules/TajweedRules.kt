package com.abedfattal.quranx.tajweedrules

import com.abedfattal.quranx.tajweedprocessor.Tajweed
import com.abedfattal.quranx.tajweedrules.model.PartRule
import com.abedfattal.quranx.tajweedrules.model.RulesTypeList
import com.abedfattal.quranx.tajweedrules.model.WordsWithRules

/**
 * [TajweedRules] the main class to list tajweed rules e.g. when user clicks on aya you could
 * list all rules in the aya.
 *
 * It only supports the text edition of `quran-tajweed`.
 *
 * @property tajweed needed to get user's (aka the developer) color [MetaColors] of tajweed rules
 * and to extract the rules of text.
 */
class TajweedRules(private val tajweed: Tajweed) {

    private val rulesTypeList = RulesTypeList(tajweed.metaColors)

    /**
     * @return A list [WordsWithRules] of all supported tajweed rules in the `quran-tajweed` edition.
     */
    fun getAllRules() = rulesTypeList.rulesMap.values

    /**
     * List all the reciting rules in each word of [ayaText].
     *
     * @sample com.abedfattal.quranx.sample.tajweedrules.rulesdialog.TajweedRulesBottomSheet
     * @sample com.abedfattal.quranx.sample.tajweedrules.rulesdialog.word.WordRulesListAdapter
     *
     * @return A list [WordsWithRules] of only tajweed rules that exists in [ayaText] `quran-tajweed` edition.
     * Note that [ayaText] must be derivative from `quran-tajweed` otherwise it won't work.
     */
    fun getRulesOfAya(ayaText: String): List<WordsWithRules> {
        //TODO add disabled rules
        val wordsWithRulesList = mutableListOf<WordsWithRules>()
        tajweed.onEachWord(ayaText) { wordWithItsMeta ->

            val word = tajweed.getStyledWords(wordWithItsMeta.word)
            val rulesInAya = mutableListOf<PartRule>()

            tajweed.applyActionsSplits(wordWithItsMeta.rawMetaSplits) { meta, ayahSplit ->
                rulesTypeList.rulesMap[meta]?.let { rule ->
                    rulesInAya += PartRule(ayahSplit, rule)
                }
            }

            //Only adds the words that have reciting rules.
            if (rulesInAya.isNotEmpty())
                wordsWithRulesList += WordsWithRules(word, rulesInAya)
        }

        return wordsWithRulesList
    }
}