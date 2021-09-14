package com.abedfattal.quranx.core.utils

import com.abedfattal.quranx.core.model.ProcessState
import kotlinx.coroutines.flow.*

/**
 * Sometimes we can't control the data type of the [ProcessState], e.g. when have fixed types in remote API response.
 *
 * @param block is only class the process is [ProcessState.Success], since it's the only one that have data.
 */
inline fun <reified Data, T : ProcessState<Data>, R> Flow<T>.processTransform(
    crossinline block: suspend (Data) -> R
): Flow<ProcessState<R>> {
    return map {
        if (it is ProcessState.Success<*>)
            ProcessState.Success(block(it.data as Data))
        else
            it.changeProcessType<R>()
    }
}

/**
 * Create new flow that only if [ProcessState.Success] will emit values.
 *
 */
inline val <reified T> Flow<ProcessState<T>>.onSuccess: Flow<T?>
    get() = flow<T?> {
        this@onSuccess.collect {
            if (it is ProcessState.Success<*>)
                emit(it.data as? T)
        }
    }
