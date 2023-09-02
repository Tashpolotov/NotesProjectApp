package com.example.notesprojectapp.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import com.example.notesprojectapp.R
import com.example.notesprojectapp.databinding.ActivityMainBinding
import com.example.notesprojectapp.fragments.greetingnote.GreetingNoteFragment
import com.example.notesprojectapp.fragments.homefragment.HomeFragment

import com.example.notesprojectapp.fragments.settingfragment.SettingFragment
import com.example.notesprojectapp.util.Pref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SettingFragment.ThemeChangeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: Pref

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ololo", "onCreate - Called")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        pref = Pref.getInstance(this)

        if (!pref.isBoardShow()) {
            // Первый запуск: Показать фрагмент приветствия (GreetingNoteFragment)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fr_container, GreetingNoteFragment())
                .commit()

            // Установить флаг, указывающий, что фрагмент уже был показан
            pref.setBoardShow(true)
        } else {
            // Последующие запуски: Перейти к фрагменту домашней страницы (HomeFragment)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fr_container, HomeFragment())
                .commit()
        }
    }


    override fun onThemeChanged(selectedTheme: String) {
        saveSelectedTheme(selectedTheme)
        Log.d("ololo", "onThemeChanged - New Theme: $selectedTheme")
        applyTheme(selectedTheme)
    }

    private fun applyTheme(selectedTheme: String) {
        when (selectedTheme) {
            "Light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "Dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

        }
        Log.d("ololo", "applyTheme - Theme Applied: $selectedTheme")
    }

    private fun saveSelectedTheme(selectedTheme: String) {
        val sharedPreferences = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("selected_theme", selectedTheme)
        editor.apply()
        Log.d("ololo", "saveSelectedTheme - Theme Saved: $selectedTheme")
    }
    override fun onBackPressed() {
        finish()
    }

}