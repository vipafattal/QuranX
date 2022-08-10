package com.abedfattal.quranx.core.rules

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.abedfattal.quranx.core.framework.db.LibraryDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class LibraryDatabaseRule : TestWatcher() {

    lateinit var db: LibraryDatabase
        private set

    override fun starting(description: Description?) {
        super.starting(description)
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context, LibraryDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        db.close()
    }
}