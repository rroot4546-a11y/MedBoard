package com.roox.medboard.ui.bookmarks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roox.medboard.data.TopicEntity
import com.roox.medboard.databinding.ItemTopicGridBinding
import com.roox.medboard.ui.main.SystemAdapter

class BookmarkAdapter(
    private val onClick: (TopicEntity) -> Unit
) : ListAdapter<TopicEntity, BookmarkAdapter.ViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<TopicEntity>() {
            override fun areItemsTheSame(old: TopicEntity, new: TopicEntity) = old.id == new.id
            override fun areContentsTheSame(old: TopicEntity, new: TopicEntity) = old == new
        }
    }

    inner class ViewHolder(val binding: ItemTopicGridBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }

        fun bind(topic: TopicEntity) {
            binding.tvTopicTitle.text = topic.title
            binding.tvSystemName.text = topic.systemId
            binding.tvSystemIcon.text = SystemAdapter.SYSTEM_ICONS[topic.systemId] ?: "⚕️"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTopicGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
