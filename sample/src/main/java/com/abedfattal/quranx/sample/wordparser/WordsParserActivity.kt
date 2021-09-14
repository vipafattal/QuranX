package com.abedfattal.quranx.sample.wordparser

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.abedfattal.quranx.sample.R
import com.abedfattal.quranx.sample.utils.observer
import com.abedfattal.quranx.sample.utils.viewModelOf

class WordsParserActivity : AppCompatActivity() {

    private val verseViewModel: VerseViewModel by lazy { viewModelOf() }
    private val verseText: TextView by lazy { findViewById(R.id.verse_text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words_parser)

        verseViewModel.getVerseWithTranslation(2).observer(this) {verseWithItsWords->
            verseText.text = verseWithItsWords!!.verse.text
            //TODO add list of words translation view.
        }
    }
}