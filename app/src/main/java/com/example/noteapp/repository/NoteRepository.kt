package com.example.noteapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapp.Utlils.NetworkResult
import com.example.noteapp.api.NoteAPI
import com.example.noteapp.models.NoteRequest
import com.example.noteapp.models.NoteResponse
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val notesAPI : NoteAPI) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData : LiveData<NetworkResult<List<NoteResponse>>>
        get() = notesLiveData
    private val _statusResponse = MutableLiveData<NetworkResult<String>>()
        val statusResponse : LiveData<NetworkResult<String>>
        get() = _statusResponse

    suspend fun getNotes(){
        _notesLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.getNotes()

        if (response.isSuccessful && response.body() != null) {
            _notesLiveData.postValue(NetworkResult.Success(response.body()!!))

        } else if (response.errorBody() != null) {

            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))

        } else {
            _notesLiveData .postValue(NetworkResult.Error("Code have an error"))


        }
    }

    suspend fun createNotes(noteRequest: NoteRequest){
          _statusResponse.postValue(NetworkResult.Loading())
        val response = notesAPI.createNote(noteRequest)
        handleResponse(response,"Note Created")
    }


    suspend fun deleteNote(noteId : String){
        _statusResponse.postValue(NetworkResult.Loading())
        val response = notesAPI.deleteNote(noteId)
        handleResponse(response,"note Updated")
    }
    suspend fun updateNotes(noteId: String,noteRequest: NoteRequest){
        _statusResponse.postValue(NetworkResult.Loading())
        val response = notesAPI.updateNotes(noteId,noteRequest)
        handleResponse(response,"note Updated")
    }

    fun handleResponse(response: Response<NoteResponse>,message : String){

        if (response.isSuccessful && response.body()!=null) {
            _statusResponse.postValue(NetworkResult.Success(message))
        }
        else{
            _statusResponse.postValue(NetworkResult.Error(message))
        }
    }

}