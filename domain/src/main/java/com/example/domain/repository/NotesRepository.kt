package com.example.domain.repository

import com.example.domain.model.HomeNotesModel
import com.example.domain.model.TemplateNotesModel
import com.example.domain.model.model

interface NotesRepository {

    fun getHomeNotes(id:String):List<HomeNotesModel>
    fun addHomeNote(homeNotesModel: HomeNotesModel)
    fun searchHomeNotes(query: String): List<HomeNotesModel>


    fun getTemplateNotes(id:String):List<TemplateNotesModel>
    fun addTemplatesNotes(templateNotesModel: TemplateNotesModel)
    fun searchTemplateNotes(query: String): List<TemplateNotesModel>

    fun getTempNotes(id:String):List<model>
    fun addTempNote(homeNotesModel: model)

    fun updateHomeNote(id:String, homeNotesModel: HomeNotesModel)
    fun updateEdit(id:String, model: model)

    fun deleteHomeNote(id:String)

    fun deleteTempNote(id:String)




}