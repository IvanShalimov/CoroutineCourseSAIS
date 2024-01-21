package com.ivanshalimov.practicecoroutinecoursesais.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CommentDao {
    @Query("SELECT * FROM comment")
    suspend fun getAll(): List<Comment>
}