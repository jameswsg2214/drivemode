package com.wils.drivingmode.roomdb

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM ContactEntity")
    fun getContactEntity(): Flow<List<ContactEntity>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSelected(contactEntity: List<ContactEntity>)

    @Query("DELETE  FROM ContactEntity")
    fun clearAllData()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: ContactEntity)
}