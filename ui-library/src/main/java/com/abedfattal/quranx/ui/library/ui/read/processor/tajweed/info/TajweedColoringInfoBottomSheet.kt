package com.abedfattal.quranx.ui.library.ui.read.processor.tajweed.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abedfattal.quranx.tajweedrules.TajweedRules
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.read.ReadLibraryFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_tajweed_info_rule.*


class TajweedColoringInfoBottomSheet : BottomSheetDialogFragment() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tajweedRulesList = TajweedRules(ReadLibraryFragment.getDefaultTajweed()).getAllRules()
        tajweedColorsRecycler.adapter = TajweedColoringInfoAdapter(tajweedRulesList.toList())
        close_image.setOnClickListener { dismiss() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottomsheet_tajweed_info_rule, container, false)

}