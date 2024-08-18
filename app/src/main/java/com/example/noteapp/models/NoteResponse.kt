package com.example.noteapp.models

data class NoteResponse(
    val _v : Int,
    val _id : String,
    val createdAt : String,
    val discription : String,
    val title : String,
    val updateAt : String,
    val userId: String

)
