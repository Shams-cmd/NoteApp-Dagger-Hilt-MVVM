package com.example.noteapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.models.NoteRequest
import com.example.noteapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

    val notesLivedata get() = noteRepository.notesLiveData
    val notesStatus get() = noteRepository.statusResponse
    fun getNotes(){
        viewModelScope.launch {
            noteRepository.getNotes()
        }

    }

    fun createNote(noteRequest: NoteRequest){
        viewModelScope.launch {
            noteRepository.createNotes(noteRequest)
        }
    }

    fun updateNote(noteId : String, noteRequest: NoteRequest){
        viewModelScope.launch {
            noteRepository.updateNotes(noteId,noteRequest)
        }
    }

    fun deleteNote(noteId : String){
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
        }
    }
}