package com.abedfattal.quranx.ui.library.ui.manage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abedfattal.quranx.core.model.DownloadingProcess
import com.abedfattal.quranx.ui.common.ConnectionView
import com.abedfattal.quranx.ui.common.dp
import com.abedfattal.quranx.ui.common.extensions.view.onScroll
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.models.EditionDownloadState
import com.abedfattal.quranx.ui.library.ui.simpleLoading.LoadingViewModel
import com.abedfattal.quranx.ui.library.ui.simpleLoading.SimpleLoadingDialog
import com.abedfattal.quranx.ui.library.utils.EditionShortcut
import kotlinx.android.synthetic.main.fragment_manage_library.*
import kotlinx.android.synthetic.main.fragment_manage_library.view.*
import kotlinx.android.synthetic.main.toolbar_manage_library.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


@Suppress("UNCHECKED_CAST")
class ManageLibraryFragment : Fragment() {


    private val parentActivity: AppCompatActivity by lazy { requireActivity() as AppCompatActivity }
    private val manageLibraryViewModel: ManageLibraryViewModel by viewModel()
    private val loadingViewModel: LoadingViewModel by sharedViewModel()
    private lateinit var editionAdpater: ManageLibraryAdapter
    private lateinit var connectionView: ConnectionView

    private val connectionViewChanges = object : ConnectionView.OnViewStateChanges {
        override fun loadViewData() {
            manageLibraryViewModel.getEditions().observe(viewLifecycleOwner) { data ->
                if (data.isNotEmpty()) {
                    editionAdpater.updateDataList(data)
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

        initFragmentUI()
        connectionView.loadViewData()
    }

    private fun initFragmentUI() {
        initAppbar()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        editionAdpater = ManageLibraryAdapter(mutableListOf(), ::onEditionClick)
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
        if (item.itemId == android.R.id.home)
            findNavController().navigateUp()
        return super.onOptionsItemSelected(item)
    }


    private fun onEditionClick(data: EditionDownloadState) {
        loadingViewModel.changeShowLoadingState(false)


        if (!data.isDownloaded) {
            lifecycleScope.launch {

                val numberOfDownloaded = withContext(Dispatchers.IO) {
                    editionAdpater.getData().filter { it.isDownloaded }.size
                }


                loadingViewModel.enableCancelling()

                manageLibraryViewModel.downloadMushaf(data.edition)
                    .observe(viewLifecycleOwner) { process ->
                        loadingViewModel.setLoadingText(R.string.downloading)

                        when (process) {
                            is DownloadingProcess.Failed, is DownloadingProcess.Success -> loadingViewModel.changeShowLoadingState(
                                true
                            )
                            is DownloadingProcess.Saving -> {
                                loadingViewModel.disableCancelling()
                                loadingViewModel.setLoadingText(R.string.saving_data)
                            }
                        }
                    }
            }
        } else {
            loadingViewModel.setLoadingText(R.string.deleting)
            manageLibraryViewModel.deleteMushaf(data.edition).observe(viewLifecycleOwner) {
                EditionShortcut(data.edition, requireContext())
                    .disableShortcut()
                loadingViewModel.disableCancelling()

                loadingViewModel.changeShowLoadingState(true)
            }
        }
        loadingViewModel.onDialogCancelled.observe(viewLifecycleOwner) {
            if (it != null) manageLibraryViewModel.cancelWork()
        }
        SimpleLoadingDialog.show(childFragmentManager)


    }


}

