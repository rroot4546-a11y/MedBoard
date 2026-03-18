package com.roox.medboard.ui.topic

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roox.medboard.R
import com.roox.medboard.data.model.MedSystem
import com.roox.medboard.data.model.TopicSummary
import com.roox.medboard.service.MedicalCatalog

class SystemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system)

        val systemId = intent.getStringExtra("system_id") ?: return finish()
        val system = MedicalCatalog.getSystem(systemId) ?: return finish()

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<TextView>(R.id.tvIcon).text = system.icon
        findViewById<TextView>(R.id.tvTitle).text = system.name
        findViewById<TextView>(R.id.tvSubtitle).text = "${system.topicCount} topics • Harrison's & Davidson's"

        val rv = findViewById<RecyclerView>(R.id.rvTopics)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = TopicAdapter(system, system.topics) { topic ->
            startActivity(Intent(this, TopicActivity::class.java)
                .putExtra("system_id", system.id)
                .putExtra("topic_id", topic.id)
                .putExtra("topic_title", topic.title)
                .putExtra("system_name", system.name))
        }
    }

    class TopicAdapter(private val sys: MedSystem, private val topics: List<TopicSummary>, private val onClick: (TopicSummary) -> Unit)
        : RecyclerView.Adapter<TopicAdapter.VH>() {
        inner class VH(v: View) : RecyclerView.ViewHolder(v) {
            val tvTitle: TextView = v.findViewById(R.id.tvTitle)
            val tvSubtitle: TextView = v.findViewById(R.id.tvSubtitle)
            val colorBar: View = v.findViewById(R.id.colorBar)
        }
        override fun onCreateViewHolder(p: ViewGroup, vt: Int) = VH(LayoutInflater.from(p.context).inflate(R.layout.item_topic, p, false))
        override fun getItemCount() = topics.size
        override fun onBindViewHolder(h: VH, pos: Int) {
            val t = topics.get(pos)
            h.tvTitle.text = t.title
            h.tvSubtitle.text = t.subtitle
            try { h.colorBar.setBackgroundColor(Color.parseColor(sys.color)) } catch (_: Exception) {}
            h.itemView.setOnClickListener { onClick(t) }
        }
    }
}
