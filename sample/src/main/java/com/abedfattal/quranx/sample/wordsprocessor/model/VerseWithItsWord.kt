package com.abedfattal.quranx.sample.wordsprocessor.model

import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.wordsprocessor.model.AyaWordV2

data class VerseWithItsWord(val verse: Aya, val words:List<AyaWordV2>)