package com.abedfattal.quranx.ui.library.ui.manage

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.LocaleListCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.ui.common.createPopup
import com.abedfattal.quranx.ui.common.extensions.colorOf
import com.abedfattal.quranx.ui.common.extensions.colorAccent
import com.abedfattal.quranx.ui.common.extensions.drawableOf

import com.abedfattal.quranx.ui.common.extensions.view.inflater
import com.abedfattal.quranx.ui.common.extensions.view.onClick
import com.abedfattal.quranx.ui.common.getLocalizedLanguage
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.models.EditionDownloadState
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_library_manage.*
import kotlinx.android.synthetic.main.item_library_manage_with_header.view.*


class ManageLibraryAdapter(
    private val dataList: MutableList<EditionDownloadState>,
    private val onItemClick: (EditionDownloadState) -> Unit,
) : RecyclerView.Adapter<ManageLibraryAdapter.ViewHolder>() {

    fun getData(): List<EditionDownloadState> = dataList

    fun updateDataList(newList: List<EditionDownloadState>) {
        val diffCallback = DownloadStateDiffCallback(dataList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        dataList.clear()
        dataList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent, viewType)

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        val currentData = dataList[position]
        val prvData = if (position != 0) dataList[position - 1] else dataList[position]
        val prvLanguage = prvData.edition.language

        return when {
            position == 0 -> itemLibraryWithLanguage
            currentData.edition.language != prvLanguage -> itemLibraryWithLanguage
            else -> itemLibrary
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bindData(data = data)
    }

    inner class ViewHolder(view: ViewGroup, @LayoutRes private val viewType: Int) :
        RecyclerView.ViewHolder(view.inflater(viewType)), LayoutContainer {

        override val containerView: View = itemView

        fun bindData(data: EditionDownloadState) {
            if (viewType == itemLibraryWithLanguage) bindLibraryLanguageItem(data)
            else bindLibraryItem(data)
        }

        private fun bindLibraryLanguageItem(data: EditionDownloadState) {
            itemView.language.text = getLocalizedLanguage(data.edition.language)
            bindLibraryItem(data)
        }

        @SuppressLint("SetTextI18n")
        private fun bindLibraryItem(data: EditionDownloadState) {
            edition_name.text = data.edition.name
            edition_info.text = data.edition.getLocalizedType(LocaleListCompat.getDefault().get(0))
            initDownloadImage(data)
        }

        private fun initDownloadImage(data: EditionDownloadState) {
            val downloadImage: Drawable
            val downloadColor: Int
            if (data.isDownloaded) {
                downloadImage = drawableOf(R.drawable.ic_check_circle)!!
                downloadColor = colorOf(R.color.colorDone)
            } else {
                downloadImage = drawableOf(R.drawable.ic_download)!!
                downloadColor = itemView.context.colorAccent
            }
            DrawableCompat.setTint(downloadImage, downloadColor)
            download_img.setImageDrawable(downloadImage)
            itemLibrary_rootView.onClick {
                //Show conformation popup before delete.
                if (data.isDownloaded) showPopup(data)
                else onItemClick(data)
            }
        }

        private fun View.showPopup(data: EditionDownloadState) {
            createPopup(R.menu.popup_delete_download) {
              onItemClick(data)
            }
        }

    }

    companion object {
        private val itemLibraryWithLanguage = R.layout.item_library_manage_with_header
        private val itemLibrary = R.layout.item_library_manage
    }

}