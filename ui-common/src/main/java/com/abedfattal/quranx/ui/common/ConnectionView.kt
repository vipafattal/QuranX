package com.abedfattal.quranx.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abedfattal.quranx.ui.common.extensions.view.gone
import com.abedfattal.quranx.ui.common.extensions.view.visible

class ConnectionView(rootView: ViewGroup, private val viewStateChanges: ConnectionView.OnViewStateChanges) {
    private val inflater = LayoutInflater.from(rootView.context)

    private var layoutView: View = View.inflate(rootView.context,R.layout.layout_connection,rootView)
    private var connectionErrorView = layoutView.findViewById<View>(R.id.connectionErrorView)
    private var loadingView = layoutView.findViewById<View>(R.id.loadingView)

    interface OnViewStateChanges{
        fun loadViewData(){}
        fun onLoadingCompleted(withError: Boolean){}

    }

    fun loadViewData() {
        showLoading()
        connectionErrorView.gone()
        viewStateChanges.loadViewData()
    }

    fun loadingCompleted(withError: Boolean = false) {
        hideLoading()
        if (withError)
            connectionErrorView.visible()
        viewStateChanges.onLoadingCompleted(withError)
    }

    fun showErrorView() {
        hideLoading()
        connectionErrorView.visible()
    }
    private fun showLoading() {
        loadingView.visible()
    }

    private fun hideLoading() = loadingView.gone()






}