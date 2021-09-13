package com.abedfattal.quranx.tajweed.parser

import android.text.Spannable
import android.text.SpannableStringBuilder
import com.abedfattal.quranx.tajweed.parser.lib.Splitter
import com.abedfattal.quranx.tajweed.parser.lib.setSplitSpan
import com.abedfattal.quranx.tajweed.parser.model.MetaColors

/**
 * Use [Tajweed] to get colored text by converting [String] with special meta to [Spannable] text.
 * It only supports the text edition of `quran-tajweed`.
 *
 * @property metaColors used to apply colors on tajweed rules (reciting rules).
 */
class Tajweed(val metaColors: MetaColors = MetaColors()) {


    /**
     *
     * Use it to execute custom action on each ayah split.
     *
     * [rawAyah] represents aya text in the default format of `quran-tajweed` edition.
     * For styled text see [Tajweed.getStyledAyah].
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
     * Applies color to each meta in ayah split in [rawAyah].
     */
    fun getStyledAyah(rawAyah: String): Spannable {
        val ayahSplits = Splitter.getAyahSplits(rawAyah)
        return applyTajweedColors(ayahSplits)
    }

    /**
     * Removes metas from [rawAyah] and returns the text.
     */
    fun getAyahWithoutStyle(rawAyah: String): String {
        return rawAyah.replace("[]\\[$tajweedMetas:0-9]".toRegex(), "")
    }

    /**
     * Converting [splits] to a [android.text.SpannableStringBuilder],
     * so we could apply color for each meta
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