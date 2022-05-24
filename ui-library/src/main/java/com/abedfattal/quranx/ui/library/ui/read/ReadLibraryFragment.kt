package com.abedfattal.quranx.ui.library.ui.read

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.core.model.Surah
import com.abedfattal.quranx.core.utils.isNotArabic
import com.abedfattal.quranx.tajweedprocessor.Tajweed
import com.abedfattal.quranx.ui.common.extensions.colorAccent
import com.abedfattal.quranx.ui.common.extensions.isDarkThemeOn
import com.abedfattal.quranx.ui.common.removePunctuation
import com.abedfattal.quranx.ui.library.R
import com.abedfattal.quranx.ui.library.ReadLibrary.tempPreferences
import com.abedfattal.quranx.ui.library.ui.bookmarks.BookmarksViewModel
import com.abedfattal.quranx.ui.library.ui.read.adapter.ReadLibraryAdapter
import com.abedfattal.quranx.ui.library.ui.read.menus.ReadingMenu
import com.abedfattal.quranx.ui.library.ui.read.menus.TajweedMenu
import com.abedfattal.quranx.ui.library.ui.read.menus.WordByWordMenu
import com.abedfattal.quranx.ui.library.ui.read.processor.tajweed.TajweedColorsHelper
import com.abedfattal.quranx.ui.library.ui.settings.LibraryPreferences
import com.abedfattal.ui.supported.edition.SupportedUiEditions
import kotlinx.android.synthetic.main.fragment_read_library.*
import java.util.*

abstract class ReadLibraryFragment : Fragment() {

    private val readLibraryViewModel: ReadLibraryViewModel by lazy {
        ViewModelProvider(this).get(ReadLibraryViewModel::class.java)
    }
    private val bookmarksViewModel by lazy { BookmarksViewModel.get(this) }
    private var previewingSurahIndex = 0
    private lateinit var surahsDrawerAdapter: SurahsDrawerAdapter
    private var surahLists: List<Surah> = listOf()

    private lateinit var readLibraryAdapter: ReadLibraryAdapter

    protected abstract val bookEdition: Edition
    protected abstract val startAtSurah: Int
    protected abstract val startAtAya: Int

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

        readLibraryViewModel.getSurahs(bookEdition.identifier).observe(viewLifecycleOwner) {
            surahLists = it
            updateSurahsDrawerAdapter(previewingSurahIndex)
        }
    }

    private fun initFragment() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).initSystemWindows()

        surahsDrawerAdapter = SurahsDrawerAdapter(::onSurahDrawerClicked)
        recycler_surahs_list.adapter = surahsDrawerAdapter

        openLastReadSurah()
        onActivityBackPressed()
    }

    private fun AppCompatActivity.initSystemWindows() {
        if (Build.VERSION.SDK_INT < 23)
            toolbar_library_surah.setBackgroundColor(requireContext().colorAccent)

        toolbar_library_surah.navigationIcon =
            AppCompatResources.getDrawable(this, com.abedfattal.quranx.ui.common.R.drawable.ic_menu)

        setSupportActionBar(toolbar_library_surah)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    private fun updateCurrentSurahInfo(surah: Surah) {
        val defaultLocal = Locale.getDefault()
        if (defaultLocal.isNotArabic) {
            toolbar_library_surah.subtitle = surah.englishNameTranslation
            toolbar_library_surah.title = surah.englishName
        }
        toolbar_library_surah.title = surah.name.removePunctuation()
    }

    private fun openLastReadSurah() {

        val scrollAyahPosition = if (startAtAya != -1) startAtAya
        else tempPreferences.getInt(LastScrollPosition + bookEdition.identifier, 0)

        val lastSurahReadNumber = if (startAtSurah != -1) startAtSurah
        else tempPreferences.getInt(LAST_SURAH_NUMBER_ARG + bookEdition.identifier, 1)

        openSurah(lastSurahReadNumber, scrollAyahPosition)
    }

    private fun openSurah(surahNumber: Int, scrollTo: Int) {
        if (previewingSurahIndex == 0) previewingSurahIndex = surahNumber
        updateSurahsDrawerAdapter(surahNumber)

        bookmarksViewModel

        readLibraryViewModel.getEditionAyat(bookEdition, surahNumber)
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
                mainEditionIsEqualTo(SupportedUiEditions.QURAN_TAJWEED.identifier) -> TajweedMenu(
                    menu,
                    inflater,
                    tajweedColorsHelper,
                    childFragmentManager
                ) {
                    readLibraryAdapter.changeTajweedColors(tajweedColorsHelper.tajweedColors)
                }
                mainEditionIsEqualTo(SupportedUiEditions.EN_WORD_BY_WORD.identifier) -> WordByWordMenu(
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
        saveReadScrollPosition(0)

        readLibraryDrawer.closeDrawer(GravityCompat.START)
        tempPreferences.put(
            LAST_SURAH_NUMBER_ARG + bookEdition.identifier,
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
        (recycler_read_library.layoutManager as? LinearLayoutManager)?.run {
            saveReadScrollPosition(findFirstVisibleItemPosition())
        }
    }

    private fun saveReadScrollPosition(position: Int) {
        tempPreferences.put(LastScrollPosition + bookEdition.identifier, position)
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
                (bookEdition.type == Edition.TYPE_QURAN && bookEdition.identifier == editionId) ||
                        ((bookEdition.type == Edition.TYPE_TAFSEER || bookEdition.type == Edition.TYPE_TRANSLATION)
                                && LibraryPreferences.getTranslationQuranEdition()?.identifier == editionId))
    }

    companion object {

        fun getDefaultTajweed() = Tajweed()
        private const val LastScrollPosition = "Last-Scroll-Position"
        const val LAST_SURAH_NUMBER_ARG = "Last-Viewed-Surah"
    }
}