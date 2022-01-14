package com.abedfattal.quranx.ui.library.ui.read.processor.word

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.ui.common.extensions.view.inflater
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.wordsprocessor.model.AyaWordV2
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import com.abedfattal.quranx.ui.library.utils.setTextSizeFromType
import kotlinx.android.synthetic.main.item_read_words_by_word.view.*

class WordsAdapter(private val wordsList: List<AyaWordV2>) :
    RecyclerView.Adapter<WordsAdapter.Holder>() {
    private val textSizeType = LibraryPreferences.getFontSize()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(textSizeType, parent.inflater(R.layout.item_read_words_by_word))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(wordsList[position])
    }

    override fun getItemCount(): Int = wordsList.size

    class Holder(textSizeType: Int, itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.quran_word_text.setTextSizeFromType(textSizeType)
        }

        fun bind(word: AyaWordV2) {
            itemView.quran_word_text.text = word.arabicWord
            itemView.english_word_text.text =
                if (LibraryPreferences.wordByWordTranslation) word.wordTranslation else word.wordTransliteration
        }
    }
}