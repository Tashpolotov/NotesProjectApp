package com.example.domain.usecase

import com.example.domain.model.HomeNotesModel
import com.example.domain.model.model
import com.example.domain.repository.NotesRepository

class TempUseCase(val repository: NotesRepository) {

    operator fun invoke(id: String, homeNotesModel: model): List<model> {
        val tempHomeNotes = repository.getTempNotes(id)
        repository.addTempNote(homeNotesModel)

        return tempHomeNotes
    }

    // Добавьте метод для обновления заметки
    fun update(id:String, model: model) {
        repository.updateEdit(id, model)
    }

    fun delete(id:String) {
        repository.deleteTempNote(id)
    }
}