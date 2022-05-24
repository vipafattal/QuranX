package com.abedfattal.quranx.ui.library.ui.bookmarks

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.utils.isArabic
import com.abedfattal.quranx.ui.common.R
import com.abedfattal.quranx.ui.common.RemoveListConfirmSnackbar
import com.abedfattal.quranx.ui.common.extensions.view.inflater
import com.abedfattal.quranx.ui.common.extensions.view.onClick
import com.abedfattal.quranx.ui.common.removePunctuation
import com.abedfattal.quranx.ui.common.toLocalizedNumber
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import kotlinx.android.synthetic.main.item_bookmark.view.*
import kotlinx.android.synthetic.main.item_bookmark_with_header.view.*
import java.util.*

/**
 * Created by ${User} on ${Date}
 */
class BookmarksAdapter(
    private val ayatList: MutableList<AyaWithInfo>,
    private val removeListConfirmSnackbar: RemoveListConfirmSnackbar<AyaWithInfo>,
    private val onBookmarkClick: (AyaWithInfo) -> Unit
) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {


    private val isArabicNumber = LibraryPreferences.isArabicNumbers()
    private  val locale = Locale.getDefault()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(viewType, parent)

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


    inner class ViewHolder(
        @LayoutRes private val layoutId: Int,
        parent: ViewGroup,
    ) : RecyclerView.ViewHolder(parent.inflater(layoutId)) {

        fun bindData(aya: AyaWithInfo) {
            if (layoutId == itemWithHeader)
                itemView.bindItemWithHeader(aya)
            else itemView.bindItem(aya)
        }

        @SuppressLint("SetTextI18n")
        private fun View.bindItem(ayaWithInfo: AyaWithInfo) {
            val aya = ayaWithInfo.aya
            surah_name_bookmark.text =  if (locale.isArabic) ayaWithInfo.surah.name.removePunctuation() else ayaWithInfo.surah.englishName
            info_bookmark.text =
                "${context.getString(R.string.page)} ${aya.page.toLocalizedNumber(isArabicNumber)}, " + //Page
                        "${context.getString(R.string.juz)} ${aya.juz.toLocalizedNumber(isArabicNumber)}, " + //Juz
                        "${context.getString(R.string.aya)} ${aya.numberInSurah.toLocalizedNumber(isArabicNumber)}, " + //Page
                        // Edition
                        if (ayaWithInfo.edition.type == Edition.TYPE_QURAN) ""
                        else {
                            ayaWithInfo.edition.getLocalizedName(locale)
                        }

            item_bookmark_root_view.onClick {
                openBookmarkedAya(ayaWithInfo)
            }
            remove_bookmark.onClick {
                removeListConfirmSnackbar.removeBookmarked(ayaWithInfo)
            }
        }


        private fun openBookmarkedAya(ayaWithInfo: AyaWithInfo) {
            onBookmarkClick(ayaWithInfo)
        }

        private fun View.bindItemWithHeader(ayaWithInfo: AyaWithInfo) {
            type_header.text = ayaWithInfo.edition.getLocalizedType(Locale.getDefault())
            bindItem(ayaWithInfo)
        }
    }

    companion object {
        private val itemWithHeader =
            com.abedfattal.quranx.ui.library.R.layout.item_bookmark_with_header
        private val itemBookmark = com.abedfattal.quranx.ui.library.R.layout.item_bookmark
    }
}