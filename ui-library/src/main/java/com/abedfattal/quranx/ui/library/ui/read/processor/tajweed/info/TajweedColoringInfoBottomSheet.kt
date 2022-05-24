package com.abedfattal.quranx.ui.library.ui.read.processor.tajweed.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.abedfattal.quranx.tajweedrules.TajweedRules
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.read.ReadLibraryFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_tajweed_info_rule.*


class TajweedColoringInfoBottomSheet : BottomSheetDialogFragment() {


    private val noteText: String? by lazy { arguments?.getString(TAJWEED_NOTE_TEXT_ARG) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tajweedRulesList = TajweedRules(ReadLibraryFragment.getDefaultTajweed()).getAllRules()
        tajweedColorsRecycler.adapter = TajweedColoringInfoAdapter(tajweedRulesList.toList())
        noteText?.run { rulesInfoNotes.text = this }
        close_image.setOnClickListener { dismiss() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottomsheet_tajweed_info_rule, container, false)


    companion object {

        private val TAJWEED_NOTE_TEXT_ARG = "tajweed-note-arg"

        fun show(fm: FragmentManager, noteText: String? = null) {

            TajweedColoringInfoBottomSheet().apply {
                arguments = bundleOf(TAJWEED_NOTE_TEXT_ARG to noteText)
            }.show(fm, this::class.simpleName)
        }
    }
}