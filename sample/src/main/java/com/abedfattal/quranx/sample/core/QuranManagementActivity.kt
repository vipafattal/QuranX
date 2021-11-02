package com.abedfattal.quranx.sample.core

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.DownloadingProcess
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.sample.R
import com.abedfattal.quranx.sample.core.viewmodels.EditionViewModel
import com.abedfattal.quranx.sample.core.viewmodels.LanguageViewModel
import com.abedfattal.quranx.sample.core.viewmodels.QuranManagementViewModel
import com.abedfattal.quranx.sample.utils.observer
import com.abedfattal.quranx.sample.utils.viewModelOf
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class QuranManagementActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    //Initializing view models.
    private val languageViewModel: LanguageViewModel by lazy { viewModelOf() }
    private val editionViewModel: EditionViewModel by lazy { viewModelOf() }
    private val quranViewModel: QuranManagementViewModel by lazy { viewModelOf() }

    //Initializing views.
    private val downloadedEditionsText: TextView by lazy { findViewById(R.id.downloaded_editions) }
    private val stateText: TextView by lazy { findViewById(R.id.state_text) }
    private val downloadButton: MaterialButton by lazy { findViewById(R.id.download_edition_button) }
    private val languagesPicker: Spinner by lazy { findViewById(R.id.languages_picker) }
    private val editionTypePicker: Spinner by lazy { findViewById(R.id.edition_type_picker) }
    private val editionPicker: Spinner by lazy { findViewById(R.id.edition_picker) }

    //Picker selected values.
    private var selectedLanguage: String? = null
    private var selectedEditionType: String? = null
    private var selectedEdition: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quran_management)

        loadLanguagesPicker()
        loadEditionsTypePicker()
        initDownloadedEditionsView()

        downloadButton.setOnClickListener {
            quranViewModel.getQuranBook(selectedEdition!!).observer(this) { downloadingProcess ->
                stateText.text = when (downloadingProcess) {
                    is DownloadingProcess.Loading -> getString(R.string.download_loading)
                    is DownloadingProcess.Failed -> {
                        Log.d(getString(R.string.download_fail), downloadingProcess.reason!!)
                        getString(R.string.download_fail) + getString(downloadingProcess.friendlyMsg)
                    }
                    is DownloadingProcess.Saving -> getString(R.string.download_saving)
                    is DownloadingProcess.Success -> getString(R.string.download_success)


                    else -> throw IllegalStateException()
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun loadLanguagesPicker() {
        languageViewModel.getSupportedLanguages().observer(this) { languageProcess ->
            when (languageProcess) {
                is ProcessState.Loading -> stateText.text = getString(R.string.lang_loading)
                is ProcessState.Failed -> {
                    Log.d(getString(R.string.lang_fail), languageProcess.reason!!)
                    stateText.text =
                        getString(R.string.lang_fail) + getString(languageProcess.friendlyMsg)
                }
                is ProcessState.Success -> {

                    stateText.text = getString(R.string.lang_success)

                    languagesPicker.adapter =
                        createPickerAdapter(languageProcess.data!!.map { it.code })
                }
                else -> throw IllegalStateException()
            }
        }

        languagesPicker.onItemSelectedListener = this
    }

    private fun loadEditionsTypePicker() {
        editionTypePicker.adapter = createPickerAdapter(Edition.getAllEditionsTypes())
        editionTypePicker.onItemSelectedListener = this
    }

    @SuppressLint("SetTextI18n")
    private fun loadEditionsPicker() {
        require(selectedEditionType != null && selectedLanguage != null)
        editionViewModel.getEditionsByType(selectedLanguage!!, selectedEditionType!!)
            .observer(this) { editionProcess ->
                when (editionProcess) {
                    is ProcessState.Loading -> stateText.text = getString(R.string.edition_fail)
                    is ProcessState.Failed -> {
                        Log.d(getString(R.string.edition_fail), editionProcess.reason!!)
                        stateText.text =
                            getString(R.string.edition_fail) + getString(editionProcess.friendlyMsg)
                    }
                    is ProcessState.Success -> {
                        stateText.text = getString(R.string.edition_success)
                        editionPicker.adapter =
                            createPickerAdapter(editionProcess.data!!.map { it.id })
                    }
                    else -> throw IllegalStateException()
                }
            }

        editionPicker.onItemSelectedListener = this
    }


    private fun initDownloadedEditionsView() {
        editionViewModel.listAllDownloadedEditions().observer(this) { editions ->
            downloadedEditionsText.text = editions.joinToString { it.name }
        }
    }

    /**
     * When picker (spinner) value is selected will call [onItemSelected], and only if [selectedLanguage] and [selectedEditionType] are not null,
     * then load editions picker data ([loadEditionsPicker]).
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val selectedData = parent.adapter.getItem(position) as String
        when (parent.id) {
            R.id.languages_picker -> {
                clearEditionSelection()
                selectedLanguage = selectedData
                if (selectedEditionType != null) loadEditionsPicker()
            }
            R.id.edition_type_picker -> {
                clearEditionSelection()
                selectedEditionType = selectedData
                if (selectedLanguage != null) loadEditionsPicker()
            }
            R.id.edition_picker -> {
                //Enable download button when edition id is selected.
                selectedEdition = selectedData
                downloadButton.isEnabled = true
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}

    private fun clearEditionSelection() {
        selectedEdition = null
        downloadButton.isEnabled = false
    }

    private fun createPickerAdapter(data: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            data
        )
    }
}