package com.example.noteapp.api

import android.provider.ContactsContract.CommonDataKinds.Note

import com.example.noteapp.models.NoteRequest
import com.example.noteapp.models.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteAPI
{
    @GET("/note")
    suspend fun getNotes() : Response<List<NoteResponse>>

    @POST("/note")
    suspend fun createNote(@Body noteReques: NoteRequest) : Response<NoteResponse>

    @PUT("/note/{noteId}")
    suspend fun updateNotes(@Path("noteId")noteId : String, @Body noteRequest: NoteRequest) : Response<NoteResponse>

    @DELETE("/note/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId : String) : Response<String>
}