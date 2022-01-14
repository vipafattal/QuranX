package com.abedfattal.quranx.ui.library.ui.manage

import androidx.recyclerview.widget.DiffUtil

import com.abedfattal.quranx.ui.library.models.EditionDownloadState

internal class DownloadStateDiffCallback(
    private val oldList: List<EditionDownloadState>,
    private val newList: List<EditionDownloadState>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].isDownloaded == newList[newItemPosition].isDownloaded

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isDownloaded == newList[newItemPosition].isDownloaded
    }
}