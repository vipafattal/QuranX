package com.abedfattal.quranx.ui.library.ui.simpleLoading

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.abedfattal.quranx.ui.common.BaseDialog
import com.abedfattal.quranx.ui.common.extensions.animateText
import com.abedfattal.quranx.ui.common.extensions.textButtonDisabled
import com.abedfattal.quranx.ui.common.extensions.textButtonEnabled
import com.abedfattal.quranx.ui.common.extensions.view.onClick
import com.abedfattal.quranx.ui.library.R
import kotlinx.android.synthetic.main.dialog_loading_simple.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SimpleLoadingDialog : BaseDialog() {

    override val isCancelableDialog: Boolean = false
    private val loadingViewModel: LoadingViewModel by sharedViewModel()

    override val layoutId: Int = R.layout.dialog_loading_simple

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadingViewModel.newWork()

        loadingViewModel.loadingText.observe(viewLifecycleOwner) {
            simple_loading_text.animateText(it)
        }
        loadingViewModel.loadingState().observe(viewLifecycleOwner) { hide ->
            if (hide) dismiss()
        }
        loadingViewModel.disableCancelButton.observe(viewLifecycleOwner) { disable ->
            if (disable) cancel_button.textButtonDisabled()
            else cancel_button.textButtonEnabled()
        }

        cancel_button.onClick {
            loadingViewModel.cancelDialog()
            dismiss()
        }

    }

    companion object {
        @JvmField
        val TAG = SimpleLoadingDialog::class.java.simpleName

        fun show(fragment: FragmentManager) {
            SimpleLoadingDialog().show(
                fragment,
                TAG
            )
        }

    }
}