package com.example.notesprojectapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.HomeNotesModel
import com.example.domain.model.TemplateNotesModel
import com.example.domain.model.model
import com.example.domain.usecase.NotesUseCase
import com.example.domain.usecase.TempUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TempViewModel @Inject constructor(private val useCase: TempUseCase):ViewModel() {

    private val _tempHome = MutableStateFlow(TempState())
    val tempsHome: StateFlow<TempState> = _tempHome



    fun delete(id:String) {
        viewModelScope.launch {
            try {
                useCase.delete(id)
                val deleteUpdate = useCase.repository.getTempNotes(id)
                _tempHome.value = TempState(tempNotes = deleteUpdate)
            } catch (e:Exception){

            }
        }
    }


    fun update(id: String, updatedHomeNotesModel: model) {
        viewModelScope.launch {
            try {
                useCase.repository.updateEdit(id, updatedHomeNotesModel)
                val updatedNotes = useCase.repository.getTempNotes(id)
                _tempHome.value = TempState(tempNotes = updatedNotes)
            } catch (e: Exception) {

            }
        }
    }


    fun addNotesTemp(id: String, homeNotesModel: model) {
        viewModelScope.launch {
            try {
                val updatedNotes = useCase(id, homeNotesModel)
                _tempHome.value = TempState(tempNotes = updatedNotes)
                Log.d("TempViewModel", "Добавление заметки в TempViewModel: $homeNotesModel")
            } catch (e: Exception) {
                Log.e("TempViewModel", "Ошибка при добавлении заметки в TempViewModel", e)
            }
        }
    }

    fun refreshNoteTemp(id: String) {
        viewModelScope.launch {
            try {
                val updatedNotes = useCase.repository.getTempNotes(id) // Получите список заметок из репозитория для указанного id
                _tempHome.value = TempState(tempNotes = updatedNotes)
            } catch (e: Exception) {

            }
        }
    }

}