package com.example.noteapp.api


import com.example.noteapp.models.UserRequest
import com.example.noteapp.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("/signUp")
    suspend fun signUp(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("XYZ/signIn")
    suspend fun signIn(@Body userRequest: UserRequest) : Response<UserResponse>
}