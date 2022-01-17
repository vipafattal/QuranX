package com.abedfattal.quranx.ui.library.ui.books


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.ui.common.extensions.view.*
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.read.ReadLibraryViewModel
import kotlinx.android.synthetic.main.fragment_library.*


class LibraryBooksFragment : Fragment() {

    private val readLibraryViewModel: ReadLibraryViewModel by lazy {
        ViewModelProvider(this).get(ReadLibraryViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initEditionsRecyclerView()
        recycler_items_library.addDividerDecoration(RecyclerView.VERTICAL)
        onClicks(add_item_fab) { view ->
            val destinationId =
                if (view.id == R.id.add_item_fab) R.id.action_libraryPagerFragment_to_manageLibraryFragment
                else R.id.action_bookmarkFragment_to_readLibraryFragment
            findNavController().navigate(destinationId)
        }

        val layoutManager = recycler_items_library.layoutManager as LinearLayoutManager
        recycler_items_library.onScroll { _, dy ->
            if (dy > 0) add_item_fab.shrink()
            else if (layoutManager.findFirstVisibleItemPosition() < 1) add_item_fab.extend()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_library,
            container,
            false
        ) //getPersistentView(inflater, container)

    }

    private fun initEditionsRecyclerView() {
        readLibraryViewModel.listDownloadedEdition().observe(viewLifecycleOwner) { quranEditions ->

            if (quranEditions.isNotEmpty()) {
                empty_data_text?.gone()
                recycler_items_library?.adapter = LibraryBooksAdapter(quranEditions)
            } else
                empty_data_text?.visible()
        }
    }


}
