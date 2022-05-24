package com.abedfattal.quranx.ui.library.ui.manage

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.ui.common.R
import com.abedfattal.quranx.ui.common.createPopup
import com.abedfattal.quranx.ui.common.extensions.colorAccent
import com.abedfattal.quranx.ui.common.extensions.colorOf
import com.abedfattal.quranx.ui.common.extensions.drawableOf
import com.abedfattal.quranx.ui.common.extensions.view.gone
import com.abedfattal.quranx.ui.common.extensions.view.inflater
import com.abedfattal.quranx.ui.common.extensions.view.onClick
import com.abedfattal.quranx.ui.common.extensions.view.visible
import com.abedfattal.quranx.ui.common.getLocalizedLanguage
import com.abedfattal.quranx.ui.library.framework.download.EditionDownloadJob
import com.abedfattal.quranx.ui.library.framework.download.EditionDownloadManager
import com.abedfattal.quranx.ui.library.models.EditionDownloadState
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_library_manage.*
import kotlinx.android.synthetic.main.item_library_manage_with_header.view.*
import java.util.*


class ManageLibraryAdapter(
    private val dataList: MutableList<Pair<EditionDownloadState, EditionDownloadJob?>>,
    private val editionDownloadManager: EditionDownloadManager,
    private val manageLibraryViewModel: ManageLibraryViewModel,
    private val onItemClick: (Pair<EditionDownloadState, EditionDownloadJob?>) -> Unit,
) : RecyclerView.Adapter<ManageLibraryAdapter.ViewHolder>() {

    fun updateDataList(newList: List<Pair<EditionDownloadState, EditionDownloadJob?>>) {
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
        val prvEdition = prvData.first.edition
        val prvLanguage = prvEdition.language

        return when {
            position == 0 -> itemLibraryWithLanguage
            currentData.first.edition.language != prvLanguage -> itemLibraryWithLanguage
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
        private val context = itemView.context


        fun bindData(data: Pair<EditionDownloadState, EditionDownloadJob?>) {
            if (viewType == itemLibraryWithLanguage) bindLibraryLanguageItem(data)
            else bindLibraryItem(data)
        }

        private fun bindLibraryLanguageItem(data: Pair<EditionDownloadState, EditionDownloadJob?>) {
            itemView.language.text = getLocalizedLanguage(data.first.edition.language)
            bindLibraryItem(data)
        }

        @SuppressLint("SetTextI18n")
        private fun bindLibraryItem(data: Pair<EditionDownloadState, EditionDownloadJob?>) {
            val edition = data.first.edition
            edition_name.text = edition.getLocalizedName(Locale.getDefault())
            applyDownloadState(data)
            applyDownloadImage(data)
        }

        @SuppressLint("SetTextI18n")
        private fun applyDownloadState(data: Pair<EditionDownloadState, EditionDownloadJob?>) {
            val downloadState = data.second?.downloadState
            val edition = data.first.edition
            if (downloadState != null) {
                if (!downloadState.isProcessCompleted) book_download_loading.visible()
                edition_info.visible()
                val stateName = context.getString(downloadState.stateName)
                edition_info.text =
                    edition.getLocalizedType(Locale.getDefault()) + " . " + stateName
            } else {
                book_download_loading.gone()
                edition_info.text = edition.getLocalizedType(Locale.getDefault())
            }
        }

        private fun applyDownloadImage(data: Pair<EditionDownloadState, EditionDownloadJob?>) {
            val downloadImage: Drawable
            val downloadColor: Int
            when {
                data.first.isDownloaded -> {
                    downloadImage = drawableOf(R.drawable.ic_check_circle)!!
                    downloadColor = colorOf(R.color.colorDone)
                }
                data.second != null -> {
                    downloadImage = drawableOf(R.drawable.ic_stop)!!
                    downloadColor = colorOf(R.color.textColor, context)

                }
                else -> {
                    downloadImage = drawableOf(R.drawable.ic_download)!!
                    downloadColor = itemView.context.colorAccent
                }
            }

            DrawableCompat.setTint(downloadImage, downloadColor)
            download_img.setImageDrawable(downloadImage)
            download_img.onClick {
                //Show conformation popup before delete.
                when {
                    data.first.isDownloaded -> showPopup(data)
                    data.second != null -> editionDownloadManager.cancelDownloading(data.first.edition)
                    else -> onItemClick(data)
                }
            }
        }

        private fun View.showPopup(data: Pair<EditionDownloadState, EditionDownloadJob?>) {
            createPopup(
                com.abedfattal.quranx.ui.library.R.menu.popup_delete_download,
                onItemClickListener = {
                    manageLibraryViewModel.deleteMushaf(data.first.edition)
                },
            )

        }
    }

    companion object {
        private val itemLibraryWithLanguage =
            com.abedfattal.quranx.ui.library.R.layout.item_library_manage_with_header
        private val itemLibrary = com.abedfattal.quranx.ui.library.R.layout.item_library_manage
    }

}