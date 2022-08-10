package com.abedfattal.quranx.core.model

import androidx.annotation.StringRes
import com.abedfattal.quranx.core.R
import com.abedfattal.quranx.core.model.DownloadingProcess.*
import com.abedfattal.quranx.core.utils.transform

/**
 * A wrapper class for processes that can [InProgress], [Success], or [Failed].
 * This class used by the library to simplify requesting data code structure (rather then using try-catch pattern)
 * which really improves the code readability. e.g. requesting data from a remote API service may fail, success, or even waiting for response.
 *
 * The type [T] represents the data type of the current process.
 */
sealed class DownloadingProcess<T>(
    @StringRes val stateName: Int,
    val isProcessCompleted: Boolean = false,
) {

    /**
     * [Pending] mean's the process pending data. e.g. waiting in execution queue API response.
     */
    class Pending<T> : DownloadingProcess<T>(R.string.downloading_pending_state)

    /**
     * [InProgress] mean's the process loading data. e.g. waiting for remote API response.
     */
    class InProgress<T> : DownloadingProcess<T>(R.string.downloading_in_progress_state)

    /**
     * [Saving] mean's the process data is being saved in local.
     */
    class Saving<T> : DownloadingProcess<T>(R.string.downloading_saving_state)

    /**
     * When the process [Success] it should hold [data]. e.g. remote API response data.
     */
    data class Success<T>(val data: T? = null) :
        DownloadingProcess<T>(R.string.downloading_success_state, true)

    /**
     * When the process [Failed] for a [reason] you can use [friendlyMsg] to show the user a msg why it's failed.
     */
    data class Failed<T>(val reason: String?, @StringRes val friendlyMsg: Int) :
        DownloadingProcess<T>(R.string.downloading_failed_state, true)

    /**
     * Used by library to transform the current process [T] type to another.
     * @see transform
     */
    fun <T> transformProcessType(): DownloadingProcess<T> {
        return when (this) {
            is Pending -> Pending()
            is InProgress -> InProgress()
            is Saving -> Saving()
            is Success -> Success(null)
            is Failed -> Failed(reason, friendlyMsg)
        }
    }
}
