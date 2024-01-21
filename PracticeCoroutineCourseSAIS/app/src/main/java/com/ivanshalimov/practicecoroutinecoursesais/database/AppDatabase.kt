package com.ivanshalimov.practicecoroutinecoursesais.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Comment::class, User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comments(): CommentDao
    abstract fun users(): UserDao
}