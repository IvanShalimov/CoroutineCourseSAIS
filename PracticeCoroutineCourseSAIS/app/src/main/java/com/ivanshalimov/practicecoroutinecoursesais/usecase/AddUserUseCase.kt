package com.ivanshalimov.practicecoroutinecoursesais.usecase

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.ivanshalimov.practicecoroutinecoursesais.Repository
import com.ivanshalimov.practicecoroutinecoursesais.UserUI
import com.ivanshalimov.practicecoroutinecoursesais.UserValidator

class AddUserUseCase(
    private val repository: Repository,
    private val userValidator: UserValidator
) {

    suspend fun execute(user: UserUI): Result<Unit> {
        if (!userValidator.isValid(user)) {
            return Result.failure(IllegalArgumentException("User is not valid"))
        }

        try {
            repository.addUser(user)
        } catch (e: Exception) {
            return Result.failure(e)
        }

        return Result.success(Unit)
    }
}