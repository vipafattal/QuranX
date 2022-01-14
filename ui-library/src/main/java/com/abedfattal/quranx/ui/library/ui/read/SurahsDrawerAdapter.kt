package com.abedfattal.quranx.ui.library.ui.read

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.ui.common.extensions.view.inflater
import com.abedfattal.quranx.core.model.Surah
import com.abedfattal.quranx.core.utils.isArabic
import com.abedfattal.quranx.ui.common.extensions.view.onClick
import com.abedfattal.quranx.ui.common.extensions.colorOf
import com.abedfattal.quranx.ui.common.extensions.colorAccent
import com.abedfattal.quranx.ui.common.extensions.textColor
import com.abedfattal.quranx.ui.library.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_library_choose_surah.*
import java.util.*

/**
 * Created by ${User} on ${Date}
 */
class SurahsDrawerAdapter(
    private val onClick: (Surah)->Unit
) : RecyclerView.Adapter<SurahsDrawerAdapter.ViewHolder>() {

    private var dataList: List<Surah> = mutableListOf()
    private var previewSurahIndex = 0

    fun updateAdapter(
        newDataList: List<Surah>,
        newSurahIndex: Int
    ) {
        dataList = newDataList

        notifyItemChanged(previewSurahIndex - 1)
        previewSurahIndex = newSurahIndex
        notifyItemChanged(previewSurahIndex - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflater(R.layout.item_library_choose_surah)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bindView(data)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        override val containerView: View = itemView

        fun bindView(data: Surah) {

            surahName.text =
                if (Locale.getDefault().isArabic) data.name else data.englishName
            surahNumber.text = data.getFormattedNumber()
           // pageNumber.text = data.numberOfAyahs.toString()
            itemView.onClick { onClick(data) }

            setIsViewColored(data.number == previewSurahIndex)
        }

        private fun setIsViewColored(colored: Boolean) {
            val context = itemView.context

            if (colored) {
                val colorAccent = context.colorAccent
                library_item_color.setCardBackgroundColor(colorOf(R.color.colorAccentLight,context))
                surahName.setTextColor(colorAccent)
                surahNumber.setTextColor(colorAccent)
                //pageNumber.setTextColor(colorAccent)
            } else {
                val textColor = context.textColor
                library_item_color.setBackgroundColor(Color.TRANSPARENT)
                surahName.setTextColor(textColor)
                surahNumber.setTextColor(textColor)
                //pageNumber.setTextColor(textColor)
            }
        }


    }


}