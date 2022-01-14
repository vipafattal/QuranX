package com.abedfattal.quranx.ui.library.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.core.model.Bookmark
import com.abedfattal.quranx.core.model.Edition
import com.abedfattal.quranx.ui.library.ReadLibrary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class BookmarksViewModel : ViewModel() {
    private val bookmarksRepository = DataSources.localDataSource.bookmarksRepository

    fun listenToAllBookmarks(): LiveData<List<AyaWithInfo>> =
        bookmarksRepository.listenToBookmarksChanges()
            .map { it.distinctBy { it.aya.number }.filter { it.bookmark != null } }
            .asLiveData(Dispatchers.IO)

    fun listenToBookmarkedEdition(editionId: String): LiveData<List<AyaWithInfo>> =
        bookmarksRepository.listenToEditionBookmarks(editionId).asLiveData(Dispatchers.IO)

    fun addBookmark(ayaNumber: Int, edition: Edition) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarksRepository.addBookmark(
                ayaNumber,
                edition.id,
                edition.type,
                ReadLibrary.userId ?: ""
            )
        }
    }

    fun removeBookmarks(bookmarks: List<Bookmark>) {
        viewModelScope.launch(Dispatchers.IO) {
            for (b in bookmarks) {
                bookmarksRepository.updateBookmark(b.copy(isDeleted = true, isDirty = true, lastUpdate = Date()))
            }
        }
    }
    fun removeBookmarks(bookmark: Bookmark) {
        viewModelScope.launch(Dispatchers.IO) {
                bookmarksRepository.updateBookmark(bookmark.copy(isDeleted = true, isDirty = true, lastUpdate = Date()))

        }
    }
}