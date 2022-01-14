package com.abedfattal.quranx.ui.library.ui.bookmarks

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.ui.common.RemoveListConfirmSnackbar
import com.abedfattal.quranx.ui.common.extensions.view.setup
import com.abedfattal.quranx.ui.common.extensions.view.visible
import com.abedfattal.quranx.ui.library.R
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


open class BookmarkFragment : Fragment() {

    private val bookmarksViewModel: BookmarksViewModel by viewModel()

    @FunctionalInterface
    interface BookmarkRefreshListener {
        suspend fun onRefresh()
    }

    var bookmarkFragmentRefreshListener: BookmarkRefreshListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        lifecycleScope.launch(Dispatchers.Main) {
            bookmarks_refresher.isRefreshing = true
            withContext(Dispatchers.IO) { bookmarkFragmentRefreshListener?.onRefresh() }
            bookmarks_refresher.isRefreshing = false
        }
    }
    private lateinit var bookmarksAdapter: BookmarksAdapter
    private lateinit var removeListConfirmSnackbar: RemoveListConfirmSnackbar<AyaWithInfo>


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        swipeToRefreshInit()
        bookmarksViewModel.listenToAllBookmarks().observe(viewLifecycleOwner) { data ->
            if (data.isEmpty())
                empty_data_text.visible()

            val ayatList = data.toMutableList()
            removeListConfirmSnackbar =
                RemoveListConfirmSnackbar(
                    context as Activity,
                    ayatList,
                    snackbarItemRemovedListener
                )
            bookmarksAdapter = BookmarksAdapter(ayatList, removeListConfirmSnackbar)
            recycler_bookmarks.adapter = bookmarksAdapter

        }
    }

    private fun swipeToRefreshInit() {
        lifecycleScope.launchWhenResumed {
            bookmarks_refresher.setup()
            bookmarks_refresher.setOnRefreshListener(onRefreshListener)
        }
    }

    override fun onPause() {
        super.onPause()
        if (::removeListConfirmSnackbar.isInitialized)
            removeListConfirmSnackbar.removePermanently()
    }

    private val snackbarItemRemovedListener =
        object : RemoveListConfirmSnackbar.ItemRemoveListener<AyaWithInfo> {
            override fun onRemoved(index: Int) {
                bookmarksAdapter.notifyItemRemoved(index)
                bookmarksAdapter.notifyItemRangeChanged(index, bookmarksAdapter.itemCount)
            }

            override fun onRemovedPermanently(data: List<AyaWithInfo>) {
                bookmarksViewModel.removeBookmarks(data.mapNotNull { it.bookmark })
            }

            override fun onRestore(index: Int) {
                bookmarksAdapter.notifyItemInserted(index)
                bookmarksAdapter.notifyItemRangeChanged(index, bookmarksAdapter.itemCount)
            }
        }

}