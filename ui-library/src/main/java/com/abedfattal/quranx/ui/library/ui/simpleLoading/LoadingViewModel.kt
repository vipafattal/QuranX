package com.abedfattal.quranx.ui.library.ui.simpleLoading

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoadingViewModel : ViewModel() {

    private val _onDialogCancelled: MutableLiveData<Unit?> = MutableLiveData(null)
    val onDialogCancelled: LiveData<Unit?> get() = _onDialogCancelled
    private val _disableCancelButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val disableCancelButton: LiveData<Boolean> get() = _disableCancelButton

    private val _loadingText = MutableLiveData<Int>()
    val loadingText: LiveData<Int> get() = _loadingText

    private val hideLoadingDialog: MutableLiveData<Boolean> = MutableLiveData()

    fun setLoadingText(@StringRes text: Int) {
        _loadingText.value = text
    }

    fun loadingState(): LiveData<Boolean> {
        return hideLoadingDialog
    }

    fun changeShowLoadingState(hide: Boolean) {
        hideLoadingDialog.postValue(hide)
    }


    fun disableCancelling() {
        _disableCancelButton.postValue(true)
    }

    fun enableCancelling() {
        _disableCancelButton.postValue(false)
    }

    fun cancelDialog() {
        _onDialogCancelled.postValue(Unit)
    }

    fun newWork() { _onDialogCancelled.postValue(null) }

}