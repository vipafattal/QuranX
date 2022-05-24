package com.abedfattal.quranx.ui.library.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.common.BaseDialog
import com.abedfattal.quranx.ui.common.extensions.view.invisible
import com.abedfattal.quranx.ui.common.extensions.view.visible
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.models.EditionDownloadState
import com.google.android.material.radiobutton.MaterialRadioButton
import kotlinx.android.synthetic.main.dialog_tanslation_quran_edition.*


class TranslationQuranEditionDialog : BaseDialog() {

    private val translationQuranViewModel: TranslationQuranViewModel by lazy {
        ViewModelProvider(this).get(TranslationQuranViewModel::class.java)
    }
    override val layoutId: Int = R.layout.dialog_tanslation_quran_edition

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editionsLoadingProgress.visible()
        quranEditionGroup.invisible()
        bindEditionsDataToView()

        //TODO convert Radio button to simple recyclerview item picker like language.
    }

    private fun bindEditionsDataToView() {

        val optionsList = listOf(editionOne, editionTwo, editionThree)
        translationQuranViewModel.getDownloadedQuranEdition()
            .observe(viewLifecycleOwner) { quranEditions ->
                for (index in quranEditions.indices) {

                    val editionDownloadState = quranEditions[index]
                    val materialRadioButton = optionsList[index]

                    materialRadioButton.text = editionDownloadState.edition.name
                    if (!editionDownloadState.isDownloaded) {
                        materialRadioButton.isChecked = false
                    } else
                        materialRadioButton.isChecked =
                            isUserQuranEdition(editionDownloadState.edition)
                }

                activateOptionListener(quranEditions)

                editionsLoadingProgress.invisible()
                quranEditionGroup.visible()
            }

    }


    private fun isUserQuranEdition(quranEdition: Edition): Boolean {
        val userEdition = LibraryPreferences.getTranslationQuranEdition()
        return userEdition != null && userEdition.identifier == quranEdition.identifier
    }

    private fun activateOptionListener(quranEditions: List<EditionDownloadState>) {

        quranEditionGroup.setOnCheckedChangeListener { _, checkedId ->
            val editionIndex: Int = when (checkedId) {
                R.id.editionOne -> 0
                R.id.editionTwo -> 1
                else -> 2
            }

            if (!quranEditions[editionIndex].isDownloaded) {
                requireView().findViewById<MaterialRadioButton>(checkedId).isChecked = false
                Toast.makeText(requireContext(), R.string.not_downloaded_edtion, Toast.LENGTH_LONG)
                    .show()
            } else {
                LibraryPreferences.setTranslationQuranEdition(quranEditions[editionIndex].edition)
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "Translation-Quran-Edition-Dialog"

        fun show(fm: FragmentManager) {
            val dialog = TranslationQuranEditionDialog()
            dialog.show(fm, TAG)
        }
    }
}