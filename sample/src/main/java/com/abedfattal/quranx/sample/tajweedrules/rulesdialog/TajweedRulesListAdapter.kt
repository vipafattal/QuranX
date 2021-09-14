package com.abedfattal.quranx.sample.tajweedrules.rulesdialog

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.sample.R
import com.abedfattal.quranx.sample.tajweedrules.rulesdialog.word.WordRulesListAdapter
import com.abedfattal.quranx.sample.utils.inflate
import com.abedfattal.quranx.tajweedpasrser.model.WordsWithRules

@Suppress("ReplaceGetOrSet")
class TajweedRulesListAdapter(
    private val aya: Aya,
    private val tajweedRulesList: List<WordsWithRules>
) :
    RecyclerView.Adapter<TajweedRulesListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.inflate(R.layout.item_tajweed_word)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tajweedRule = tajweedRulesList[position]
            holder.bind(tajweedRule)

    }

    override fun getItemCount(): Int = tajweedRulesList.size

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val tajweedRuleCategoryView: TextView = itemView.findViewById(R.id.tajweed_word)
        private val tajweedRulesRecyclerView: RecyclerView = itemView.findViewById(R.id.tajweed_rules_recyclerview)

        fun bind(tajweedRule: WordsWithRules) {
            tajweedRuleCategoryView.text = tajweedRule.ayaWord
            tajweedRulesRecyclerView.adapter = WordRulesListAdapter(tajweedRule.rules)
        }
    }
}