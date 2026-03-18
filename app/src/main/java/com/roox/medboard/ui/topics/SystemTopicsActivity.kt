package com.roox.medboard.ui.topics

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.roox.medboard.databinding.ActivitySystemTopicsBinding
import com.roox.medboard.ui.detail.TopicDetailActivity
import com.roox.medboard.viewmodel.TopicsViewModel

class SystemTopicsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySystemTopicsBinding
    private val viewModel: TopicsViewModel by viewModels()
    private lateinit var topicAdapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemTopicsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val systemId = intent.getStringExtra("SYSTEM_ID") ?: return finish()
        title = systemId
        viewModel.setSystemId(systemId)

        topicAdapter = TopicAdapter(
            onClick = { topic ->
                val intent = Intent(this, TopicDetailActivity::class.java)
                intent.putExtra("TOPIC_ID", topic.id)
                startActivity(intent)
            },
            onBookmark = { topic ->
                viewModel.toggleBookmark(topic)
            }
        )

        binding.rvTopics.apply {
            layoutManager = LinearLayoutManager(this@SystemTopicsActivity)
            adapter = topicAdapter
        }

        viewModel.topics.observe(this) { topics ->
            topicAdapter.submitList(topics)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
