package com.roox.medboard.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.roox.medboard.databinding.ActivityBookmarksBinding
import com.roox.medboard.ui.detail.TopicDetailActivity
import com.roox.medboard.viewmodel.BookmarksViewModel

class BookmarksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarksBinding
    private val viewModel: BookmarksViewModel by viewModels()
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bookmarkAdapter = BookmarkAdapter { topic ->
            val intent = Intent(this, TopicDetailActivity::class.java)
            intent.putExtra("TOPIC_ID", topic.id)
            startActivity(intent)
        }

        binding.rvBookmarks.apply {
            layoutManager = GridLayoutManager(this@BookmarksActivity, 2)
            adapter = bookmarkAdapter
        }

        viewModel.bookmarks.observe(this) { bookmarks ->
            bookmarkAdapter.submitList(bookmarks)
            binding.tvEmpty.visibility = if (bookmarks.isEmpty()) View.VISIBLE else View.GONE
            binding.rvBookmarks.visibility = if (bookmarks.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
