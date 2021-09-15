package com.abedfattal.quranx.sample.wordparser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.utils.onSuccess
import com.abedfattal.quranx.sample.QURAN_EDITION
import com.abedfattal.quranx.sample.QURAN_WORDS_EDITION
import com.abedfattal.quranx.sample.wordparser.model.VerseWithItsWord
import com.abedfattal.quranx.wordparser.WordByWordEnglish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine

class VerseViewModel : ViewModel() {

    private val dataSource = DataSources.remoteDataSource
    private val repository = dataSource.quranRepository

    fun getVerseWithTranslation(
        verseNumber: Int,
    ): LiveData<VerseWithItsWord?> {

        return combine(
            repository.getAya(verseNumber, QURAN_EDITION),
            repository.getAya(verseNumber, QURAN_WORDS_EDITION)
        ) { simpleQuranProcess: ProcessState<Aya>, wordByWordProcess: ProcessState<Aya> ->

            /**
             * If you're sure the verse of [QURAN_EDITION] or [QURAN_WORDS_EDITION] then you cloud simply omit the code below and
             * call from [DataSources.localDataSource]:
             *
             * val dataSource = DataSources.localDataSource
             * val repository = dataSource.quranRepository
             * repository.getAyaEditions(verseNumber,QURAN_EDITION,QURAN_WORDS_EDITION)
             *
             */
            val processes = listOf(
                simpleQuranProcess,
                wordByWordProcess
            ).map { it.transformProcessType<VerseWithItsWord>() }

            if (simpleQuranProcess is ProcessState.Success && wordByWordProcess is ProcessState.Success) {
                val wordByWords = WordByWordEnglish.getWordOfAyaV2(wordByWordProcess.data!!.text)
                val simpleQuranVerse = simpleQuranProcess.data!!

                return@combine ProcessState.Success(VerseWithItsWord(simpleQuranVerse, wordByWords))
            } else
                return@combine processes.firstOrNull { it is ProcessState.Failed } ?: ProcessState.Loading()

        }.onSuccess.asLiveData(Dispatchers.IO)

    }

}