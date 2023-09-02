package com.example.notesprojectapp.viewmodel

import com.example.domain.model.HomeNotesModel
import com.example.domain.model.TemplateNotesModel

data class NotesState(

    val homeNotes: List<HomeNotesModel> = emptyList(),

    val templateNotes: List<TemplateNotesModel> = emptyList(),



)