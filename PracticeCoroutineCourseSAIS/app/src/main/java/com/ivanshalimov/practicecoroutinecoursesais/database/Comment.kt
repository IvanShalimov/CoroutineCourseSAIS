package com.ivanshalimov.practicecoroutinecoursesais.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "text")val text:String
)
