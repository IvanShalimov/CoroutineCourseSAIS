package com.ivanshalimov.practicecoroutinecoursesais.usecase

import com.ivanshalimov.practicecoroutinecoursesais.Repository
import com.ivanshalimov.practicecoroutinecoursesais.UserUI
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val repository: Repository
) {

    fun execute(): Flow<List<UserUI>> {
        return repository.getUsers()
    }
}