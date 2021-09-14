package com.abedfattal.quranx.tajweedpasrser.model

import android.text.Spannable

data class WordsWithRules internal constructor(val ayaWord:Spannable, val rules:List<PartRule>)