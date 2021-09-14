package com.abedfattal.quranx.sample.tajweedrules

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.sample.R
import com.abedfattal.quranx.sample.tajweedrules.rulesdialog.TajweedRulesBottomSheet
import com.abedfattal.quranx.sample.utils.inflate

class TajweedRulesAdapter(private val surahAyaList: List<Aya>) :
    RecyclerView.Adapter<TajweedRulesAdapter.ItemHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ItemHolder {
        val view = viewGroup.inflate(R.layout.item_tajweed_parser)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(itemHolder: ItemHolder, index: Int) {
        val aya = surahAyaList[index]
        itemHolder.bind(aya)
    }

    override fun getItemCount(): Int = surahAyaList.size

    inner class ItemHolder(holderView: View) : RecyclerView.ViewHolder(holderView) {

        private val tajweed = TajweedRulesActivity.tajweed
        private val ayaTextView: TextView = holderView.findViewById(R.id.ayahText)
        private val ayaNumberTextView: TextView = holderView.findViewById(R.id.ayahNumber)

        init {
            itemView.setOnClickListener {
                val aya = surahAyaList[adapterPosition]
                val fragmentManager = (it.context as TajweedRulesActivity).supportFragmentManager
                TajweedRulesBottomSheet.showDialog(fragmentManager, aya)
            }
        }

        fun bind(aya: Aya) {
            //Apply text style on aya text.
            ayaTextView.text = tajweed.getStyledWords(aya.text)
            ayaNumberTextView.text = aya.numberInSurah.toString()
        }

    }

}