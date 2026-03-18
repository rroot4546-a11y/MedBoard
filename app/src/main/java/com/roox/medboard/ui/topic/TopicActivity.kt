package com.roox.medboard.ui.topic

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.roox.medboard.MedBoardApp
import com.roox.medboard.R
import com.roox.medboard.data.model.Bookmark
import com.roox.medboard.data.model.CachedTopic
import com.roox.medboard.data.model.ReadingProgress
import com.roox.medboard.data.model.Section
import com.roox.medboard.data.model.TopicContent
import com.roox.medboard.service.ContentService
import io.noties.markwon.Markwon
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.html.HtmlPlugin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopicActivity : AppCompatActivity() {
    private lateinit var markwon: Markwon
    private lateinit var dao: com.roox.medboard.data.dao.MedDao
    private val gson = Gson()
    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        val topicId = intent.getStringExtra("topic_id") ?: return finish()
        val topicTitle = intent.getStringExtra("topic_title") ?: "Topic"
        val systemId = intent.getStringExtra("system_id") ?: ""
        val systemName = intent.getStringExtra("system_name") ?: ""

        markwon = Markwon.builder(this)
            .usePlugin(TablePlugin.create(this))
            .usePlugin(HtmlPlugin.create())
            .build()

        dao = (application as MedBoardApp).dao

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<TextView>(R.id.tvTitle).text = topicTitle

        val btnBookmark = findViewById<ImageButton>(R.id.btnBookmark)
        lifecycleScope.launch {
            isBookmarked = withContext(Dispatchers.IO) { dao.isBookmarked(topicId) > 0 }
            btnBookmark.setImageResource(if (isBookmarked) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off)
        }
        btnBookmark.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                if (isBookmarked) {
                    dao.removeBookmark(Bookmark(topicId, systemId, topicTitle))
                } else {
                    dao.addBookmark(Bookmark(topicId, systemId, topicTitle))
                }
                isBookmarked = !isBookmarked
                withContext(Dispatchers.Main) {
                    btnBookmark.setImageResource(if (isBookmarked) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off)
                    Toast.makeText(this@TopicActivity, if (isBookmarked) "⭐ Bookmarked!" else "Removed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loadContent(topicId, topicTitle, systemId, systemName)
    }

    private fun loadContent(topicId: String, topicTitle: String, systemId: String, systemName: String) {
        val loading = findViewById<View>(R.id.layoutLoading)
        val scroll = findViewById<ScrollView>(R.id.scrollContent)
        val sections = findViewById<LinearLayout>(R.id.layoutSections)

        loading.visibility = View.VISIBLE
        scroll.visibility = View.GONE

        lifecycleScope.launch {
            // Check cache first
            val cached = withContext(Dispatchers.IO) { dao.getCachedTopic(topicId) }
            if (cached != null) {
                val content = try { gson.fromJson(cached.contentJson, TopicContent::class.java) } catch (_: Exception) { null }
                if (content != null) {
                    loading.visibility = View.GONE
                    scroll.visibility = View.VISIBLE
                    renderSections(sections, content.sections)
                    return@launch
                }
            }

            // Generate with AI
            val prefs = getSharedPreferences("medboard_prefs", MODE_PRIVATE)
            val svc = ContentService.fromPrefs(prefs)
            val content = withContext(Dispatchers.IO) { svc.generateTopicContent(systemName, topicTitle, "") }
            val fullContent = content.copy(id = topicId, systemId = systemId)

            // Cache it
            withContext(Dispatchers.IO) {
                dao.cacheTopic(CachedTopic(topicId, systemId, topicTitle, gson.toJson(fullContent)))
                dao.saveProgress(ReadingProgress(topicId, systemId))
            }

            loading.visibility = View.GONE
            scroll.visibility = View.VISIBLE
            renderSections(sections, fullContent.sections)
        }
    }

    private fun renderSections(container: LinearLayout, sectionList: List<Section>) {
        container.removeAllViews()
        for (section in sectionList) {
            // Section title
            val titleView = TextView(this).apply {
                text = when (section.type) {
                    "board_note" -> "📝 ${section.title}"
                    "update" -> "🆕 ${section.title}"
                    "reference" -> "📚 ${section.title}"
                    else -> section.title
                }
                textSize = 18f
                setTypeface(typeface, android.graphics.Typeface.BOLD)
                setTextColor(resources.getColor(when (section.type) {
                    "board_note" -> R.color.accent
                    "update" -> R.color.sys_id
                    "reference" -> R.color.primary
                    else -> R.color.primary_dark
                }, null))
                setPadding(0, 24, 0, 8)
            }
            container.addView(titleView)

            // Section card
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(20, 16, 20, 16)
                val bgRes = when (section.type) {
                    "board_note" -> R.drawable.bg_board_note
                    "update" -> R.drawable.bg_update_note
                    else -> R.drawable.bg_system_card
                }
                setBackgroundResource(bgRes)
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                lp.bottomMargin = 12
                layoutParams = lp
                elevation = if (section.type == "text") 4f else 2f
            }

            val contentView = TextView(this).apply {
                textSize = 14.5f
                setTextColor(resources.getColor(R.color.text_primary, null))
                setLineSpacing(0f, 1.35f)
            }
            markwon.setMarkdown(contentView, section.content)
            card.addView(contentView)
            container.addView(card)
        }

        // Disclaimer
        val disclaimer = TextView(this).apply {
            text = "⚠️ AI-generated content based on Harrison's 21st Ed & Davidson's 25th Ed. Always verify with the original textbook."
            textSize = 11f
            setTextColor(resources.getColor(R.color.text_light, null))
            setPadding(0, 32, 0, 48)
            gravity = android.view.Gravity.CENTER
        }
        container.addView(disclaimer)
    }
}
