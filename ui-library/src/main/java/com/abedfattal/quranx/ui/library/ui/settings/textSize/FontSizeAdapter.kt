package com.abedfattal.quranx.ui.library.ui.settings.textSize

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.ui.common.Fonts
import com.abedfattal.quranx.ui.common.extensions.view.inflater
import com.abedfattal.quranx.ui.common.extensions.view.onClicks
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.utils.setTextSizeFromType
import kotlinx.android.synthetic.main.item_font_size.view.*
import java.util.*

class FontSizeAdapter(
    private val sizeNamesRes: Array<Int>,
    private val selectedTextType: Int,
    private val onItemClickRecycler: (Int) -> Unit
) : RecyclerView.Adapter<FontSizeAdapter.ViewHolder>() {

    private val appLanguage = Locale.getDefault().language

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflater(R.layout.item_font_size))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sizeNamesRes[position])
    }

    override fun getItemCount(): Int = sizeNamesRes.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(sizeName: Int) {
            itemView.apply {
                textSizeName.text = context.getString(sizeName)

                selectedTextSizeRadio.isChecked = sizeName == selectedTextType
                quranTextViewSize.text = context.getString(R.string.preview_quran)
                quranTextViewSize.setTextSizeFromType(sizeName)
                translationTextViewSize.setTextSizeFromType(sizeName)

                translationTextViewSize.typeface = Fonts.getNormalFont(context,appLanguage)

                onClicks(itemView, textSizeRow, selectedTextSizeRadio) {
                    itemView.selectedTextSizeRadio.isChecked = true
                    onItemClickRecycler(sizeName)
                }
            }
        }
    }
}