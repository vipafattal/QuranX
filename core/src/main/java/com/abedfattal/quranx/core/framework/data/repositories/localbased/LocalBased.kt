package com.abedfattal.quranx.core.framework.data.repositories.localbased

import android.util.Log
import com.abedfattal.quranx.core.framework.data.repositories.local.LocalDownloadStateRepository
import com.abedfattal.quranx.core.model.Aya
import com.abedfattal.quranx.core.model.DownloadState
import com.abedfattal.quranx.core.model.ProcessState
import com.abedfattal.quranx.core.utils.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

 abstract class LocalBased internal constructor(){


     companion object {
         @PublishedApi
         internal const val REMOTE_CALL = "Remote call on:"

         @PublishedApi
         internal const val LOCAL_CALL = "Local call on:"
     }
    protected inline fun <reified T : Any> caller(
        crossinline local: suspend () -> List<T>,
        crossinline remote: () -> Flow<ProcessState<List<T>>>,
        crossinline onRemoteSuccess: suspend (List<T>) -> Unit
    ): Flow<ProcessState<List<T>>> = flow {
        val localEditions = local.invoke()
        if (localEditions.isNotEmpty())
            emit(ProcessState.Success(localEditions))
        else {
            val remoteEditions = remote.invoke()
            Log.d(REMOTE_CALL,remoteEditions::class.java.simpleName)
            remoteEditions.onSuccess.collect { onRemoteSuccess(it!!) }
            emitAll(remoteEditions)
        }
    }

     protected inline fun <reified T : Any> callerItem(
         crossinline local: suspend () -> T?,
         crossinline remote: () -> Flow<ProcessState<T>>,
         crossinline onRemoteSuccess: suspend (T) -> Unit
    ): Flow<ProcessState<T>> = flow {
        val localEditions = local.invoke()
        if (localEditions != null)
            emit(ProcessState.Success(localEditions))
        else {
            val remoteEditions: Flow<ProcessState<T>> = remote.invoke()
            Log.d(REMOTE_CALL,remoteEditions::class.qualifiedName!!)
            remoteEditions.onSuccess.collect { onRemoteSuccess(it!!) }
            emitAll(remoteEditions)
        }
    }
}