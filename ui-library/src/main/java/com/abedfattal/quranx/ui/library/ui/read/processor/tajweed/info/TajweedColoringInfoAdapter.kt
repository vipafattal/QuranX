package com.abedfattal.quranx.ui.library.ui.read.processor.tajweed.info

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.tajweedrules.model.TajweedRuleType
import com.abedfattal.quranx.ui.common.extensions.view.inflater
import com.abedfattal.quranx.ui.library.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tajweed_with_header.*
import kotlinx.android.synthetic.main.item_tajweed_word_rules.*

class TajweedColoringInfoAdapter(private val tajweedRulesList: List<TajweedRuleType>) :
    RecyclerView.Adapter<TajweedColoringInfoAdapter.ViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        val tajweedRule = tajweedRulesList.get(position)
        val prvTajweedRule = tajweedRulesList.getOrNull(position - 1)

        return if (tajweedRule.category == prvTajweedRule?.category)
            SIMPLE_LAYOUT
        else
            WITH_HEADER_LAYOUT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.inflater(viewType)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tajweedRule = tajweedRulesList[position]
        if (holder.itemViewType == SIMPLE_LAYOUT)
            holder.bind(tajweedRule)
        else
            holder.bindWithHeader(tajweedRule)
    }

    override fun getItemCount(): Int = tajweedRulesList.size

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

        override val containerView: View = itemView

        fun bindWithHeader(tajweedRule: TajweedRuleType) {
            tajweed_headline.setText(tajweedRule.category)
            bind(tajweedRule)
        }

        fun bind(tajweedRule: TajweedRuleType) {
            tajweed_rule_color.setCardBackgroundColor(Color.parseColor(tajweedRule.color))
            tajweed_rule_name.setText(tajweedRule.name)
        }
    }

    companion object {
        private val SIMPLE_LAYOUT = R.layout.item_tajweed_word_rules
        private val WITH_HEADER_LAYOUT = R.layout.item_tajweed_with_header
    }

}