package com.abedfattal.quranx.ui.library.ui.read.menus

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.read.processor.tajweed.TajweedColorsHelper
import com.abedfattal.quranx.ui.library.ui.read.processor.tajweed.info.TajweedColoringInfoBottomSheet

class TajweedMenu(
    menu: Menu,
    inflater: MenuInflater,
    private val tajweedColorsHelper: TajweedColorsHelper,
    private val fragmentManager: FragmentManager,
    private val onTajweedColorChange:()->Unit,
) : ReadingMenu(R.menu.menu_tajweed, menu, inflater) {

    private val tajweedOptionCheckListener = MenuItem.OnMenuItemClickListener { item ->
        tajweedColorsHelper.updateTajweedColor(item.itemId)
        item.isChecked = !item.isChecked
        onTajweedColorChange.invoke()
        return@OnMenuItemClickListener true
    }


    override fun buildMenu() {
        initTajweedInfoButton()
        initTajweedRulesOptions()
    }

    private fun initTajweedRulesOptions() {
        arrayOf(
            R.id.mududCheckBox,
            R.id.noonCheckBox,
            R.id.mimmCheckBox,
            R.id.qalaqahCheckBox,
            R.id.idghamCheckBox
        ).onEach { itemId ->
            findItem(itemId).setOnMenuItemClickListener(tajweedOptionCheckListener)
            findItem(itemId).isChecked = tajweedColorsHelper.getCheckBoxState(itemId)
        }
    }


    private fun initTajweedInfoButton() {
        findItem(R.id.tajweedRulesInfo).setOnMenuItemClickListener {
            TajweedColoringInfoBottomSheet().show(fragmentManager, "")
            true
        }
    }

}