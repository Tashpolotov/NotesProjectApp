package com.example.notesprojectapp.util

import android.content.Context
import android.content.SharedPreferences
import android.text.BoringLayout
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Pref @Inject constructor(@ApplicationContext context: Context) {


        private val sharedPref : SharedPreferences = context.getSharedPreferences(
            "pref",
            Context.MODE_PRIVATE
        )

        fun isBoardShow():Boolean{
            return sharedPref.getBoolean("boardingShow", false)
        }

        fun setBoardShow(isShow:Boolean) {
            return sharedPref.edit().putBoolean("boardingShow", isShow).apply()
        }
    companion object {
        @Volatile
        private var INSTANCE: Pref? = null

        fun getInstance(context: Context): Pref {
            return INSTANCE ?: synchronized(this) {
                val instance = Pref(context)
                INSTANCE = instance
                instance
            }
        }
    }

}