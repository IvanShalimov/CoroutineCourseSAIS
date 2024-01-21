package com.ivanshalimov.practicecoroutinecoursesais.usecase

import com.ivanshalimov.practicecoroutinecoursesais.Repository

class DeleteAllUsersUseCase(
    private val repository: Repository
) {

    suspend fun execute() {
        repository.deleteAllUsers()
    }
}