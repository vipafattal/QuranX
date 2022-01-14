package com.abedfattal.quranx.ui.common

import android.app.Activity
import com.abedfattal.quranx.ui.common.extensions.colorOf
import com.abedfattal.quranx.ui.common.snackbar.material
import com.abedfattal.quranx.ui.common.snackbar.onDismissed
import com.abedfattal.quranx.ui.common.snackbar.snackbar
import com.abedfattal.quranx.ui.common.snackbar.withAction

class RemoveListConfirmSnackbar<T>(
    private val activity: Activity,
    private val dataList: MutableList<T>,
    private val removeListener: ItemRemoveListener<T>?
) {

    private val toRemove = mutableListOf<T>()
    private var removedDataIndex = -1
    private var removedData: T? = null

    interface ItemRemoveListener<T> {
        fun onRemoved(index: Int)
        fun onRestore(index: Int)
        fun onRemovedPermanently(data: List<T>)
    }

    fun removeBookmarked(data: T) {
        removedData = data
        addToDelete(data)
        removedDataIndex = dataList.indexOf(data)
        dataList.remove(data)
        removeListener?.onRemoved(removedDataIndex)
        activeDeleteAyaAction()
    }

    private fun activeDeleteAyaAction() {
        activity.snackbar(R.string.remove_bookmark)
            .material(true)
            .setAnchorView((activity as ActivityInterface).getBottomNavigationViewId())
            .withAction(
                R.string.undo,
                actionTextColor = colorOf(R.color.colorAccent)
            ) { restoreDeletedAya() }
            .onDismissed { removePermanently() }
    }

    private fun addToDelete(aya: T) {
        toRemove.add(aya)
    }

    private fun restoreDeletedAya() {
        toRemove.remove(removedData)
        dataList.add(removedDataIndex, removedData!!)
        removeListener?.onRestore(removedDataIndex)

    }

    fun removePermanently() {
        if (toRemove.isNotEmpty()) {
            removeListener?.onRemovedPermanently(toRemove.toList())
            toRemove.clear()
        }

    }
}