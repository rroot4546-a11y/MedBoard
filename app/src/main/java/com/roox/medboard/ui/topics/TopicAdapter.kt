package com.roox.medboard.ui.topics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.roox.medboard.data.TopicEntity
import com.roox.medboard.databinding.ItemTopicBinding

class TopicAdapter(
    private val onClick: (TopicEntity) -> Unit,
    private val onBookmark: (TopicEntity) -> Unit
) : ListAdapter<TopicEntity, TopicAdapter.ViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<TopicEntity>() {
            override fun areItemsTheSame(old: TopicEntity, new: TopicEntity) = old.id == new.id
            override fun areContentsTheSame(old: TopicEntity, new: TopicEntity) = old == new
        }
    }

    inner class ViewHolder(val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
            binding.ivBookmark.setOnClickListener {
                onBookmark(getItem(adapterPosition))
            }
        }

        fun bind(topic: TopicEntity) {
            binding.tvTopicTitle.text = topic.title
            binding.tvSystemName.text = topic.systemId
            binding.ivBookmark.setImageResource(
                if (topic.isBookmarked) android.R.drawable.btn_star_big_on
                else android.R.drawable.btn_star_big_off
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
