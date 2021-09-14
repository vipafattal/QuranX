package com.abedfattal.quranx.sample.tajweedrules.rulesdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.sample.R
import com.abedfattal.quranx.sample.tajweedrules.TajweedRulesActivity
import com.abedfattal.quranx.tajweedpasrser.model.WordsWithRules
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TajweedRulesBottomSheet : BottomSheetDialogFragment() {

    private val aya: Aya by lazy {
        Aya.fromJson(
            requireArguments().getString(AYA_ARG)!!
        )
    }

    private val recyclerView: RecyclerView by lazy { requireView().findViewById(R.id.tajweed_rules_recyclerview) }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val ruleTajweedRules = getRules(aya.text)
        recyclerView.adapter = TajweedRulesListAdapter(aya, ruleTajweedRules)
    }

    private fun getRules(ayaText: String): List<WordsWithRules> {
        return TajweedRulesActivity.tajweedRules.getRulesOfAya(ayaText)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottomsheet_tajweed_rule, container, false)

    companion object {
        const val AYA_ARG = "AYA_ARG"

        fun showDialog(fragmentManager: FragmentManager, aya: Aya) {
            TajweedRulesBottomSheet().apply {
                arguments = bundleOf(AYA_ARG to aya.toJson())
                show(fragmentManager, this::class.simpleName)
            }
        }
    }
}