package com.roox.medboard.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roox.medboard.MedBoardApp
import com.roox.medboard.R
import com.roox.medboard.data.model.Bookmark
import com.roox.medboard.service.MedicalCatalog
import com.roox.medboard.ui.topic.TopicActivity

class BookmarksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        val rv = findViewById<RecyclerView>(R.id.rvBookmarks)
        val tvEmpty = findViewById<TextView>(R.id.tvEmpty)
        rv.layoutManager = LinearLayoutManager(this)

        val dao = (application as MedBoardApp).dao
        dao.allBookmarks().observe(this) { bookmarks ->
            tvEmpty.visibility = if (bookmarks.isEmpty()) View.VISIBLE else View.GONE
            rv.visibility = if (bookmarks.isEmpty()) View.GONE else View.VISIBLE
            rv.adapter = BmAdapter(bookmarks) { bm ->
                val sys = MedicalCatalog.getSystem(bm.systemId)
                startActivity(Intent(this, TopicActivity::class.java)
                    .putExtra("system_id", bm.systemId)
                    .putExtra("topic_id", bm.topicId)
                    .putExtra("topic_title", bm.title)
                    .putExtra("system_name", sys?.name ?: ""))
            }
        }
    }

    class BmAdapter(private val items: List<Bookmark>, private val onClick: (Bookmark) -> Unit) : RecyclerView.Adapter<BmAdapter.VH>() {
        inner class VH(v: View) : RecyclerView.ViewHolder(v) {
            val tvIcon: TextView = v.findViewById(R.id.tvSysIcon)
            val tvTitle: TextView = v.findViewById(R.id.tvTitle)
            val tvSystem: TextView = v.findViewById(R.id.tvSystem)
        }
        override fun onCreateViewHolder(p: ViewGroup, vt: Int) = VH(LayoutInflater.from(p.context).inflate(R.layout.item_search_result, p, false))
        override fun getItemCount() = items.size
        override fun onBindViewHolder(h: VH, pos: Int) {
            val bm = items.get(pos)
            val sys = MedicalCatalog.getSystem(bm.systemId)
            h.tvIcon.text = sys?.icon ?: "📖"
            h.tvTitle.text = bm.title
            h.tvSystem.text = sys?.name ?: bm.systemId
            h.itemView.setOnClickListener { onClick(bm) }
        }
    }
}
