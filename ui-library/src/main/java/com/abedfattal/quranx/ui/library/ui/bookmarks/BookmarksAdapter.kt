package com.abedfattal.quranx.ui.library.ui.bookmarks

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.ui.common.extensions.view.inflater
import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.common.RemoveListConfirmSnackbar
import com.abedfattal.quranx.ui.common.extensions.view.onClick
import com.abedfattal.quranx.ui.common.isRightToLeft
import com.abedfattal.quranx.ui.common.toLocalizedNumber
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.LibraryPagerFragmentDirections
import kotlinx.android.synthetic.main.item_bookmark.view.*
import kotlinx.android.synthetic.main.item_bookmark_with_header.view.*
import java.util.*

/**
 * Created by ${User} on ${Date}
 */
class BookmarksAdapter(
    private val ayatList: MutableList<AyaWithInfo>,
    private val removeListConfirmSnackbar: RemoveListConfirmSnackbar<AyaWithInfo>
) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(viewType, parent, removeListConfirmSnackbar)

    override fun getItemViewType(position: Int): Int {
        val currentData = ayatList[position]
        val prvData = if (position != 0) ayatList[position - 1] else ayatList[position]
        val prvType = prvData.edition.type

        return when {
            position == 0 -> itemWithHeader
            currentData.edition.type != prvType -> itemWithHeader
            else -> itemBookmark
        }
    }

    override fun getItemCount(): Int {
        return ayatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = ayatList[position]
        holder.bindData(data)
    }


    class ViewHolder(
        @LayoutRes private val layoutId: Int,
        parent: ViewGroup,
        private val removeListConfirmSnackbar: RemoveListConfirmSnackbar<AyaWithInfo>,
    ) : RecyclerView.ViewHolder(parent.inflater(layoutId)) {

        fun bindData(aya: AyaWithInfo) {
            if (layoutId == itemWithHeader)
                itemView.bindItemWithHeader(aya)
            else itemView.bindItem(aya)
        }

        @SuppressLint("SetTextI18n")
        private fun View.bindItem(ayaWithInfo: AyaWithInfo) {
            val aya = ayaWithInfo.aya
            surah_name_bookmark.text =
                if (isRightToLeft) ayaWithInfo.surah!!.name  else ayaWithInfo.surah!!.englishName
            val page =
                context.getString(R.string.page) + " ${aya.page.toString().toLocalizedNumber()}"
            val juz = context.getString(R.string.juz) + " ${aya.juz.toString().toLocalizedNumber()}"
            val numberInSurah = context.getString(R.string.aya) + " ${
                aya.numberInSurah.toString().toLocalizedNumber()
            }"
            val editionName =
                if (ayaWithInfo.edition.type != Edition.TYPE_QURAN) ayaWithInfo.edition.name else ""
            info_bookmark.text = "$page, $juz, $numberInSurah, $editionName"

            item_bookmark_root_view.onClick {
                openBookmarkedAya(ayaWithInfo.edition, aya.surah_number, aya.numberInSurah)
            }
            remove_bookmark.onClick {
                removeListConfirmSnackbar.removeBookmarked(ayaWithInfo)
            }
        }

        private fun openBookmarkedAya(edition: Edition, surahNumber: Int, ayaNumberInSurah: Int) {
            itemView.findNavController().navigate(
                LibraryPagerFragmentDirections.actionLibraryPagerFragmentToReadLibraryFragment(
                    edition.toJson(),
                    surahNumber,
                    ayaNumberInSurah-1//ayaNumberInSurah begin at 1 but scrolling start at 0.
                )
            )
        }

        private fun View.bindItemWithHeader(ayaWithInfo: AyaWithInfo) {
            type_header.text = ayaWithInfo.edition.getLocalizedType(Locale.getDefault())
            bindItem(ayaWithInfo)
        }
    }

    companion object {
        private val itemWithHeader = R.layout.item_bookmark_with_header
        private val itemBookmark = R.layout.item_bookmark
    }
}