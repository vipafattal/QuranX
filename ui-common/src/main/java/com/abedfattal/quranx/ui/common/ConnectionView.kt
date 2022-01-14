package com.abedfattal.quranx.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.abedfattal.quranx.ui.common.databinding.LayoutConnectionBinding
import com.abedfattal.quranx.ui.common.extensions.view.gone
import com.abedfattal.quranx.ui.common.extensions.view.visible

class ConnectionView(rootView: ViewGroup, private val viewStateChanges: ConnectionView.OnViewStateChanges) {
    private val inflater = LayoutInflater.from(rootView.context)
    private var binding: LayoutConnectionBinding = LayoutConnectionBinding.inflate(inflater)
    interface OnViewStateChanges{
        fun loadViewData(){}
        fun onLoadingCompleted(withError: Boolean){}

    }
    init {
        rootView.addView(binding.root,ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
    }

    fun loadViewData() {
        showLoading()
        binding.connectionErrorView.gone()
        viewStateChanges.loadViewData()
    }

    fun loadingCompleted(withError: Boolean = false) {
        hideLoading()
        if (withError)
            binding.connectionErrorView.visible()
        viewStateChanges.onLoadingCompleted(withError)
    }

    fun showErrorView() {
        hideLoading()
        binding.connectionErrorView.visible()
    }
    private fun showLoading() {
        binding.loadingView.loadingView.visible()
    }

    private fun hideLoading() = binding.loadingView.loadingView?.gone()






}