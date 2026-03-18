package com.roox.medboard.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.roox.medboard.R
import com.roox.medboard.databinding.ActivityMainBinding
import com.roox.medboard.ui.bookmarks.BookmarksActivity
import com.roox.medboard.ui.search.SearchActivity
import com.roox.medboard.ui.topics.SystemTopicsActivity
import com.roox.medboard.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var systemAdapter: SystemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
        setupObservers()
        setupSearch()
        setupBottomNav()
        setupChips()
    }

    private fun setupRecyclerView() {
        systemAdapter = SystemAdapter { systemId ->
            val intent = Intent(this, SystemTopicsActivity::class.java)
            intent.putExtra("SYSTEM_ID", systemId)
            startActivity(intent)
        }
        binding.rvSystems.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = systemAdapter
        }
    }

    private fun setupObservers() {
        viewModel.allTopics.observe(this) { topics ->
            val systemCounts = topics.groupBy { it.systemId }
                .map { (systemId, list) ->
                    SystemDisplayItem(
                        systemId = systemId,
                        icon = SystemAdapter.SYSTEM_ICONS[systemId] ?: "⚕️",
                        topicCount = list.size
                    )
                }
                .sortedBy { it.systemId }
            systemAdapter.submitList(systemCounts)
        }
    }

    private fun setupSearch() {
        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                startActivity(Intent(this, SearchActivity::class.java))
                binding.etSearch.clearFocus()
            }
        }
        binding.etSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun setupBottomNav() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.nav_bookmarks -> {
                    startActivity(Intent(this, BookmarksActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupChips() {
        binding.chipHighYield.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("HIGH_YIELD_ONLY", true)
            startActivity(intent)
        }
    }
}
