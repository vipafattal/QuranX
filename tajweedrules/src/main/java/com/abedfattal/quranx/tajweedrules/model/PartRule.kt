package com.abedfattal.quranx.tajweedrules.model

/**
 * Represents single a reciting rule of a word.
 *
 * @property part the part of the word that has this rule.
 * @property ruleType the rule info.
 *
 */
data class PartRule internal constructor(val part: String, val ruleType: TajweedRuleType)