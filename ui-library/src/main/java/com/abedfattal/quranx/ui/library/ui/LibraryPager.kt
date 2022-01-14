package com.abedfattal.quranx.ui.library.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abedfattal.quranx.ui.library.ui.bookmarks.BookmarkFragment
import com.abedfattal.quranx.ui.library.ui.books.LibraryBooksFragment

class LibraryPager(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList = listOf(
        LibraryBooksFragment(),
        BookmarkFragment(),
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}