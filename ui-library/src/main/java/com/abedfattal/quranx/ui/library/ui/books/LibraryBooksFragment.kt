package com.abedfattal.quranx.ui.library.ui.books


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.common.extensions.view.addDividerDecoration
import com.abedfattal.quranx.ui.common.extensions.view.gone
import com.abedfattal.quranx.ui.common.extensions.view.onScroll
import com.abedfattal.quranx.ui.common.extensions.view.visible
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.read.ReadLibraryViewModel
import kotlinx.android.synthetic.main.fragment_library.*


abstract class LibraryBooksFragment : Fragment() {

    private val readLibraryViewModel: ReadLibraryViewModel by lazy {
        ViewModelProvider(this).get(ReadLibraryViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //checkForBookBookShortcut()
        initEditionsRecyclerView()
        recycler_items_library.addDividerDecoration(RecyclerView.VERTICAL)

        add_item_fab.setOnClickListener {
            navigateToManageLibrary()
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
                recycler_items_library?.adapter = LibraryBooksAdapter(quranEditions, ::onBooksItemClicked)
            } else
                empty_data_text?.visible()
        }
    }

    protected abstract fun onBooksItemClicked(edition: Edition)

    abstract fun navigateToManageLibrary()


/*    private fun checkForBookBookShortcut() {
        //if coming from shortcut.
        arguments?.getString(LIBRARY_BOOK_EDITION_ARG)?.let { jsonEdition ->
            findNavController().navigate(
                LibraryPagerFragmentDirections.actionLibraryPagerFragmentToSettingsFragment(

                )
            )
            //When resuming fragment doesn't launch back this block.
            requireArguments().putString(LIBRARY_BOOK_EDITION_ARG, null)
        }
    }*/

}
