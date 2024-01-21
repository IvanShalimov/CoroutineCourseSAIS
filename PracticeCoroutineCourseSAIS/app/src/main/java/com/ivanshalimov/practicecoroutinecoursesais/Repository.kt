package com.ivanshalimov.practicecoroutinecoursesais

import com.ivanshalimov.practicecoroutinecoursesais.database.UserDao
import com.ivanshalimov.practicecoroutinecoursesais.mapper.UserDbToUserUIMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(
    private val userDao: UserDao,
    private val dbMapper: UserDbToUserUIMapper
) {

    fun getUsers(): Flow<List<UserUI>> {
        return userDao.getAll().map { dbMapper.map(it) }
    }

    suspend fun addUser(user: UserUI) {
        userDao.insert(dbMapper.reversMap(user))
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAll()
    }
}