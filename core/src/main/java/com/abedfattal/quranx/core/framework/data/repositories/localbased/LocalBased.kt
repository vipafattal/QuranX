package com.abedfattal.quranx.core.framework.data.repositories.localbased

import androidx.annotation.Keep
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.utils.toSuccessProcess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

@Keep
abstract class LocalBased internal constructor() {


    protected inline fun <T : Any> caller(
        prioritizeRemote: Boolean,
        crossinline local: suspend () -> T?,
        crossinline remote: () -> Flow<ProcessState<T>>,
        crossinline onRemoteSuccess: suspend (T) -> Unit
    ): Flow<ProcessState<T>> = flow {

        val localData = if (prioritizeRemote) null else getFromLocalOrNull(local)

        if (localData != null) emit(localData)
        else emitFromRemoteCall(remote, local, onRemoteSuccess)
    }

    @PublishedApi
    internal suspend inline fun <T : Any> getFromLocalOrNull(
        crossinline local: suspend () -> T?,
        exception: Exception? = null
    ): ProcessState.Success<T>? {
        val localData: T? = local.invoke()
        return if ((localData is List<*> && localData.isEmpty()) || (localData == null)) null
        else localData.toSuccessProcess(exception)
    }

    @PublishedApi
    internal suspend inline fun <T : Any> FlowCollector<ProcessState<T>>.emitFromRemoteCall(
        crossinline remote: () -> Flow<ProcessState<T>>,
        crossinline local: suspend () -> T?,
        crossinline onRemoteSuccess: suspend (T) -> Unit,
    ) {
        emitAll(
            remoteCall(
                call = remote,
                backupSourceOnFail = local,
                onSuccess = { onRemoteSuccess(it) },
            )
        )
    }

    @PublishedApi
    internal suspend inline fun <T : Any> remoteCall(
        crossinline call: () -> Flow<ProcessState<T>>,
        crossinline onSuccess: suspend (T) -> Unit,
        crossinline backupSourceOnFail: suspend () -> T?

    ): Flow<ProcessState<T>> = flow {

        call.invoke().collect { process ->
            if (process is ProcessState.Failed)
                emit(getFromLocalOrNull(backupSourceOnFail) ?: process)
            else {
                emit(process)
                if (process is ProcessState.Success) onSuccess(process.data!!)
            }

        }
    }


}