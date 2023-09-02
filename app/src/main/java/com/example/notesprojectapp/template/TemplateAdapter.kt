package com.example.notesprojectapp.template

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.TemplateNotesModel
import com.example.notesprojectapp.databinding.ItemTemplateBinding
import java.util.*

class TemplateAdapter(val onClick:(TemplateNotesModel)->Unit):ListAdapter<TemplateNotesModel, TemplateAdapter.TemplatesViewHolder>(TemplatesDiff()) {


    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    inner class TemplatesViewHolder(val binding: ItemTemplateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TemplateNotesModel) {
            val currentDate = Calendar.getInstance().time
            val formattedDate = dateFormat.format(currentDate)
            binding.tvDate.text = formattedDate
            binding.tvPlaner.text = model.workList

            itemView.setOnClickListener {
                onClick(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplatesViewHolder {
        return TemplatesViewHolder(ItemTemplateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TemplatesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TemplatesDiff:DiffUtil.ItemCallback<TemplateNotesModel>() {
    override fun areItemsTheSame(
        oldItem: TemplateNotesModel,
        newItem: TemplateNotesModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TemplateNotesModel,
        newItem: TemplateNotesModel
    ): Boolean {
        return oldItem == newItem
    }

}
