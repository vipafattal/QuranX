package com.abedfattal.quranx.sample.wordsprocessor

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.sample.R
import com.abedfattal.quranx.sample.utils.inflate
import com.abedfattal.quranx.wordsprocessor.model.AyaWordV2

class WordsAdapter(private val wordsList: List<AyaWordV2>) :
        RecyclerView.Adapter<WordsAdapter.ItemHolder>() {


        override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ItemHolder {
            val view = viewGroup.inflate(R.layout.item_words_parser)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(itemHolder: ItemHolder, index: Int) {
            val word = wordsList[index]
            itemHolder.bind(word)
        }

        override fun getItemCount(): Int = wordsList.size

        inner class ItemHolder(holderView: View) : RecyclerView.ViewHolder(holderView) {

            private val arabicWordTextView: TextView = holderView.findViewById(R.id.word_arabic_text)
            private val englishWordTextView: TextView = holderView.findViewById(R.id.word_english_translation_text)
            private val englishTransliterationWordTextView: TextView = holderView.findViewById(R.id.word_english_transliteration_text)

            fun bind(word: AyaWordV2) {
                arabicWordTextView.text = word.arabicWord
                englishWordTextView.text = word.wordTranslation
                englishTransliterationWordTextView.text = word.wordTranslation
            }
        }

}