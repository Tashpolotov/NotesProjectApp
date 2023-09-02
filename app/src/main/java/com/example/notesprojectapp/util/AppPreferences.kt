package com.example.notesprojectapp.util

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun saveLastEntry(
        id: String,
        date: String,
        month: String,
        years: String,
        time: String,
        workList: String,
        textNotes: String,
        isImportant: Boolean
    ) {
        val editor = sharedPreferences.edit()
        editor.putString("last_id", id)
        editor.putString("last_date", date)
        editor.putString("last_month", month)
        editor.putString("last_years", years)
        editor.putString("last_time", time)
        editor.putString("last_work_list", workList)
        editor.putString("last_text_notes", textNotes)
        editor.putBoolean("last_is_important", isImportant)
        editor.apply()
    }

    fun getLastEntry(): LastEntryModel {
        val id = sharedPreferences.getString("last_id", "") ?: ""
        val date = sharedPreferences.getString("last_date", "") ?: ""
        val month = sharedPreferences.getString("last_month", "") ?: ""
        val years = sharedPreferences.getString("last_years", "") ?: ""
        val time = sharedPreferences.getString("last_time", "") ?: ""
        val workList = sharedPreferences.getString("last_work_list", "") ?: ""
        val textNotes = sharedPreferences.getString("last_text_notes", "") ?: ""
        val isImportant = sharedPreferences.getBoolean("last_is_important", false)

        return LastEntryModel(id, date, month, years, time, workList, textNotes, isImportant)
    }

    data class LastEntryModel(
        val id: String,
        val date: String,
        val month: String,
        val years: String,
        val time: String,
        val workList: String,
        val textNotes: String,
        val isImportant: Boolean
    )
}