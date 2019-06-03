package com.example.roomkotlinuijavalogin

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
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

    fun findUserByNameAndPasswordViewModel(name: String, password: String,
                                           etName:EditText, etPassword:EditText,
                                           btnSubmit:Button, btnLogout:Button)= viewModelScope.launch(Dispatchers.IO) {
        var usersByNameAndPassword= repository.findUserByNameAndPasswordRepository(name, password)
        if (usersByNameAndPassword != null) {
            Log.d("LoginV","UserViewModel.findUserByNameAndPasswordViewModel: size "+ usersByNameAndPassword.size)
            if(usersByNameAndPassword.size>0){
                MyUtils.isLogin= true
//                val mainActivity:MainActivity= MainActivity()
//                mainActivity.initilizeLayoutDisplay()
                etName.visibility= View.GONE
                etPassword.visibility= View.GONE
                btnSubmit.visibility= View.GONE
                btnLogout.visibility= View.VISIBLE
            }
            for(user in usersByNameAndPassword){
                Log.d("LoginV","UserViewModel. finUserByNameAndPasswordViewModel: "+ user.toString())
            }
        }
        else{
            Log.d("LoginV","UserViewModel. finUserByNameAndPassword: size: null ")
        }
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