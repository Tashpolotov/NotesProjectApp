package com.example.notesprojectapp.fragments.homefragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.notesprojectapp.R
import com.example.notesprojectapp.databinding.FragmentHomeBinding
import com.example.notesprojectapp.viewmodel.NotesViewModel
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.domain.model.HomeNotesModel
import com.example.notesprojectapp.fragments.greetingnote.GreetNoteFragment
import com.example.notesprojectapp.fragments.settingfragment.SettingFragment
import com.example.notesprojectapp.template.TemplateFragment
import com.example.notesprojectapp.util.AppPreferences
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = HomeNotesListAdapter(this::onCLick)
    private val viewModel by viewModels<NotesViewModel>()
    private var showOnlyImportant = false
    private var currentFilter = FILTER_ALL
    private var selectedTextView: TextView? = null
    private var isMenuOpen = false
    private val appPreferences by lazy { AppPreferences(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setSelectedTextView(binding.tvAllNotes)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateNotesList()
        initAdapter()
        initList()
        imitMenu()
        initSearch()

    }

    private fun initSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ничего не делаем перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Ничего не делаем при изменении текста
            }

            override fun afterTextChanged(s: Editable?) {
                // Проверяем, является ли текст пустым
                if (s.isNullOrEmpty()) {
                    // Текстовое поле пустое, восстанавливаем все заметки
                    viewModel.refreshNotes(id = String())
                } else {
                    // Текст введен, выполняем поиск
                    viewModel.searchHome(query = s.toString())
                }
            }
        })
    }



    private fun onCLick(model: HomeNotesModel) {

        val fragment = EditNoteHomeFragment()
        val bundle = Bundle()
        bundle.putString("noteId", model.id)
        bundle.putString("title", model.workList)
        bundle.putString("content", model.textNotes)
        fragment.arguments = bundle
        fragment.setTargetFragment(this, 0)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fr_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun imitMenu() {
        binding.imgMenu.setOnClickListener {
            if (isMenuOpen) {
                closeMenu()
            } else {
                openMenu()
            }
        }

        val navView = binding.navView
        val rootView = binding.root

        rootView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (navView.isShown && event.x > navView.right) {
                    closeMenu()
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.setiing -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fr_container, SettingFragment())
                        .addToBackStack(null)
                        .commit()
                    closeMenu()
                    true
                }
                R.id.Notes -> {
                    try {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fr_container, TemplateFragment())
                            .addToBackStack(null)
                            .commit()
                        closeMenu()
                        true
                    } catch (e: Exception) {
                        Log.e("NavigationError", "Error navigating to creatNewFragment", e)
                        false
                    }
                }
                else -> false
            }
        }
    }

    private fun openMenu() {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in)
        binding.navView.startAnimation(animation)
        binding.navView.visibility = View.VISIBLE
        isMenuOpen = true
    }

    private fun closeMenu() {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out)
        binding.navView.startAnimation(animation)
        binding.navView.visibility = View.INVISIBLE
        isMenuOpen = false

    }

    private fun initList() {
        binding.btnPlus.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fr_container, GreetNoteFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.tvPinned.setOnClickListener {
            setSelectedTextView(binding.tvPinned)
            currentFilter = FILTER_PINNED
            updateNotesList()
        }

        binding.tvAllNotes.setOnClickListener {
            setSelectedTextView(binding.tvAllNotes)
            currentFilter = FILTER_ALL
            updateNotesList()
        }

        binding.tvToDo.setOnClickListener {
            setSelectedTextView(binding.tvToDo)
            currentFilter = FILTER_TODO
            toggleShowOnlyImportant()
        }
    }

    private fun initAdapter() {
        binding.rv.adapter = adapter
        val spancount = 2
        binding.rv.layoutManager = StaggeredGridLayoutManager(spancount, StaggeredGridLayoutManager.VERTICAL)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.notesHome.collect {
                adapter.submitList(it.homeNotes)
            }
        }
    }

    private fun setSelectedTextView(textView: TextView) {
        selectedTextView?.let { previousTextView ->
            previousTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
        }

        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.buttobColors))
        selectedTextView = textView
    }

    private fun updateNotesList() {
        val allNotes = viewModel.notesHome.value.homeNotes
        val filteredNotes = when (currentFilter) {
            FILTER_ALL -> allNotes
            FILTER_PINNED -> allNotes.filter { it.isImportant }
            else -> emptyList()
        }
        adapter.submitList(filteredNotes)
        Log.d("alala", "Updated notes list with ${filteredNotes.size} items")
    }

    private fun toggleShowOnlyImportant() {
        showOnlyImportant = !showOnlyImportant
        updateNotesList()
    }

    override fun onResume() {
        super.onResume()
        val userId = "your_user_id_here"
        viewModel.refreshNotes(userId)
    }

    companion object {
        const val FILTER_ALL = "All"
        const val FILTER_PINNED = "Pinned"
        const val FILTER_TODO = "ToDo"
    }
}