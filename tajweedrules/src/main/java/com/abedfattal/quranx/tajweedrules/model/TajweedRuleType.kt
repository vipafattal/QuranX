package com.abedfattal.quranx.tajweedrules.model

/**
 * Represents a reciting rule of Quran.
 *
 * @property category string resource which is the name of rule category like the Arabic Mim and Noon rules.
 * @property name string resource which is the name of rule like the Idgham.
 * @property color color in hex string format use Android Color to convert it to Integer format.
 *
 * Color.parseColor(tajweedRule.ruleType.color)
 */
data class TajweedRuleType internal constructor(
    val category: Int,
    val name: Int,
    val color: String,
)