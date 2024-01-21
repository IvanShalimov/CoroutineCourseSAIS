package com.ivanshalimov.practicecoroutinecoursesais.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Comment::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comments(): CommentDao
}