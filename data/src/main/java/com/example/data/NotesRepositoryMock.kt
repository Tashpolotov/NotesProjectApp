package com.example.data

import com.example.domain.model.HomeNotesModel
import com.example.domain.model.TemplateNotesModel
import com.example.domain.model.UpdateModel
import com.example.domain.model.model
import com.example.domain.repository.NotesRepository

class NotesRepositoryMock:NotesRepository {
    private val homeNotesList = mutableListOf<HomeNotesModel>()
    private val templatesNotesList = mutableListOf<TemplateNotesModel>()
    private val tempHomeNoteList = mutableListOf<model>()


    override fun getHomeNotes(id: String): List<HomeNotesModel> {
        return homeNotesList
    }

    override fun addHomeNote(homeNotesModel: HomeNotesModel) {
        homeNotesList.add(homeNotesModel)
    }

    override fun searchHomeNotes(query: String): List<HomeNotesModel> {
        val searchHome = homeNotesList.filter {
            it.workList.contains(query, ignoreCase = true) || it.textNotes.contains(query, ignoreCase = true)
        }
        return searchHome
    }

    override fun getTemplateNotes(id: String): List<TemplateNotesModel> {
        return templatesNotesList
    }

    override fun addTemplatesNotes(templateNotesModel: TemplateNotesModel) {
        templatesNotesList.add(templateNotesModel)
    }

    override fun searchTemplateNotes(query: String): List<TemplateNotesModel> {
        val searchTemp = templatesNotesList.filter {
            it.workList.contains(query, ignoreCase = true)
        }
        return searchTemp
    }

    override fun getTempNotes(id: String): List<model> {
        return tempHomeNoteList
    }

    override fun addTempNote(homeNotesModel: model) {
        tempHomeNoteList.add(homeNotesModel)
    }

    override fun updateHomeNote(id:String, homeNotesModel: HomeNotesModel) {
        // Обновите заметку здесь, например, поиском по id и обновлением полей
        val existingNote = homeNotesList.find { it.id == homeNotesModel.id }
        existingNote?.let {
            // Обновление полей заметки
            it.workList = homeNotesModel.workList
            it.textNotes = homeNotesModel.textNotes
            // Другие обновления, если необходимо
        }
    }

    override fun updateEdit(id: String, model: model) {
        val existingTemp = tempHomeNoteList.find { it.id == model.id }
        existingTemp?.let {
            it.textNotes = model.textNotes
            it.textDesc = model.textDesc
        }
    }

    override fun deleteHomeNote(id: String) {
        val delete = homeNotesList.find { it.id == id }
            delete?.let {
                homeNotesList.remove(it)
            }
    }

    override fun deleteTempNote(id: String) {
        val delete = tempHomeNoteList.find { it.id == id }
        delete?.let {
            tempHomeNoteList.remove(it)
        }
    }
}