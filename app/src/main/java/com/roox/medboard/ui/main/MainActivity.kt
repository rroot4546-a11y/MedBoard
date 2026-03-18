package com.roox.medboard.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roox.medboard.R
import com.roox.medboard.data.model.MedSystem
import com.roox.medboard.data.model.TopicSummary
import com.roox.medboard.service.MedicalCatalog
import com.roox.medboard.ui.bookmarks.BookmarksActivity
import com.roox.medboard.ui.settings.SettingsActivity
import com.roox.medboard.ui.topic.SystemActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvSystems = findViewById<RecyclerView>(R.id.rvSystems)
        val rvSearch = findViewById<RecyclerView>(R.id.rvSearch)
        val etSearch = findViewById<EditText>(R.id.etSearch)
        val tvStats = findViewById<TextView>(R.id.tvStats)

        tvStats.text = "${MedicalCatalog.systems.size} systems • ${MedicalCatalog.totalTopics()} topics"

        // Systems list
        val sysAdapter = SystemAdapter(MedicalCatalog.systems) { sys ->
            startActivity(Intent(this, SystemActivity::class.java).putExtra("system_id", sys.id))
        }
        rvSystems.layoutManager = LinearLayoutManager(this)
        rvSystems.adapter = sysAdapter

        // Search results
        val searchAdapter = SearchAdapter { sys, topic ->
            startActivity(Intent(this, com.roox.medboard.ui.topic.TopicActivity::class.java)
                .putExtra("system_id", sys.id).putExtra("topic_id", topic.id)
                .putExtra("topic_title", topic.title).putExtra("system_name", sys.name))
        }
        rvSearch.layoutManager = LinearLayoutManager(this)
        rvSearch.adapter = searchAdapter

        // Search
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val q = s?.toString()?.trim() ?: ""
                if (q.length >= 2) {
                    val results = MedicalCatalog.search(q)
                    searchAdapter.update(results)
                    rvSearch.visibility = View.VISIBLE
                    rvSystems.visibility = View.GONE
                } else {
                    rvSearch.visibility = View.GONE
                    rvSystems.visibility = View.VISIBLE
                }
            }
        })

        findViewById<ImageButton>(R.id.btnBookmarks).setOnClickListener { startActivity(Intent(this, BookmarksActivity::class.java)) }
        findViewById<ImageButton>(R.id.btnSettings).setOnClickListener { startActivity(Intent(this, SettingsActivity::class.java)) }
    }

    // --- Adapters ---

    class SystemAdapter(private val systems: List<MedSystem>, private val onClick: (MedSystem) -> Unit)
        : RecyclerView.Adapter<SystemAdapter.VH>() {
        inner class VH(v: View) : RecyclerView.ViewHolder(v) {
            val tvIcon: TextView = v.findViewById(R.id.tvIcon)
            val tvName: TextView = v.findViewById(R.id.tvName)
            val tvCount: TextView = v.findViewById(R.id.tvCount)
            val tvProgress: TextView = v.findViewById(R.id.tvProgress)
        }
        override fun onCreateViewHolder(p: ViewGroup, vt: Int) = VH(LayoutInflater.from(p.context).inflate(R.layout.item_system, p, false))
        override fun getItemCount() = systems.size
        override fun onBindViewHolder(h: VH, pos: Int) {
            val s = systems.get(pos)
            h.tvIcon.text = s.icon
            h.tvName.text = s.name
            h.tvCount.text = "${s.topicCount} topics"
            h.tvName.setTextColor(Color.parseColor(s.color))
            h.itemView.setOnClickListener { onClick(s) }
        }
    }

    class SearchAdapter(private val onClick: (MedSystem, TopicSummary) -> Unit)
        : RecyclerView.Adapter<SearchAdapter.VH>() {
        private var items = listOf<Pair<MedSystem, TopicSummary>>()
        fun update(list: List<Pair<MedSystem, TopicSummary>>) { items = list; notifyDataSetChanged() }
        inner class VH(v: View) : RecyclerView.ViewHolder(v) {
            val tvIcon: TextView = v.findViewById(R.id.tvSysIcon)
            val tvTitle: TextView = v.findViewById(R.id.tvTitle)
            val tvSystem: TextView = v.findViewById(R.id.tvSystem)
        }
        override fun onCreateViewHolder(p: ViewGroup, vt: Int) = VH(LayoutInflater.from(p.context).inflate(R.layout.item_search_result, p, false))
        override fun getItemCount() = items.size
        override fun onBindViewHolder(h: VH, pos: Int) {
            val (sys, topic) = items.get(pos)
            h.tvIcon.text = sys.icon
            h.tvTitle.text = topic.title
            h.tvSystem.text = "${sys.name} • ${topic.subtitle}"
            h.itemView.setOnClickListener { onClick(sys, topic) }
        }
    }
}
