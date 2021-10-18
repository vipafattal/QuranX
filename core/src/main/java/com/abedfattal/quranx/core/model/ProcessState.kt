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
sealed class ProcessState<T> {

    /**
     * [Pending] mean's the process hasn't started yet.
     */
    class Pending<T> : ProcessState<T>()

    /**
     * [Loading] mean's the process loading data. e.g. waiting for remote API response.
     */
    class Loading<T> : ProcessState<T>()

    /**
     * When the process [Success] it should hold [data]. e.g. remote API response data.
     */
    data class Success<T>(val data: T?) : ProcessState<T>()

    /**
     * When the process [Failed] for a [reason] you can use [friendlyMsg] to show the user a msg why it's failed.
     */
    data class Failed<T>(val reason: String?, @StringRes val friendlyMsg: Int) : ProcessState<T>()

    /**
     * Used by library to transform the current process [T] type to another.
     * @see processTransform
     */
    fun <T> transformProcessType(): ProcessState<T> {
        return when (this) {
            is Loading -> Loading()
            is Pending -> Pending()
            is Success -> Success(null)
            is Failed -> Failed(reason, friendlyMsg)
        }
    }

    fun toDownloadProcess(): DownloadingProcess<T> {
         return when (this) {
            is Loading -> DownloadingProcess.Loading()
            is Pending -> DownloadingProcess.Pending()
            is Success -> DownloadingProcess.Success(data)
            is Failed -> DownloadingProcess.Failed(reason, friendlyMsg)
        }
    }
}
