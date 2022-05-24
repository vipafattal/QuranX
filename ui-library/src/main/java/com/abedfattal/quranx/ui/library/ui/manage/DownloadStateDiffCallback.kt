package com.abedfattal.quranx.ui.library.ui.manage

import androidx.recyclerview.widget.DiffUtil
import com.abedfattal.quranx.ui.library.framework.download.EditionDownloadJob

import com.abedfattal.quranx.ui.library.models.EditionDownloadState

internal class DownloadStateDiffCallback(
    private val oldList: List<Pair<EditionDownloadState, EditionDownloadJob?>>,
    private val newList: List<Pair<EditionDownloadState, EditionDownloadJob?>>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].first.edition.identifier == newList[newItemPosition].first.edition.identifier
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].first.isDownloaded == newList[newItemPosition].first.isDownloaded &&
        oldList[oldItemPosition].second?.downloadState == newList[newItemPosition].second?.downloadState
    }
}