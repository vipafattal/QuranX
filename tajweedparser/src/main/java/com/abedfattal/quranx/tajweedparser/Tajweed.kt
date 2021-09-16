package com.abedfattal.quranx.tajweedparser

import android.text.Spannable
import android.text.SpannableStringBuilder
import com.abedfattal.quranx.tajweedparser.lib.Splitter
import com.abedfattal.quranx.tajweedparser.lib.setSplitSpan
import com.abedfattal.quranx.tajweedparser.model.MetaColors
import com.abedfattal.quranx.tajweedparser.model.WordWithItsMeta

/**
 * Use [Tajweed] to get colored text by converting [String] with special meta to [Spannable] text.
 * It only supports the text edition of `quran-tajweed`.
 *
 * @property metaColors used to apply colors on tajweed rules (reciting rules).
 */
class Tajweed(val metaColors: MetaColors = MetaColors()) {
    //TODO add meta filter.

    /**
     *
     * Use [onEachSplit] to execute custom action on each ayah split.
     *
     * @param rawAyah represents aya text in the default format of `quran-tajweed` edition.
     * For styled text see [Tajweed.getStyledWords].
     *
     * Executes your custom [action], on each split.
     * @param [action.meta] when value is null, means it has no tajweed meta in this
     * @param [action.ayahSplit].
     *
     */
    inline fun onEachSplit(rawAyah: String, action: (meta: Char?, ayahSplit: String) -> Unit) {
        Splitter.doOnSplits(splits = Splitter.getAyahSplits(rawAyah), action)
    }

    /**
     * Use [applyActionsSplits] to execute custom action on your raw [splits], see [WordWithItsMeta.rawMetaSplits].
     *
     *
     * Executes your custom [action], on each split.
     * @param [action.meta] when value is null, means it has no tajweed meta in this
     * @param [action.ayahSplit].
     */
    inline fun applyActionsSplits(
        splits: List<String>,
        action: (meta: Char?, ayahSplit: String) -> Unit
    ) {
        Splitter.doOnSplits(splits = splits, action)
    }

    /**
     * Use [onEachWord] to execute custom action on word.
     *
     * @param rawAyah represents aya text in the default format of `quran-tajweed` edition.
     * For styled text see [Tajweed.getStyledWords].
     *
     * Executes your custom [action], on word.
     * @param [action.meta] when value is null, means it has no tajweed meta in this
     * @param [action.ayahSplit].
     */
    inline fun onEachWord(rawAyah: String, action: (WordWithItsMeta) -> Unit) {
        Splitter.getAyahWordSplits(rawAyah).onEach {
            action(it)
        }
    }

    /**
     * Applies color to each meta in ayah split in [rawAyah].
     *
     * @sample com.abedfattal.quranx.sample.tajweedparser.TajweedParserActivity
     * @sample com.abedfattal.quranx.sample.tajweedparser.TajweedAdapter
     *
     */
    fun getStyledWords(rawAyah: String): Spannable {
        val ayahSplits = Splitter.getAyahSplits(rawAyah)
        return applyTajweedColors(ayahSplits)
    }

    /**
     * @return Split verses into words and apply color to each on each word.
     */
    fun getStyledAyahWords(rawAyah: String): List<Spannable> {
        val wordsList = rawAyah.split(' ')
        return wordsList.map { getStyledWords(it) }
    }

    /**
     * Removes metas from [rawAyah] and returns the text.
     */
    fun getAyahWithoutStyle(rawAyah: String): String {
        return rawAyah.replace("[]\\[$tajweedMetas:0-9]".toRegex(), "")
    }

    /**
     * Converting [splits] to a [android.text.SpannableStringBuilder],
     * so we could apply color for each meta.
     */
    private fun applyTajweedColors(splits: List<String>): Spannable {
        val spannableAya = SpannableStringBuilder("")
        Splitter.doOnSplits(splits) { meta, ayahSpilt ->
            val metaColor = metaColors.colorOfMeta(meta)
            spannableAya.setSplitSpan(ayahText = ayahSpilt, color = metaColor)
        }
        return spannableAya
    }
}