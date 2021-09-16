package com.abedfattal.quranx.tajweedparser.lib

import com.abedfattal.quranx.tajweedparser.Tajweed
import com.abedfattal.quranx.tajweedparser.model.WordWithItsMeta
import com.abedfattal.quranx.tajweedparser.tajweedMetas

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

/*    inline fun doOnSplitsWithWords(
        words: List<String>,
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
    }*/


    fun getAyahSplits(rawAyah: String): List<String> {
        val ayah = rawAyah.replace("[\\[0-9:]".toRegex(), "")
        return ayah.splitWithDelimiter("(?<=[\\]\\[$tajweedMetas])|(?=[\\]\\[$tajweedMetas])")
    }

    fun getAyahWordSplits(rawAyah: String): List<WordWithItsMeta> {
        return rawAyah.split(' ').map { WordWithItsMeta(word = it, rawMetaSplits = getAyahSplits(it)) }
    }

    private fun String.splitWithDelimiter(input: CharSequence) =
        this.split(Regex("(?<=[$input])|(?=[$input])"))

}

