    package com.example.domain.usecase

    import com.example.domain.model.HomeNotesModel
    import com.example.domain.model.TemplateNotesModel
    import com.example.domain.repository.NotesRepository

        class NotesUseCase(val repository: NotesRepository) {

        operator fun invoke(id: String, homeNotesModel: HomeNotesModel): List<HomeNotesModel> {
            val homeNotes = repository.getHomeNotes(id)
            repository.addHomeNote(homeNotesModel)

            return homeNotes
        }

        operator fun invoke(id: String, templateNotesModel: TemplateNotesModel): List<TemplateNotesModel> {
            val templatesNotes = repository.getTemplateNotes(id)
            repository.addTemplatesNotes(templateNotesModel)
            return templatesNotes
        }


        // Добавьте метод для обновления заметки
         fun update(id:String, homeNotesModel: HomeNotesModel) {
            repository.updateHomeNote(id, homeNotesModel)
        }

            fun delete(id:String) {
                repository.deleteHomeNote(id)
            }

            fun searchTemplateNotes(query: String): List<TemplateNotesModel> {
                // Вызываем метод поиска в репозитории и возвращаем результат
                return repository.searchTemplateNotes(query)
            }

            fun searchHomeNotes(query: String): List<HomeNotesModel> {
                // Вызываем метод поиска в репозитории и возвращаем результат
                return repository.searchHomeNotes(query)
            }
    }

