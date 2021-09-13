package com.abedfattal.quranx.tajweed.parser.lib

import com.abedfattal.quranx.tajweed.parser.Tajweed
import com.abedfattal.quranx.tajweed.parser.tajweedMetas

/**
 * [Splitter] object is used to split ayah text by metas.
 * Used by [Tajweed] class, create different sorts of actions, such as color each split, remove text from each split.
 * To create custom action on each split see [Tajweed.onEachSplit]
 */
@PublishedApi
internal object Splitter {

    inline fun doOnSplits(
        splits: List<String>,
        action: (meta: Char?, ayahSplit: String) -> Unit,
    ) {
        var metaSpilt = ""
        for (ayahSpilt in splits) {
            when {
                tajweedMetas.contains(ayahSpilt) -> metaSpilt = ayahSpilt
                metaSpilt.isNotEmpty() -> {
                    action(metaSpilt[0], ayahSpilt)
                    metaSpilt = ""
                }
                ayahSpilt == "]" -> continue
                //null means no meta here (which means it's regular split).
                else -> action(null, ayahSpilt)
            }
        }
    }


    fun getAyahSplits(rawAyah: String): List<String> {
        val ayah = rawAyah.replace("[\\[0-9:]".toRegex(), "")
        return ayah.splitWithDelimiter("(?<=[\\]\\[$tajweedMetas])|(?=[\\]\\[$tajweedMetas])")
    }

    private fun String.splitWithDelimiter(input: CharSequence) =
        this.split(Regex("(?<=[$input])|(?=[$input])"))

}

