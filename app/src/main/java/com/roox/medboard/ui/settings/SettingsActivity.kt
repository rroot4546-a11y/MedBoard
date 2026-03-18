package com.roox.medboard.ui.settings

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.roox.medboard.MedBoardApp
import com.roox.medboard.R
import com.roox.medboard.service.ContentService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsActivity : AppCompatActivity() {

    private val models = listOf(
        "google/gemini-2.0-flash-001" to "Gemini 2.0 Flash (Free ⭐)",
        "google/gemini-2.5-pro-preview" to "Gemini 2.5 Pro (Best Quality)",
        "openai/gpt-4o" to "GPT-4o",
        "anthropic/claude-sonnet-4" to "Claude Sonnet 4",
        "deepseek/deepseek-chat-v3-0324:free" to "DeepSeek V3 (Free)",
        "meta-llama/llama-4-maverick" to "Llama 4 Maverick"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val prefs = getSharedPreferences("medboard_prefs", MODE_PRIVATE)
        val etKey = findViewById<EditText>(R.id.etApiKey)
        val spinner = findViewById<Spinner>(R.id.spinnerModel)

        // Load saved
        etKey.setText(prefs.getString("api_key", ""))
        val savedModel = prefs.getString("model", models.get(0).first) ?: models.get(0).first

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, models.map { it.second })
        spinner.adapter = adapter
        val idx = models.indexOfFirst { it.first == savedModel }
        if (idx >= 0) spinner.setSelection(idx)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            prefs.edit()
                .putString("api_key", etKey.text.toString().trim())
                .putString("model", models.get(spinner.selectedItemPosition).first)
                .apply()
            Toast.makeText(this, "✅ Saved!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnTest).setOnClickListener {
            val key = etKey.text.toString().trim()
            val model = models.get(spinner.selectedItemPosition).first
            if (key.isBlank()) { Toast.makeText(this, "Enter API key first", Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            Toast.makeText(this, "Testing...", Toast.LENGTH_SHORT).show()
            lifecycleScope.launch {
                val svc = ContentService(key, model)
                val ok = withContext(Dispatchers.IO) {
                    try {
                        val r = svc.generateTopicContent("Test", "Test", "Test")
                        r.sections.isNotEmpty()
                    } catch (_: Exception) { false }
                }
                Toast.makeText(this@SettingsActivity, if (ok) "✅ Connection works!" else "❌ Failed", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnClearCache).setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                (application as MedBoardApp).dao.clearCache()
                withContext(Dispatchers.Main) { Toast.makeText(this@SettingsActivity, "🗑️ Cache cleared", Toast.LENGTH_SHORT).show() }
            }
        }
    }
}
