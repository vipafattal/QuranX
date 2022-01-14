package com.abedfattal.quranx.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.abedfattal.quranx.ui.common.extensions.view.invisible
import com.abedfattal.quranx.ui.common.extensions.view.visible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    protected abstract val layoutId: Int
    protected open val closeView: View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)


    @JvmField
    val dialogOffsetLimit: Float = 0.5f

    final override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { Dialog ->
            val d = Dialog as BottomSheetDialog

            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?

            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.addBottomSheetCallback(dialogSlidingListener)
        }
        return dialog
    }

    open fun onDialogScrolled(slideOffset: Float) {}

    private val dialogSlidingListener = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            onDialogScrolled(slideOffset)
            if (slideOffset > 0.5)
                closeView?.visible()
            else
                closeView?.invisible()
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN)
                dismiss()
        }
    }


}
