package com.ivanshalimov.practicecoroutinecoursesais.mapper

import com.ivanshalimov.practicecoroutinecoursesais.UserUI
import com.ivanshalimov.practicecoroutinecoursesais.database.UserDb

class UserDbToUserUIMapper {

    fun map(userDb: List<UserDb>): List<UserUI> = userDb.map {
        UserUI(it.login, it.password)
    }

    fun reversMap(userUI: UserUI): UserDb {
        return UserDb(userUI.login, userUI.password)
    }
}