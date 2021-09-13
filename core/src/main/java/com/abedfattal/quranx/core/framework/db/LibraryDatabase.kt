package com.abedfattal.quranx.core.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abedfattal.quranx.core.framework.db.daos.BookmarksDao
import com.abedfattal.quranx.core.framework.db.daos.EditionsDao
import com.abedfattal.quranx.core.framework.db.daos.LanguagesDao
import com.abedfattal.quranx.core.framework.db.daos.QuranDao
import com.abedfattal.quranx.core.model.*

/** @suppress */
@Database(
    entities = [Aya::class, Edition::class, Surah::class, Bookmark::class, Language::class],
    version = DATA_VERSION,
)
abstract class LibraryDatabase : RoomDatabase() {
    abstract fun getQuranDao(): QuranDao
    abstract fun getBookmarksDao(): BookmarksDao
    abstract fun getEditionsDao(): EditionsDao
    abstract fun getLanguagesDao(): LanguagesDao
}