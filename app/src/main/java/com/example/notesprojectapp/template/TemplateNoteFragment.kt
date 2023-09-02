package com.example.notesprojectapp.template

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.domain.model.HomeNotesModel
import com.example.domain.model.model
import com.example.notesprojectapp.R
import com.example.notesprojectapp.databinding.FragmentHomeBinding
import com.example.notesprojectapp.databinding.FragmentTemplateNoteBinding
import com.example.notesprojectapp.fragments.greetingnote.GreetNoteFragment
import com.example.notesprojectapp.fragments.homefragment.EditNoteHomeFragment
import com.example.notesprojectapp.fragments.homefragment.HomeNotesListAdapter
import com.example.notesprojectapp.fragments.settingfragment.SettingFragment
import com.example.notesprojectapp.viewmodel.NotesViewModel
import com.example.notesprojectapp.viewmodel.TempViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplateNoteFragment : Fragment() {

    private lateinit var binding:FragmentTemplateNoteBinding
    private val adapter = TempNoteAdapter(this::onCLick)
    private val viewModel by viewModels<TempViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTemplateNoteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedTemplateId = arguments?.getString("id")
        initAdapter()
        if (selectedTemplateId != null) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.tempsHome.collect { templates ->
                    val filteredNotes = templates.tempNotes.filter { it.templateId == selectedTemplateId }
                    adapter.submitList(filteredNotes)
                    Log.d("TemplateNoteFragment", "Filtered Notes: ${filteredNotes.size}") // Добавьте этот лог
                }
            }
        } else {
            Log.d("TemplateNoteFragment", "selectedTemplateId is null")
            // Если selectedTemplateId не установлен, можно обработать этот случай, например, отображать сообщение о том, что выбранного шаблона нет.
        }


        binding.imgAdd.setOnClickListener {
            val bundle = Bundle()

            bundle.putString("id", selectedTemplateId)
            val addTemplateFragment = AddTemplateFragmentFragment()
            addTemplateFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fr_container, addTemplateFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fr_container, TemplateFragment())
                .addToBackStack(null)
                .commit()
        }

    }

    private fun onCLick(model: model) {
        val fragment = EditTemplateFragment()
        val bundle = Bundle()
        bundle.putString("noteId", model.id)
        bundle.putString("content", model.textNotes)
        bundle.putString("title", model.textDesc)

        fragment.arguments = bundle
        fragment.setTargetFragment(this, 0)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fr_container, fragment)
            .addToBackStack(null)
            .commit()

        }


    private fun initAdapter() {
        binding.rv.adapter = adapter
        val spancount = 2
        binding.rv.layoutManager = StaggeredGridLayoutManager(spancount, StaggeredGridLayoutManager.VERTICAL)

    }


    override fun onResume() {
        super.onResume()
        val userId = "your_user_id_here"
        viewModel.refreshNoteTemp(userId)
    }
}
