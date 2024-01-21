package com.ivanshalimov.practicecoroutinecoursesais.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<UserDb>>

    @Insert
    suspend fun insert(user: UserDb)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

}