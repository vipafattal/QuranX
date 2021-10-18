package com.abedfattal.quranx.core.model

import androidx.annotation.StringRes
import com.abedfattal.quranx.core.utils.processTransform

/**
 * A wrapper class for processes that can [Loading], [Success], or [Failed].
 * This class used by the library to simplify requesting data code structure (rather then using try-catch pattern)
 * which really improves the code readability. e.g. requesting data from a remote API service may fail, success, or even waiting for response.
 *
 * The type [T] represents the data type of the current process.
 */
sealed class DownloadingProcess<T>  {

    /**
     * [Pending] mean's the process hasn't started yet.
     */
    class Pending<T> : DownloadingProcess<T>()

    /**
     * [Loading] mean's the process loading data. e.g. waiting for remote API response.
     */
    class Loading<T> : DownloadingProcess<T>()

    /**
     * [Saving] mean's the process data is being saved in local.
     */
    class Saving<T> : DownloadingProcess<T>()

    /**
     * When the process [Success] it should hold [data]. e.g. remote API response data.
     */
    data class Success<T>(val data: T? = null) : DownloadingProcess<T>()

    /**
     * When the process [Failed] for a [reason] you can use [friendlyMsg] to show the user a msg why it's failed.
     */
    data class Failed<T>(val reason: String?, @StringRes val friendlyMsg: Int) : DownloadingProcess<T>()

    /**
     * Used by library to transform the current process [T] type to another.
     * @see processTransform
     */
    fun <T> transformProcessType(): DownloadingProcess<T> {
        return when (this) {
            is Loading -> Loading()
            is Pending -> Pending()
            is Saving -> Saving()
            is Success -> Success(null)
            is Failed -> Failed(reason, friendlyMsg)
        }
    }
}
