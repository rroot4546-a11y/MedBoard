package com.roox.medboard.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.roox.medboard.databinding.ActivitySearchBinding
import com.roox.medboard.ui.detail.TopicDetailActivity
import com.roox.medboard.viewmodel.MainViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        searchAdapter = SearchAdapter { topic ->
            val intent = Intent(this, TopicDetailActivity::class.java)
            intent.putExtra("TOPIC_ID", topic.id)
            startActivity(intent)
        }

        binding.rvResults.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchQuery(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.searchResults.observe(this) { results ->
            searchAdapter.submitList(results)
            binding.tvNoResults.visibility = if (results.isEmpty() && binding.etSearch.text.isNotEmpty()) View.VISIBLE else View.GONE
            binding.rvResults.visibility = if (results.isNotEmpty()) View.VISIBLE else View.GONE
        }

        // Focus and show keyboard
        binding.etSearch.requestFocus()

        // Handle high yield filter from intent
        if (intent.getBooleanExtra("HIGH_YIELD_ONLY", false)) {
            binding.etSearch.setText("board")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
