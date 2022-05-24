package com.abedfattal.quranx.ui.library.ui.read.adapter

import androidx.recyclerview.widget.DiffUtil
import com.abedfattal.quranx.core.model.AyaWithInfo

internal class BookmarkStateDiffCallback(
    private val oldList: List<AyaWithInfo>,
    private val newList: List<AyaWithInfo>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].aya.number == newList[newItemPosition].aya.number
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isBookmarked == newList[newItemPosition].isBookmarked
    }
}