package com.abedfattal.quranx.core.framework.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abedfattal.quranx.core.framework.db.EDITIONS_TABLE
import com.abedfattal.quranx.core.model.Edition
import kotlinx.coroutines.flow.Flow

/** @suppress */
@Dao
interface EditionsDao {
    @Query("select * from $EDITIONS_TABLE order by language asc")
    fun listenToEditions(): Flow<List<Edition>>

    @Query("select * from $EDITIONS_TABLE where type == :type order by language asc")
    suspend fun getEditionsByType(type: String): List<Edition>

    @Query("select * from $EDITIONS_TABLE where format == :format order by language asc")
    suspend fun getEditionsByFormat(format: String): List<Edition>

    @Query("select * from $EDITIONS_TABLE where format == :format and language == :language and type == :type order by language asc")
    suspend fun getEditions(format: String, language: String, type: String): List<Edition>

    @Query("select * from $EDITIONS_TABLE where id == :editionId order by language asc")
    suspend fun getEdition(editionId: String): Edition?

    @Query("select * from $EDITIONS_TABLE where id in (:editionIds) order by language asc")
    suspend fun getAllEditions(vararg editionIds: String): List<Edition>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEdition(edition: Edition)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllEdition(editions: List<Edition>)

    @Query("delete from $EDITIONS_TABLE where id == :editionId")
    suspend fun deleteEdition(editionId: String)

    @Query("delete from $EDITIONS_TABLE")
    suspend fun deleteAllEditions()
}