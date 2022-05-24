package com.abedfattal.quranx.ui.library.ui.bookmarks

import androidx.lifecycle.*
import com.abedfattal.quranx.core.framework.data.DataSources
import com.abedfattal.quranx.core.model.AyaWithInfo
import com.abedfattal.quranx.core.model.Bookmark
import com.abedfattal.quranx.ui.library.ReadLibrary
import com.abedfattal.quranx.ui.library.framework.usecases.BookmarksUpdateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BookmarksViewModel : ViewModel() {
    private val bookmarksRepository = DataSources.localDataSource.bookmarksRepository

    fun listenToAllAyatBookmarks(): LiveData<List<AyaWithInfo>> =
        bookmarksRepository.listenToAyatBookmarksChanges().map { ayatList ->
            ayatList.sortedWith(
                compareBy(
                    { it.edition.type },
                    { it.surah.number },
                    { it.aya.numberInSurah },
                )
            )
        }
            .asLiveData(Dispatchers.IO)

    fun listenToAyaBookmarks(id: String): LiveData<List<Bookmark>> =
        bookmarksRepository.listenToEditionChanges(id).asLiveData(Dispatchers.IO)


    fun listenToBookmarkedEdition(editionId: String): LiveData<List<AyaWithInfo>> =
        bookmarksRepository.listenToEditionBookmarks(editionId).asLiveData(Dispatchers.IO)


    fun updateBookmarkStateInData(ayaWithInfo: AyaWithInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            BookmarksUpdateUseCase.updateBookmark(
                ayaWithInfo = ayaWithInfo,
                removePermanently = ReadLibrary.libraryConfig.bookmarksRemovedPermanently,
            )
        }
    }


    fun removeBookmarks(bookmarks: List<Bookmark>) {
        viewModelScope.launch(Dispatchers.IO) {
            BookmarksUpdateUseCase.removeBookmarks(
                bookmarks = bookmarks,
                removePermanently = ReadLibrary.libraryConfig.bookmarksRemovedPermanently
            )
        }
    }


    companion object {
        fun get(owner: ViewModelStoreOwner) =
            ViewModelProvider(owner).get(BookmarksViewModel::class.java)
    }
}