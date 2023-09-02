package com.example.notesprojectapp.module

import com.example.data.NotesRepositoryMock
import com.example.domain.repository.NotesRepository
import com.example.domain.usecase.NotesUseCase
import com.example.domain.usecase.TempUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRepository():NotesRepository {
        return NotesRepositoryMock()
    }

    @Provides
    @Singleton
    fun providesUseCase(repository: NotesRepository):NotesUseCase{
        return NotesUseCase(repository)
    }


    @Provides
    @Singleton
    fun providesTempUseCase(repository: NotesRepository):TempUseCase{
        return TempUseCase(repository)
    }
}