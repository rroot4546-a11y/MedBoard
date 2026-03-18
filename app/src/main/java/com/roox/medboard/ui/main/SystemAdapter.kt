package com.roox.medboard.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roox.medboard.databinding.ItemSystemCardBinding

data class SystemDisplayItem(
    val systemId: String,
    val icon: String,
    val topicCount: Int
)

class SystemAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<SystemDisplayItem, SystemAdapter.ViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<SystemDisplayItem>() {
            override fun areItemsTheSame(old: SystemDisplayItem, new: SystemDisplayItem) = old.systemId == new.systemId
            override fun areContentsTheSame(old: SystemDisplayItem, new: SystemDisplayItem) = old == new
        }

        val SYSTEM_ICONS = mapOf(
            "Cardiology" to "❤️",
            "Respiratory" to "🫁",
            "GI" to "🫃",
            "Gastroenterology" to "🫃",
            "Nephrology" to "💧",
            "Endocrinology" to "🦋",
            "Rheumatology" to "🦴",
            "Hematology" to "🩸",
            "Infectious Disease" to "🦠",
            "Neurology" to "🧠",
            "Oncology" to "🎗️",
            "Dermatology" to "🩹",
            "Critical Care" to "🏥",
            "Geriatrics" to "👴",
            "General Internal Medicine" to "⚕️"
        )
    }

    inner class ViewHolder(val binding: ItemSystemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val item = getItem(adapterPosition)
                onClick(item.systemId)
            }
        }

        fun bind(item: SystemDisplayItem) {
            binding.tvSystemIcon.text = item.icon
            binding.tvSystemName.text = item.systemId
            binding.tvTopicCount.text = "${item.topicCount} topics"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSystemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
