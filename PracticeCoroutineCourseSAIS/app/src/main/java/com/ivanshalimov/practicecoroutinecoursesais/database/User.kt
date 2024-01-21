package com.ivanshalimov.practicecoroutinecoursesais.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "password") val password: String
)