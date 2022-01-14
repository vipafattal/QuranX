package com.abedfattal.quranx.ui.library.ui.read

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.Surah
import com.abedfattal.quranx.core.utils.isArabic
import com.abedfattal.quranx.tajweedprocessor.Tajweed
import com.abedfattal.quranx.ui.common.extensions.colorAccent
import com.abedfattal.quranx.ui.common.extensions.isDarkThemeOn
import com.abedfattal.quranx.ui.common.extensions.view.animateElevation
import com.abedfattal.quranx.ui.common.extensions.view.onScroll
import com.abedfattal.quranx.ui.common.preferences.AppPreferences
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ui.bookmarks.BookmarksViewModel
import com.abedfattal.quranx.ui.library.ui.read.adapter.ReadLibraryAdapter
import com.abedfattal.quranx.ui.library.ui.read.menus.ReadingMenu
import com.abedfattal.quranx.ui.library.ui.read.menus.TajweedMenu
import com.abedfattal.quranx.ui.library.ui.read.menus.WordByWordMenu
import com.abedfattal.quranx.ui.library.ui.read.processor.tajweed.TajweedColorsHelper
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import com.abedfattal.quranx.ui.library.utils.EN_WORD_BY_WORD
import com.abedfattal.quranx.ui.library.utils.QURAN_TAJWEED_ID
import kotlinx.android.synthetic.main.fragment_read_library.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ReadLibraryFragment : Fragment() {

    private val readLibraryViewModel: ReadLibraryViewModel by viewModel()
    private val bookmarksViewModel: BookmarksViewModel by viewModel()
    private val preferences: AppPreferences by lazy { AppPreferences("appTemp",requireContext())}
    private var scrollAyahPosition = 0
    private var previewingSurahIndex = 0
    private lateinit var surahsDrawerAdapter: SurahsDrawerAdapter
    private var surahLists: List<Surah> = listOf()

    private lateinit var readLibraryAdapter: ReadLibraryAdapter
    private val bookEdition by lazy {
        Edition.fromJson(
            ReadLibraryFragmentArgs.fromBundle(requireArguments()).edition
        )
    }
    private val tajweedColorsHelper by lazy {
        TajweedColorsHelper(requireContext().isDarkThemeOn())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_read_library, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initFragment()

        readLibraryViewModel.getSurahs(bookEdition.id).observe(viewLifecycleOwner) {
            surahLists = it
            updateSurahsDrawerAdapter(previewingSurahIndex)
        }
    }

    private fun initFragment() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).initSystemWindows()

        surahsDrawerAdapter = SurahsDrawerAdapter(::onSurahDrawerClicked)
        recycler_surahs_list.adapter = surahsDrawerAdapter

        initRecyclerScroll()
        openLastReadSurah()
        onActivityBackPressed()
    }

    private fun AppCompatActivity.initSystemWindows() {
        if (Build.VERSION.SDK_INT >= 23) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        } else
            toolbar_library_surah.setBackgroundColor(requireContext().colorAccent)

        toolbar_library_surah.navigationIcon =
            AppCompatResources.getDrawable(this, R.drawable.ic_menu)

        setSupportActionBar(toolbar_library_surah)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    private fun updateCurrentSurahInfo(surah: Surah) {
        if (Locale.getDefault().isArabic) {
            toolbar_library_surah.subtitle = surah.englishNameTranslation
            toolbar_library_surah.title = surah.englishName
        } else
            toolbar_library_surah.title = surah.name
    }

    private fun initRecyclerScroll() {
        val layoutManger = recycler_read_library.layoutManager as LinearLayoutManager
        recycler_read_library.onScroll { _, dy ->
            scrollAyahPosition = layoutManger.findFirstVisibleItemPosition()
            if (dy > 0)
                app_bar_library_surah.animateElevation(false)
            else
                app_bar_library_surah.animateElevation(true)

            if (dy > 0 && layoutManger.findFirstVisibleItemPosition() >= 2)
                hideToolbar()
            else if (dy < 0)
                showToolbar()
        }
    }

    private fun openLastReadSurah() {
        val bundleArgument = ReadLibraryFragmentArgs.fromBundle(requireArguments())
        val startAtAyaNumberInSurah = bundleArgument.startAtAyaNumberInSurah
        val startAtSurah = bundleArgument.startAtSurah

        scrollAyahPosition = if (startAtAyaNumberInSurah != -1) startAtAyaNumberInSurah
        else preferences.getInt(LastScrollPosition + bookEdition, 0)

        val lastSurahReadNumber = if (startAtSurah != -1) startAtSurah
        else preferences.getInt(LAST_SURAH_NUMBER_ARG + bookEdition, 1)

        openSurah(lastSurahReadNumber, scrollAyahPosition)
    }

    private fun openSurah(surahNumber: Int, scrollTo: Int) {
        if (previewingSurahIndex == 0) previewingSurahIndex = surahNumber
        updateSurahsDrawerAdapter(surahNumber)

        bookmarksViewModel

        readLibraryViewModel.getEditionAyat(bookEdition.id, surahNumber)
            .observe(viewLifecycleOwner) { surahWithAyat ->

                updateCurrentSurahInfo(surahWithAyat.surah!!)

                if (::readLibraryAdapter.isInitialized)
                    readLibraryAdapter.updateBookmarkedAyat(surahWithAyat)
                else {
                    readLibraryAdapter =
                        ReadLibraryAdapter(
                            bookmarksViewModel,
                            requireContext(),
                            surahWithAyat,
                            bookEdition
                        )
                    readLibraryAdapter.changeTajweedColors(tajweedColorsHelper.tajweedColors)
                    recycler_read_library.adapter = readLibraryAdapter
                    recycler_read_library.scrollToPosition(scrollTo)
                }
            }
    }

    private fun updateSurahsDrawerAdapter(newSurahIndex: Int) {
        previewingSurahIndex = newSurahIndex
        surahsDrawerAdapter.updateAdapter(surahLists, previewingSurahIndex)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val readingMenu: ReadingMenu? =
            when {
                mainEditionIsEqualTo(QURAN_TAJWEED_ID) -> TajweedMenu(
                    menu,
                    inflater,
                    tajweedColorsHelper,
                    childFragmentManager
                ) {
                    readLibraryAdapter.changeTajweedColors(tajweedColorsHelper.tajweedColors)
                }
                mainEditionIsEqualTo(EN_WORD_BY_WORD) -> WordByWordMenu(
                    menu,
                    inflater
                ) {
                    readLibraryAdapter.notifyDataSetChanged()
                }
                else -> null
            }

        readingMenu?.buildMenu()
    }

    private fun onSurahDrawerClicked(surah: Surah) {
        saveCurrentReadScrollPosition(0)

        readLibraryDrawer.closeDrawer(GravityCompat.START)
        preferences.put(
            LAST_SURAH_NUMBER_ARG + bookEdition,
            surah.number
        )
        showToolbar()
        openSurah(surah.number, 0)
    }


    private fun onActivityBackPressed() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (readLibraryDrawer.isDrawerOpen(GravityCompat.START)) readLibraryDrawer.closeDrawer(
                        GravityCompat.START
                    )
                    else requireView().findNavController().navigateUp()
                }
            })
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home)
            readLibraryDrawer.openDrawer(GravityCompat.START)
        return true
    }

    override fun onPause() {
        super.onPause()
        //if user changed configuration like rotation we save scroll position.
        saveCurrentReadScrollPosition(scrollAyahPosition)
    }

    private fun saveCurrentReadScrollPosition(pos: Int) {
        preferences.put(LastScrollPosition + bookEdition, pos)
        scrollAyahPosition = pos
    }

    private fun hideToolbar() {
        app_bar_library_surah
            .animate()
            .setDuration(250)
            .translationY(-app_bar_library_surah.height.toFloat())
            .start()
    }

    private fun showToolbar() {
        app_bar_library_surah
            .animate()
            .setDuration(400)
            .translationY(0f)
            .start()
    }

    private fun mainEditionIsEqualTo(editionId: String): Boolean {
        return (
                (bookEdition.type == Edition.TYPE_QURAN && bookEdition.id == editionId) ||
                        ((bookEdition.type == Edition.TYPE_TAFSEER || bookEdition.type == Edition.TYPE_TRANSLATION)
                                && LibraryPreferences.getTranslationQuranEdition()?.id == editionId))
    }

    companion object {

        fun getDefaultTajweed() = Tajweed()
        private const val LastScrollPosition = "Last-Scroll-Position"
        const val LAST_SURAH_NUMBER_ARG = "Last-Viewed-Surah"
    }
}