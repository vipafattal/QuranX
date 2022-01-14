package com.abedfattal.quranx.ui.library.ui.settings

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.abedfattal.quranx.ui.common.extensions.goTo
import com.abedfattal.quranx.ui.common.onPreferencesClick
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.settings.textSize.TextSizeBottomSheet
import com.abedfattal.quranx.ui.library.ui.settings.textSize.TextSizeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    private val textSizeViewModel by sharedViewModel<TextSizeViewModel>()

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        newValue as Boolean
        when (preference.key) {
            getString(R.string.arabic_numbers) -> LibraryPreferences.setIsArabicNumbers(newValue)
            getString(R.string.translation_with_aya) -> {
                LibraryPreferences.setDisplayAyaWithTafseer(
                    newValue
                )

                quranEditionPreference.isVisible = newValue

            }
        }
        return true
    }

    private lateinit var quranEditionPreference: Preference
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.library_setting, rootKey)

        quranEditionPreference = findPreference(getString(R.string.set_quran_edition))!!

        val translationWithAya =
            findPreference<SwitchPreferenceCompat>(getString(R.string.translation_with_aya))!!
        val arabicNumbers = findPreference<CheckBoxPreference>(getString(R.string.arabic_numbers))!!

        val textSize = findPreference<Preference>(getString(R.string.font_size_key))!!

        translationWithAya.isChecked = LibraryPreferences.isDisplayAyaWithTafseer()
        arabicNumbers.isChecked = LibraryPreferences.isArabicNumbers()

        textSize.summary = getString(LibraryPreferences.getFontSize())
        translationWithAya.onPreferenceChangeListener = this
        arabicNumbers.onPreferenceChangeListener = this


        quranEditionPreference.isVisible = translationWithAya.isChecked

        onPreferencesClick(R.string.font_size_key) {
            TextSizeBottomSheet.show(parentFragmentManager)
            textSizeViewModel.selectedTextSize.observe(this) { textSize.summary = getString(it) }
        }

        onPreferencesClick(R.string.data_disclaimer) {
            context?.goTo("https://alquran.cloud/")
        }

        onPreferencesClick(R.string.set_quran_edition) {
            TranslationQuranEditionDialog.show(childFragmentManager)
        }
    }
}