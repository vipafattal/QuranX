package com.abedfattal.quranx.sample.tajweedrules.rulesdialog.word

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.sample.R
import com.abedfattal.quranx.sample.utils.inflate
import com.abedfattal.quranx.tajweedrules.model.PartRule

@Suppress("ReplaceGetOrSet")
class WordRulesListAdapter(
    private val wordRulesList: List<PartRule>
) :
    RecyclerView.Adapter<WordRulesListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.inflate(R.layout.item_tajweed_word_rules)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tajweedRule = wordRulesList[position]
            holder.bind(tajweedRule)

    }

    override fun getItemCount(): Int = wordRulesList.size

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {


        private val tajweedRuleCategoryView: CardView = itemView.findViewById(R.id.tajweed_rule_color)
        private val tajweedRuleNameView: TextView = itemView.findViewById(R.id.tajweed_rule_name)
        private val tajweedRulePartView: TextView = itemView.findViewById(R.id.tajweed_verse_part)


        fun bind(tajweedRule: PartRule) {
            val ruleColor = Color.parseColor(tajweedRule.ruleType.color)

            tajweedRuleCategoryView.setCardBackgroundColor(ruleColor)
            tajweedRulePartView.setTextColor(ruleColor)
            tajweedRulePartView.setText(tajweedRule.part)
            tajweedRuleNameView.setText(tajweedRule.ruleType.name)
        }
    }


}