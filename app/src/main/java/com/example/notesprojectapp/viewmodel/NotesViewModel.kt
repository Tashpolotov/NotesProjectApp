package com.example.notesprojectapp.viewmodel

import android.app.DownloadManager.Query
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.HomeNotesModel
import com.example.domain.model.TemplateNotesModel
import com.example.domain.usecase.NotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val useCase: NotesUseCase):ViewModel() {

    private val _notesHome = MutableStateFlow(NotesState())
    val notesHome: StateFlow<NotesState> = _notesHome

    private val _pinnedNotes = MutableStateFlow<List<HomeNotesModel>>(emptyList())
    val pinnedNotes: StateFlow<List<HomeNotesModel>> = _pinnedNotes




    fun searchHome(query: String) {
        viewModelScope.launch {
            try {
                val searchHomeResult = useCase.searchHomeNotes(query)
                _notesHome.value = NotesState(homeNotes = searchHomeResult)
            } catch (e:Exception){

            }
        }
    }

    fun searchTemp(query: String){
        viewModelScope.launch {
            try {
                val searchTemp = useCase.searchTemplateNotes(query)
                _notesHome.value = NotesState(templateNotes = searchTemp)
            } catch (e:Exception){

            }

        }
    }

    fun delete(id:String) {
        viewModelScope.launch {
            try {
                useCase.delete(id)
                val deleteUpdate = useCase.repository.getHomeNotes(id)
                _notesHome.value = NotesState(homeNotes = deleteUpdate)
            } catch (e:Exception){

            }
        }
    }

    fun update(id: String, updatedHomeNotesModel: HomeNotesModel) {
        viewModelScope.launch {
            try {
                // Вызовите метод для обновления данных в репозитории
                useCase.repository.updateHomeNote (id, updatedHomeNotesModel)

                // Получите обновленный список заметок после сохранения
                val updatedNotes = useCase.repository.getHomeNotes(id)

                // Обновите состояние _notesHome внутри ViewModel
                _notesHome.value = NotesState(homeNotes = updatedNotes)
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }

    fun addTemplateNotes(id:String, templateNotesModel: TemplateNotesModel){
        viewModelScope.launch {
            try {
                val updatesTemplateNotes = useCase(id, templateNotesModel)
                _notesHome.value = NotesState(templateNotes = updatesTemplateNotes)
            } catch (e:Exception){
                // Обработка ошибок
            }
        }
    }

    fun refreshTemplatesNotes(id: String) {
        viewModelScope.launch {
            try {
                val updatedTemplatesNotes = useCase.repository.getTemplateNotes(id) // Получите список заметок из репозитория для указанного id
                _notesHome.value = NotesState(templateNotes = updatedTemplatesNotes)
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }


    fun addNotes(id: String, homeNotesModel: HomeNotesModel) {
        viewModelScope.launch {
            try {
                val updatedNotes = useCase(id, homeNotesModel)
                _notesHome.value = NotesState(homeNotes = updatedNotes)
                Log.d("NotesViewModel", "Добавление заметки в NotesViewModel: $homeNotesModel")
            } catch (e: Exception) {
                Log.e("NotesViewModel", "Ошибка при добавлении заметки в NotesViewModel", e)
            }
        }
    }

    fun refreshNotes(id: String) {
        viewModelScope.launch {
            try {
                val updatedNotes = useCase.repository.getHomeNotes(id) // Получите список заметок из репозитория для указанного id
                _notesHome.value = NotesState(homeNotes = updatedNotes)
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }

    fun addPinnedNote(note: HomeNotesModel) {
        val currentList = _pinnedNotes.value.toMutableList()
        currentList.add(note)
        _pinnedNotes.value = currentList
    }

}