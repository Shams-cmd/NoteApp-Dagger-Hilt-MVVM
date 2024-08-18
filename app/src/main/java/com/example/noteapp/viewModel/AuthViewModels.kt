package com.example.noteapp.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.noteapp.Utlils.NetworkResult
import com.example.noteapp.models.UserRequest
import com.example.noteapp.models.UserResponse
import com.example.noteapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModels @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponnseLiveData

    fun signup(userRequest: UserRequest){
       viewModelScope.launch {
           userRepository.registerUser(userRequest)
       }

    }

    fun login(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }

    }

    fun validatesCredentials(userName : String,emailAddress : String,
    password : String,isLogin : Boolean) : Pair<Boolean,String>{
        var result = Pair(true,"")
        if (!!isLogin && TextUtils.isEmpty(userName)|| TextUtils.isEmpty(emailAddress)
            || TextUtils.isEmpty(password)){
            result = Pair(false,"Please Provide the credintials")

        }

        else if (Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            result = Pair(false,"Please provide valid email")
        }
        else if (password.length <= 5){
            result = Pair(false,"Password length should be greater than five")
        }

        return result
    }
}