package com.example.notesprojectapp.template

import android.content.Context
import android.graphics.Rect
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.domain.model.HomeNotesModel
import com.example.domain.model.model
import com.example.notesprojectapp.R
import com.example.notesprojectapp.databinding.FragmentAddTemplateFragmentBinding
import com.example.notesprojectapp.databinding.FragmentGreetNoteBinding
import com.example.notesprojectapp.fragments.homefragment.HomeFragment
import com.example.notesprojectapp.viewmodel.NotesViewModel
import com.example.notesprojectapp.viewmodel.TempViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddTemplateFragmentFragment : Fragment() {

    private lateinit var binding: FragmentAddTemplateFragmentBinding
    private val viewModelss by viewModels<TempViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTemplateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedTemplateId = arguments?.getString("id")
        Log.d(
            "AddTemplateFragmentFragment",
            "Selected Template Id: $selectedTemplateId"
        ) // Добавьте этот лог
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


        binding.imgAdd.setOnClickListener {
            val id = System.currentTimeMillis().toString()
            val textDesc = binding.etWork.text.toString()
            val textNotes = binding.etText.text.toString()

            if (textDesc.isNotEmpty() && textNotes.isNotEmpty() && selectedTemplateId != null) {
                val newNote = model(
                    id,
                    day,
                    month,
                    year,
                    time,
                    textDesc,
                    textNotes,
                    false,
                    selectedTemplateId
                )
                viewModelss.addNotesTemp(id, newNote)

                // После добавления заметки переключитесь на фрагмент TemplateNoteFragment
                val templateNoteFragment = TemplateNoteFragment()
                val bundle = Bundle()
                bundle.putString("id", selectedTemplateId)
                templateNoteFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fr_container, templateNoteFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Заполните все поля и выберите шаблон",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}