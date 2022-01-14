package com.abedfattal.quranx.ui.library.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abedfattal.quranx.ui.library.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_library_pager.*


open class LibraryPagerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library_pager, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        librarySettingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_libraryPagerFragment_to_settingsFragment)
        }/*
        lifecycleScope.launchWhenResumed {
            val isPremiumUser =
                withContext(Dispatchers.IO) { libraryPremiumHelper.isPremiumActive() }
            (go_premium_button as GoPremiumButton).setIsPremium(isPremiumUser)
        }*/
        initViewPager()

        //if coming from shortcut.
        arguments?.getString(LIBRARY_BOOK_EDITION_ARG)?.let { jsonEdition ->
            findNavController().navigate(
                LibraryPagerFragmentDirections.actionLibraryPagerFragmentToReadLibraryFragment(
                    jsonEdition
                )
            )
            //When resuming fragment doesn't launch back this block.
            requireArguments().putString(LIBRARY_BOOK_EDITION_ARG, null)
        }
    }

    private fun initViewPager() {
        library_pager.adapter = LibraryPager(childFragmentManager, lifecycle)

        TabLayoutMediator(
            library_tabs,
            library_pager
        ) { tab, position ->
            tab.text = when (position) {
                MAIN_FRAGMENT -> getString(R.string.books)
                BOOKMARKS_FRAGMENT -> getString(R.string.bookmarks)
                else -> throw IllegalArgumentException("Unknown tab position $position")
            }
        }.attach()
    }

    companion object {
        const val MAIN_FRAGMENT = 0
        const val BOOKMARKS_FRAGMENT = 1

        const val LIBRARY_BOOK_EDITION_ARG = "Reading-Edition-ID"

    }
}