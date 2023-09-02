package com.example.domain.model

data class HomeNotesModel(

    val id:String,
    val date: String,
    val month:String,
    val years: String,
    val time:String,
    var workList:String,
    var textNotes:String,
    val isImportant:Boolean

)
