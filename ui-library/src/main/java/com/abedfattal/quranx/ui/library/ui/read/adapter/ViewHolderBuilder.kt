package com.abedfattal.quranx.ui.library.ui.read.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.core.model.*
import com.abedfattal.quranx.tajweedprocessor.Tajweed
import com.abedfattal.quranx.ui.common.Fonts
import com.abedfattal.quranx.ui.common.TextActionUtil.copyToClipboard
import com.abedfattal.quranx.ui.common.TextActionUtil.shareText
import com.abedfattal.quranx.ui.common.createPopup
import com.abedfattal.quranx.ui.common.dp
import com.abedfattal.quranx.ui.common.extensions.drawableOf
import com.abedfattal.quranx.ui.common.extensions.stringer
import com.abedfattal.quranx.ui.common.extensions.view.gone
import com.abedfattal.quranx.ui.common.extensions.view.invisible
import com.abedfattal.quranx.ui.common.extensions.view.visible
import com.abedfattal.quranx.ui.common.toLocalizedNumber
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.bookmarks.BookmarksViewModel
import com.abedfattal.quranx.ui.library.ui.read.processor.tajweed.rules_sheet.TajweedRulesBottomSheet
import com.abedfattal.quranx.ui.library.ui.read.processor.word.WordsAdapter
import com.abedfattal.quranx.ui.library.ui.read.processor.word.touch.LinkTouchMovementMethod
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import com.abedfattal.quranx.ui.library.utils.EN_WORD_BY_WORD
import com.abedfattal.quranx.ui.library.utils.QURAN_TAJWEED_ID
import com.abedfattal.quranx.ui.library.utils.setTextSizeFromType
import com.abedfattal.quranx.wordsprocessor.WordByWordEnglish
import com.brilliancesoft.readlib.ui.read.processor.tajweed.TajweedSpannable
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.item_read_library.view.*
import java.util.*

class ViewHolderBuilder(
    context: Context,
    private val bookmarksViewModel: BookmarksViewModel,
    var tajweed: Tajweed,
    private val ayatWithTafseer: AyatInfoWithTafseer,
    private val edition: Edition,
) {
    private val tafseerLanguage: String =
        ayatWithTafseer.tafseerList?.getOrNull(0)?.edition?.id?.substringBefore('.') ?: "ar"
    private val tafseerFont = Fonts.getTranslationFont(context, tafseerLanguage)
    private val textSizeType = LibraryPreferences.getFontSize()
    private val showArabicNumbers = LibraryPreferences.isArabicNumbers()

    private val bookmarkedImage = drawableOf(R.drawable.ic_bookmark, context)
    private val bookmarkBorderImage = drawableOf(R.drawable.ic_bookmark_border, context)

    inner class Build(
        view: View
    ) : RecyclerView.ViewHolder(view) {


        init {
            val quranicAya = ayatWithTafseer.quranList?.get(0)
            if (quranicAya?.aya?.ayaEdition == QURAN_TAJWEED_ID)
                view.quran_text_readlibrary.movementMethod = LinkTouchMovementMethod()
            else if (quranicAya?.aya?.ayaEdition == EN_WORD_BY_WORD) {
                view.wordbyword_recyclerview.apply {
                    visible()
                    layoutManager = FlexboxLayoutManager(context).apply {
                        flexWrap = FlexWrap.WRAP
                        flexDirection = FlexDirection.ROW_REVERSE
                        alignItems = AlignItems.STRETCH
                    }
                }
            }
            view.apply {
                quran_text_readlibrary.setTextSizeFromType(textSizeType)
                aya_number_library.setTextSizeFromType(textSizeType, 0.70f)
                second_text_readlibrary.setTextSizeFromType(textSizeType)
                second_text_readlibrary.typeface = tafseerFont

                ayaMoreOptions.setOnClickListener {
                    it.createAyaPopup(
                        ayatWithTafseer.quranList?.get(adapterPosition)?.aya,
                        ayatWithTafseer.tafseerList?.get(adapterPosition)?.aya
                    )
                }
            }

        }

        private val context = itemView.context

        fun bindData(quranicAya: AyaWithInfo?, tafseerAya: AyaWithInfo?, surah: Surah) {
            val ayaInfo = (tafseerAya ?: quranicAya)!!
            val aya = ayaInfo.aya
            val bookmark: Bookmark? = ayaInfo.bookmark.let { bMark ->
                if (bMark == null || bMark.isDeleted) null
                else bMark
            }

            itemView.apply {

                bookmarkAyaButton.setOnClickListener {
                    if (bookmark == null)
                        bookmarksViewModel.addBookmark(
                            aya.number,
                            ayaInfo.edition,
                        )
                    else
                        bookmarksViewModel.removeBookmarks(bookmark)
                }

                bookmarkAyaButton.setImageDrawable(if (bookmark != null) bookmarkedImage else bookmarkBorderImage)

                if (quranicAya == null) quran_text_readlibrary.gone()
                else bindQuranicAya(quranicAya.aya)

                if (tafseerAya == null) second_text_readlibrary.gone()
                else second_text_readlibrary.text = tafseerAya.aya.text

                val numberInSurah = (quranicAya ?: tafseerAya!!).aya.numberInSurah.toString()
                if (tafseerLanguage != "ar" && quranicAya == null) aya_number_library.text =
                    numberInSurah
                else aya_number_library.text = numberInSurah.toLocalizedNumber(showArabicNumbers)

                if (adapterPosition < surah.numberOfAyahs) divider_item_library.visible()
                else divider_item_library.invisible()
                if (adapterPosition == 0) itemView.updatePadding(top = dp(65))
                else itemView.updatePadding(top = 0)
            }
        }

        private fun bindQuranicAya(quranicAya: Aya) {
            if (quranicAya.ayaEdition == EN_WORD_BY_WORD) {
                itemView.wordbyword_recyclerview.adapter =
                    WordsAdapter(WordByWordEnglish.getWordOfAyaV2(quranicAya.text))
            } else {
                if (quranicAya.ayaEdition == QURAN_TAJWEED_ID) {
                    val tajweedSpannable = TajweedSpannable(
                        context,
                        quranicAya,
                        tajweed
                    ).colorWithTouchListener { touchWord ->
                        TajweedRulesBottomSheet.showDialog(
                            (context as AppCompatActivity).supportFragmentManager,
                            touchWord
                        )
                    }
                    itemView.quran_text_readlibrary.setText(
                        tajweedSpannable,
                        TextView.BufferType.SPANNABLE
                    )
                } else
                    itemView.quran_text_readlibrary.text = quranicAya.text
            }
        }


        private fun View.createAyaPopup(quranicAya: Aya?, tafseerAya: Aya?) {
            createPopup(R.menu.popup_more_aya_options) { id ->

                val text = convertTranslationToShare(quranicAya, tafseerAya)

                if (id == R.id.copyAyaText) {
                    if (quranicAya?.ayaEdition == QURAN_TAJWEED_ID)
                        copyToClipboard(
                            context as Activity,
                            tajweed.getAyahWithoutStyle(text),
                            R.string.ayah_copied_popup
                        )
                    else
                        copyToClipboard(
                            context as Activity,
                            text,
                            R.string.ayah_copied_popup
                        )
                } else shareText(context, text, R.string.share_ayah_text)
            }
        }

        private fun convertTranslationToShare(
            quranicAya: Aya?,
            translationAaa: Aya?
        ): String {
            var shareText = ""
            val ayaData: Aya = quranicAya ?: translationAaa!!

            val surah = ayatWithTafseer.surah
            val ayaNumber = ayaData.numberInSurah
            val pageNumber = "${context.getString(R.string.page)}: ${ayaData.page}"
            val surahName = surah?.getLocalizedName(Locale.getDefault())

            val ayaInfo = "$surahName:$ayaNumber,\n$pageNumber"

            if (quranicAya != null)
                shareText += "{ ${quranicAya.text} }\n$ayaInfo\n"

            if (translationAaa != null) {
                val translationText = """ "${translationAaa.text}" """ + "\n"
                val translationInfo = "${stringer(R.string.tafseer, context)}: ${edition.name}\n"
                shareText += translationText + translationInfo
            }

            return shareText
        }
    }
}