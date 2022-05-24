package com.abedfattal.ui.library.pagenav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.abedfattal.quranx.ui.library.LibraryUIConstants
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


open class LibraryPagerFragment : Fragment() {

    private val librarySettingsButton: MaterialButton by lazy { requireView().findViewById(R.id.librarySettingsButton) }
    private val tabs: TabLayout by lazy { requireView().findViewById(R.id.library_tabs) }
    private val pager2: ViewPager2 by lazy { requireView().findViewById(R.id.library_pager) }


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

    }

    private fun initViewPager() {
        pager2.adapter = LibraryPager(childFragmentManager, lifecycle)

        TabLayoutMediator(
            tabs,
            pager2
        ) { tab, position ->
            tab.text = when (position) {
                LibraryUIConstants.MAIN_FRAGMENT -> getString(com.abedfattal.quranx.ui.library.R.string.books)
                LibraryUIConstants.BOOKMARKS_FRAGMENT -> getString(com.abedfattal.quranx.ui.library.R.string.bookmarks)
                else -> throw IllegalArgumentException("Unknown tab position $position")
            }
        }.attach()
    }

}