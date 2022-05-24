package com.abedfattal.quranx.ui.library.ui.books

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.common.createPopup
import com.abedfattal.quranx.ui.common.extensions.view.inflater
import com.abedfattal.quranx.ui.common.extensions.view.onClicks
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.utils.EditionShortcut
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_library_book.*
import java.util.*


class LibraryBooksAdapter(
    private val dataList: List<Edition>,
    private val onItemClick: (Edition) -> Unit
) :
    RecyclerView.Adapter<LibraryBooksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflater(R.layout.item_library_book)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        //val mContext = holder.itemView.context
        holder.bindData(data)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

        override val containerView: View = itemView

        @SuppressLint("SetTextI18n")
        fun bindData(edition: Edition) {

            type_library.text = edition.getLocalizedType(Locale.getDefault())
            edition_name_library.text = edition.getLocalizedName(Locale.getDefault())

            onClicks(itemView, read_translation_button, read_library_more_item) { view ->
                if (view.id != R.id.read_library_more_item) onItemBookClicked(edition)
                else
                    view.createPopup(
                        R.menu.popup_create_shortcut,
                        onItemClickListener = {
                            EditionShortcut(edition, view.context).create()
                        },
                    )
            }
        }

        private fun onItemBookClicked(edition: Edition) {
            EditionShortcut(edition, itemView.context).addToDynamicShortcut()
            onItemClick(edition)
        }
    }
}