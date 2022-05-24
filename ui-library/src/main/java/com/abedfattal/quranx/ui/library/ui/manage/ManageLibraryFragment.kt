package com.abedfattal.quranx.ui.library.ui.manage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.common.ConnectionView
import com.abedfattal.quranx.ui.common.dp
import com.abedfattal.quranx.ui.common.extensions.view.onScroll
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.framework.download.EditionDownloadJob
import com.abedfattal.quranx.ui.library.framework.download.EditionDownloadManager
import com.abedfattal.quranx.ui.library.models.EditionDownloadState
import kotlinx.android.synthetic.main.fragment_manage_library.*
import kotlinx.android.synthetic.main.fragment_manage_library.view.*
import kotlinx.android.synthetic.main.toolbar_manage_library.*


@Suppress("UNCHECKED_CAST")
abstract class ManageLibraryFragment : Fragment() {

    private lateinit var editionDownloadManager: EditionDownloadManager
    private val parentActivity: AppCompatActivity by lazy { requireActivity() as AppCompatActivity }
    private val manageLibraryViewModel: ManageLibraryViewModel by lazy {
        ViewModelProvider(this).get(ManageLibraryViewModel::class.java)
    }

    protected abstract val startAtEdition: Edition?


    private lateinit var editionAdpater: ManageLibraryAdapter
    private lateinit var connectionView: ConnectionView

    private val connectionViewChanges = object : ConnectionView.OnViewStateChanges {
        override fun loadViewData() {
            manageLibraryViewModel.getEditions().observe(viewLifecycleOwner) { data ->
                if (data.isNotEmpty()) {
                    editionAdpater.updateDataList(data)
                    startAtEdition?.let { edition ->

                        val index = data.indexOfFirst { it.first.edition.identifier == edition.identifier }
                        if (index != -1)
                            (recycler_library_manage.layoutManager as LinearLayoutManager).scrollToPosition(
                                index
                            )

                    }

                    connectionView.loadingCompleted(false)
                } else
                    connectionView.loadingCompleted(true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_library, container, false)
        connectionView = ConnectionView(view.root, connectionViewChanges)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editionDownloadManager = EditionDownloadManager(requireContext(), lifecycle)
        initFragmentUI()
        connectionView.loadViewData()
    }

    private fun initFragmentUI() {
        initAppbar()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        editionAdpater = ManageLibraryAdapter(
            mutableListOf(),
            editionDownloadManager,
            manageLibraryViewModel,
            ::onEditionClick
        )
        recycler_library_manage.adapter = editionAdpater
        val layoutManager = recycler_library_manage.layoutManager as LinearLayoutManager
        recycler_library_manage.onScroll { dx, dy ->
            val scrollPosition = layoutManager.findFirstCompletelyVisibleItemPosition()

            actionbarManageLibrary?.elevation =
                if (scrollPosition > 1) dp(8).toFloat() else dp(0).toFloat()
        }
    }

    private fun initAppbar() {
        setHasOptionsMenu(true)
        parentActivity.setSupportActionBar(toolbar_manage_library)
        parentActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        parentActivity.supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            try {
                findNavController().navigateUp()
            } catch (e: IllegalStateException) {
                requireActivity().onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun onEditionClick(data: Pair<EditionDownloadState, EditionDownloadJob?>) {
        editionDownloadManager.downloadEdition(data.first.edition)
    }


    companion object {

/*        fun getInstance(startAtEdition: String? = null): ManageLibraryFragment {
            return ManageLibraryFragment().apply {
                arguments = bundleOf("editionStart" to startAtEdition)
            }

        }*/

    }

}

