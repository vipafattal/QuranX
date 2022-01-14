package com.abedfattal.quranx.ui.library.ui.read.processor.tajweed.rules_sheet

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.toSpannable
import androidx.fragment.app.FragmentManager
import com.abed.kotlin_recycler.withSimpleAdapter
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.tajweedprocessor.Tajweed
import com.abedfattal.quranx.tajweedprocessor.model.MetaColors
import com.abedfattal.quranx.tajweedrules.TajweedRules
import com.abedfattal.quranx.tajweedrules.model.PartRule
import com.abedfattal.quranx.tajweedrules.model.WordsWithRules
import com.abedfattal.quranx.ui.common.BaseBottomSheet
import com.abedfattal.quranx.ui.common.extensions.animateText
import com.abedfattal.quranx.ui.common.extensions.colorOf
import com.abedfattal.quranx.ui.common.extensions.isDarkThemeOn
import com.abedfattal.quranx.ui.common.extensions.view.onClick
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.models.TouchedWord
import com.abedfattal.quranx.ui.library.ui.read.processor.word.touch.LinkTouchMovementMethod
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import com.abedfattal.quranx.ui.library.utils.ScrollTextUtils
import com.abedfattal.quranx.ui.library.utils.setTextSizeFromType
import com.brilliancesoft.readlib.ui.read.processor.tajweed.TajweedSpannable
import kotlinx.android.synthetic.main.bottomsheet_tajweed_word_rule.*
import kotlinx.android.synthetic.main.item_tajweed_word_rules.*


class TajweedRulesBottomSheet : BaseBottomSheet() {

    override val closeView: View get() = close_image
    override val layoutId: Int = R.layout.bottomsheet_tajweed_word_rule
    private val aya: Aya by lazy {
        Aya.fromJson(
            requireArguments().getString(AYA_ARG)!!
        )
    }
    private var selectedWord: String = ""
    private var selectedWordIndex: Int = -1

    private val tajweed by lazy {
        Tajweed(MetaColors(defaultColor = if (requireContext().isDarkThemeOn()) "#FFFFFF" else "#000000"))
    }
    private val tajweedRules by lazy { TajweedRules(tajweed) }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        selectedWordIndex = requireArguments().getInt(AYA_WORD_INDEX_ARG)
        selectedWord = requireArguments().getString(AYA_WORD_ARG)!!
        tajweed_aya_text.movementMethod = LinkTouchMovementMethod()
        tajweed_aya_text.setTextSizeFromType(LibraryPreferences.getFontSize())
        tajweed_aya_text.text =
            TajweedSpannable(requireContext(), aya, tajweed).colorWithTouchListener {
                selectedWord = it.word
                selectedWordIndex = it.index
                updateView()
            }
        ScrollTextUtils.enableScroll(tajweed_aya_text)
        updateView()
        closeView.onClick { dismiss() }
    }

    private fun updateView() {
        highlightWord()
        if (tajweed_word.text.isEmpty())
            tajweed_word.text = tajweed.getStyledWords(selectedWord)
        else
            tajweed_word.animateText(tajweed.getStyledWords(selectedWord))

        val rules = getRules()
        val wordRules: List<PartRule> = if (rules.isEmpty()) emptyList() else rules[0].rules

        tajweedColorsRecycler.withSimpleAdapter(wordRules, R.layout.item_tajweed_word_rules) {
            val tajweedRule: PartRule = wordRules[adapterPosition]
            val ruleColor = Color.parseColor(tajweedRule.ruleType.color)
            tajweed_rule_color.setCardBackgroundColor(ruleColor)
            tajweed_verse_part.setTextColor(ruleColor)
            tajweed_verse_part.text = tajweedRule.part
            tajweed_rule_name.setText(tajweedRule.ruleType.name)
        }

    }

    private fun highlightWord() {
        val text = tajweed_aya_text.text
        val string = tajweed.getAyahWithoutStyle(selectedWord.trim())
        val startPoint = text.indexOf(string)
        val endPoint = startPoint + string.length
        val spannableText = text.toSpannable()

        val spans: Array<BackgroundColorSpan> =
            spannableText.getSpans(0, spannableText.length, BackgroundColorSpan::class.java)
        for (styleSpan in spans) spannableText.removeSpan(styleSpan)

        if (startPoint != -1) {
            spannableText.setSpan(
                BackgroundColorSpan(colorOf(R.color.background_heighlight, requireContext())),
                startPoint,
                endPoint,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        tajweed_aya_text.text = spannableText

    }

    private fun getRules(): List<WordsWithRules> = tajweedRules.getRulesOfAya(selectedWord)

    companion object {
        private const val AYA_ARG = "AYA_ARG"
        private const val AYA_WORD_ARG = "AYA_TEXT_ARG"
        private const val AYA_WORD_INDEX_ARG = "AYA_TEXT_INDEX_ARG"

        fun showDialog(
            fragmentManager: FragmentManager,
            touchedWord: TouchedWord
        ) {
            TajweedRulesBottomSheet().apply {
                arguments = bundleOf(
                    AYA_ARG to touchedWord.aya.toJson(),
                    AYA_WORD_ARG to touchedWord.word,
                    AYA_WORD_INDEX_ARG to touchedWord.index
                )
                show(fragmentManager, this::class.simpleName)
            }
        }
    }
}