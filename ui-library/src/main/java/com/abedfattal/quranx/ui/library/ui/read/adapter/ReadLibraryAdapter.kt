package com.abedfattal.quranx.ui.library.ui.read.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.core.model.AyatInfoWithTafseer
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.tajweedprocessor.Tajweed
import com.abedfattal.quranx.tajweedprocessor.model.MetaColors
import com.abedfattal.quranx.ui.common.extensions.view.inflater
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.bookmarks.BookmarksViewModel
import com.abedfattal.quranx.ui.library.ui.read.ReadLibraryFragment


open class ReadLibraryAdapter(
    private val bookmarksViewModel: BookmarksViewModel,
    private val context: Context,
    private var ayatWithTafseer: AyatInfoWithTafseer,
    private val edition: Edition,
) :
    RecyclerView.Adapter<ViewHolderBuilder.Build>() {

    private var tajweed: Tajweed = ReadLibraryFragment.getDefaultTajweed()
    private val readLibraryViewHolderBuilder =
        ViewHolderBuilder(context, bookmarksViewModel, tajweed, ayatWithTafseer, edition)

    fun updateBookmarkedAyat(newData: AyatInfoWithTafseer) {
        val comparableList = (ayatWithTafseer.tafseerList ?: ayatWithTafseer.quranList)!!
        val newList = (newData.tafseerList ?: newData.quranList)!!
        val diffCallback = BookmarkStateDiffCallback(comparableList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        ayatWithTafseer = newData
        diffResult.dispatchUpdatesTo(this)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun changeTajweedColors(metaColors: MetaColors) {
        tajweed = Tajweed(metaColors)
        readLibraryViewHolderBuilder.tajweed = tajweed
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBuilder.Build {
        val view = parent.inflater(R.layout.item_read_library)
        return readLibraryViewHolderBuilder.Build(view)
    }

    override fun getItemCount(): Int =
        ayatWithTafseer.tafseerList?.size ?: ayatWithTafseer.quranList!!.size

    override fun onBindViewHolder(holder: ViewHolderBuilder.Build, position: Int) {
        val tafseerAya = ayatWithTafseer.tafseerList?.get(position)
        val quranText = ayatWithTafseer.quranList?.get(position)
        holder.bindData(quranText, tafseerAya, ayatWithTafseer.surah!!)
    }

}
