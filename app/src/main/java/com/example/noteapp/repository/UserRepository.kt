package com.example.noteapp.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapp.Utlils.NetworkResult
import com.example.noteapp.api.UserAPI
import com.example.noteapp.models.UserRequest
import com.example.noteapp.models.UserResponse
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    private val userRepositoryLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponnseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepositoryLiveData


    suspend fun registerUser(userRequest: UserRequest) {
        // userRepositoryLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.signUp(userRequest)
        handleResponse(response)

    }

    suspend fun loginUser(userRequest: UserRequest) {
        // userRepositoryLiveData.postValue(NetworkResult.Loading())j
        val response = userAPI.signIn(userRequest)
        handleResponse(response)
        Log.d(TAG, response.body().toString())
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            userRepositoryLiveData.postValue(NetworkResult.Success(response.body()!!))

        } else if (response.errorBody() != null) {

            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            userRepositoryLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))

        } else {
            userRepositoryLiveData.postValue(NetworkResult.Error("Code have an error"))


        }
    }


}