package com.abedfattal.quranx.ui.library.ui.settings.textSize

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.abedfattal.quranx.ui.common.BaseBottomSheet
import com.abedfattal.quranx.ui.common.extensions.view.gone
import com.abedfattal.quranx.ui.common.extensions.view.onClick
import com.abedfattal.quranx.ui.common.extensions.view.visible
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import kotlinx.android.synthetic.main.dialog_font_size.*

class TextSizeBottomSheet : BaseBottomSheet() {

    override val closeView: View
        get() = close_image

    companion object {
        private val TAG = this::class.java.simpleName

        fun show(fragmentManager: FragmentManager) {
            TextSizeBottomSheet().apply {
                show(fragmentManager, TAG)
            }
        }
    }

    override val layoutId: Int = R.layout.dialog_font_size
    private val textSizeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TextSizeViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val selectedTextSize = LibraryPreferences.getFontSize()
        val fontSize = arrayOf(
            R.string.small_font_size,
            R.string.medium_font_size,
            R.string.large_font_size,
            R.string.x_large_font_size
        )
        fontSizeRecycler.adapter = FontSizeAdapter(
            fontSize,
            selectedTextSize,
            ::onFontSizeClicked
        )

        close_image.onClick { dismiss() }
    }

    private fun onFontSizeClicked(data: Int) {
        LibraryPreferences.saveFontSize(data)
        textSizeViewModel.selectedTextSize.postValue(data)
        dismiss()
    }

    override fun onDialogScrolled(slideOffset: Float) {
        if (dialogOffsetLimit < slideOffset) close_image?.visible()
        else close_image?.gone()
    }
}