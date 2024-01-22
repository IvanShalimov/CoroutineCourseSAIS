package com.ivanshalimov.practicecoroutinecoursesais

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivanshalimov.practicecoroutinecoursesais.database.AppDatabase
import com.ivanshalimov.practicecoroutinecoursesais.mapper.UserDbToUserUIMapper
import com.ivanshalimov.practicecoroutinecoursesais.usecase.AddUserUseCase
import com.ivanshalimov.practicecoroutinecoursesais.usecase.DeleteAllUsersUseCase
import com.ivanshalimov.practicecoroutinecoursesais.usecase.GetUsersUseCase
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel2: ViewModel() {

    var repository: Repository? = null
    var getUsers: GetUsersUseCase? = null
    var addUser: AddUserUseCase? = null
    var deleteAll: DeleteAllUsersUseCase? = null

    private var isStartNeeded: Boolean = true

    fun startInit(db: AppDatabase) {
        if(!isStartNeeded) return

        repository = Repository(db.users(), UserDbToUserUIMapper())
        repository?.let {
            getUsers = GetUsersUseCase(it)
            addUser = AddUserUseCase(it,UserValidator())
            deleteAll= DeleteAllUsersUseCase(it)
        }

        //val users = getUsers?.execute()?.asLiveData()
    }

    fun onAddClick() {
        viewModelScope.launch {
            val result = addUser?.execute(UserUI(UUID.randomUUID().toString(), UUID.randomUUID().toString()))

            /*if (result is Error) {
                //showError(result.exception)
            }*/
        }
    }


    fun onClearClick() {
        viewModelScope.launch {
            deleteAll?.execute()
        }
    }



}