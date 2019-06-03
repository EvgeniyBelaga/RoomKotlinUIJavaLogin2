package com.example.roomkotlinuijavalogin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val allUsers: LiveData<List<User>>

    init {
        val wordsDao = UserRoomDataBase.getDatabase(application, viewModelScope).userDao()
        repository = UserRepository(wordsDao)
        allUsers = repository.allUsers
    }

    fun insert(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

    fun delateAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.delateAll()
    }

    fun findUserByNameViewModel(name: String)= viewModelScope.launch(Dispatchers.IO) {
        //repository.insert(user)
        //Log.d("LoginViewModel",""+repository.findUserByNameViewModel(name).size )
         var usersByName=repository.findUserByNameRepository(name)
        if (usersByName != null) {
            Log.d("LoginV","UserViewModel.findUserByNameViewModel: size "+ usersByName.size)
            for(user in usersByName){
                Log.d("LoginV","UserViewModel. finUserByName: "+ user.toString())
            }
        }
        else{
            Log.d("LoginV","UserViewModel. finUserByName: size: null ")
        }

//        for(user in MyUtils.usersByName){
//            Log.d("LoginV","UserViewModel. finUserByName: "+ user.toString())
//        }
    }

//    fun findUserByNameViewModel(name: String):Array<User>{
//        return repository.findUserByNameViewModel(name)
//    }

//    {
//        //Log.d("LoginViewModel",""+repository.findUserByNameViewModel(name).size )
//        return repository.findUserByNameViewModel(name)
//    }

    fun loginUser(myUser:User): Boolean{
        //var users= MyUtils.users
        var users= allUsers.value
        var isExists= false

        Log.d("Login", "LoginUser: Start")

        if (users != null) {
            for(user in users){
                if(myUser.userName.equals(user.userName) && (myUser.password.equals(user.password))){
                    isExists=true
                    MyUtils.isLogin=true;

                    Log.d("Login", ""+isExists)

                    return true
                }
            }
            Log.d("Login", ""+isExists)
        }
        return false
    }
}