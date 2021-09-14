package com.abedfattal.quranx.sample.tajweedparser

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.sample.R
import com.abedfattal.quranx.sample.utils.inflate
import com.abedfattal.quranx.tajweedpasrser.Tajweed

class TajweedAdapter(private val surahAyaList: List<Aya>, private val tajweed: Tajweed) :
    RecyclerView.Adapter<TajweedAdapter.ItemHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ItemHolder {
        val view = viewGroup.inflate(R.layout.item_tajweed_parser)
        return ItemHolder(tajweed, view)
    }

    override fun onBindViewHolder(itemHolder: ItemHolder, index: Int) {
        val aya = surahAyaList[index]
        itemHolder.bind(aya)
    }

    override fun getItemCount(): Int = surahAyaList.size

    class ItemHolder(private val tajweed: Tajweed, holderView: View) :
        RecyclerView.ViewHolder(holderView) {

        private val ayaTextView: TextView = holderView.findViewById(R.id.ayahText)
        private val ayaNumberTextView: TextView = holderView.findViewById(R.id.ayahNumber)

        fun bind(aya: Aya) {
            //Apply text style on aya text.
            ayaTextView.text = tajweed.getStyledWords(aya.text)
            ayaNumberTextView.text = aya.numberInSurah.toString()
        }
    }
}