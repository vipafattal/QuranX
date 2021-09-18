package com.abedfattal.quranx.tajweedrules.model

import android.text.Spannable
import com.abedfattal.quranx.tajweedrules.TajweedRules

/***
 * This class is used by [TajweedRules] to list all [rules] in each word.
 *
 * @property ayaWord represents a word of verse that is colored according to [Tajweed.metaColors].
 * @property rules represents the rules in this [ayaWord].
 */
data class WordsWithRules internal constructor(val ayaWord:Spannable, val rules:List<PartRule>)