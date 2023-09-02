package com.example.notesprojectapp.fragments.greetingnote

import android.content.Context
import android.graphics.Rect
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.domain.model.HomeNotesModel
import com.example.notesprojectapp.R
import com.example.notesprojectapp.databinding.FragmentGreetNoteBinding
import com.example.notesprojectapp.databinding.FragmentGreetinNoteBinding
import com.example.notesprojectapp.fragments.homefragment.HomeFragment
import com.example.notesprojectapp.util.AppPreferences
import com.example.notesprojectapp.viewmodel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class GreetNoteFragment : Fragment() {

    private lateinit var binding: FragmentGreetNoteBinding
    private lateinit var linearLayout: LinearLayout
    private val viewModel by viewModels<NotesViewModel>()
    private var isSkrepkaClicked = false
    private var isChecked = false

    private lateinit var appPreferences: AppPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGreetNoteBinding.inflate(inflater, container, false)
        linearLayout = binding.linearkeyboard
        appPreferences = AppPreferences(requireContext())
        val rootLayout = binding.root
        rootLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootLayout.getWindowVisibleDisplayFrame(rect)

            val screenHeight = rootLayout.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) {
                linearLayout.visibility = View.VISIBLE
            } else {
                linearLayout.visibility = View.GONE
            }
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }


        val currentDateTime = Calendar.getInstance().time
        val dateFormatter = SimpleDateFormat("dd", Locale.getDefault())
        val monthFormatter = SimpleDateFormat("MMMM", Locale.getDefault())
        val yearFormatter = SimpleDateFormat("yyyy", Locale.getDefault())
        val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

        val day = dateFormatter.format(currentDateTime)
        val month = monthFormatter.format(currentDateTime)
        val year = yearFormatter.format(currentDateTime)
        val time = timeFormatter.format(currentDateTime)

        binding.tvDate.text = day
        binding.tvMonth.text = month
        binding.tvYears.text = year
        binding.tvTime.text = time

        binding.imgSkrepka.setOnClickListener {
            isSkrepkaClicked = true
            binding.checkBox.visibility = View.VISIBLE
            binding.tvCheck.visibility = View.VISIBLE
            hideKeyboard()

            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                this.isChecked = isChecked
            }
        }

        binding.imgAdd.setOnClickListener {

            val id = System.currentTimeMillis().toString()
            val textDesc = binding.etWork.text.toString()
            val textNotes = binding.etText.text.toString()
            // Проверка на заполненность полей
            if (textDesc.isEmpty() || textNotes.isEmpty()) {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newNote = HomeNotesModel(id, day, month, year,  time, textDesc, textNotes, isChecked)
            viewModel.addNotes(id, newNote)
            if (isChecked) {
                viewModel.addPinnedNote(newNote)
            }

            appPreferences.saveLastEntry(id, day, month, year, time, textDesc, textNotes, isChecked)
            val fragment = HomeFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fr_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}