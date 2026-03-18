package com.roox.medboard.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.roox.medboard.R
import com.roox.medboard.databinding.ActivityTopicDetailBinding
import com.roox.medboard.viewmodel.DetailViewModel
import io.noties.markwon.Markwon
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin

class TopicDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTopicDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var sectionAdapter: SectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopicDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val topicId = intent.getStringExtra("TOPIC_ID") ?: return finish()

        val markwon = Markwon.builder(this)
            .usePlugin(TablePlugin.create(this))
            .usePlugin(StrikethroughPlugin.create())
            .build()
        sectionAdapter = SectionAdapter(markwon)

        binding.rvSections.apply {
            layoutManager = LinearLayoutManager(this@TopicDetailActivity)
            adapter = sectionAdapter
        }

        viewModel.topic.observe(this) { topic ->
            topic ?: return@observe
            title = topic.title
            binding.fabBookmark.setImageResource(
                if (topic.isBookmarked) android.R.drawable.btn_star_big_on
                else android.R.drawable.btn_star_big_off
            )
        }

        viewModel.sections.observe(this) { sections ->
            sectionAdapter.setSections(sections)
        }

        binding.fabBookmark.setOnClickListener {
            viewModel.toggleBookmark()
        }

        viewModel.loadTopic(topicId)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_share -> {
                val topic = viewModel.topic.value ?: return true
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, topic.title)
                    putExtra(Intent.EXTRA_TEXT, "MedBoard: ${topic.title}\n${topic.systemId}")
                }
                startActivity(Intent.createChooser(shareIntent, "Share topic"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
