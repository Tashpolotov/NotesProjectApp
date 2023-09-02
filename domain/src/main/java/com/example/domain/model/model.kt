package com.example.domain.model

data class model (

    val id: String, // Уникальный идентификатор заметки
    val day: String,
    val month: String,
    val year: String,
    val time: String,
    var textDesc: String,
    var textNotes: String,
    val isTemplate: Boolean, // Поле, указывающее, что это заметка, связанная с шаблоном
    val templateId: String? // Ид
)