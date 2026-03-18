package com.roox.medboard.service

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.roox.medboard.data.model.Section
import com.roox.medboard.data.model.TopicContent
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContentService(private val apiKey: String, private val model: String) {
    companion object {
        private const val URL = "https://openrouter.ai/api/v1/chat/completions"
        fun fromPrefs(p: android.content.SharedPreferences): ContentService {
            return ContentService(
                p.getString("api_key", "") ?: "",
                p.getString("model", "google/gemini-2.0-flash-001") ?: "google/gemini-2.0-flash-001"
            )
        }
    }

    private val http = OkHttpClient.Builder()
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS).build()
    private val gson = Gson()

    suspend fun generateTopicContent(systemName: String, topicTitle: String, topicSubtitle: String): TopicContent {
        val systemPrompt = buildString {
            append("You are a senior Internal Medicine professor writing a comprehensive chapter for Iraqi Board Internal Medicine residents.\n\n")
            append("PRIMARY SOURCES (use ONLY these, cite page/chapter numbers):\n")
            append("- Harrison's Principles of Internal Medicine, 21st Edition (2022)\n")
            append("- Davidson's Principles and Practice of Medicine, 24th Edition (2023)\n\n")
            append("RULES:\n")
            append("1. Write in clear, structured English with medical precision\n")
            append("2. Use markdown formatting: **bold** for key terms, bullet lists, tables\n")
            append("3. Include specific numbers (sensitivity %, doses in mg, criteria cutoffs)\n")
            append("4. After every major fact, add (Harrison Ch.XX) or (Davidson Ch.XX)\n")
            append("5. Focus on what's HIGH-YIELD for Board exams\n")
            append("6. Include clinical pearls and diagnostic algorithms\n")
            append("7. Be comprehensive but organized — no fluff\n")
        }

        val userPrompt = buildString {
            append("Write a complete chapter on: **$topicTitle** ($topicSubtitle)\n")
            append("System: $systemName\n\n")
            append("Structure the content with these EXACT sections (use === as separator):\n\n")
            append("=== DEFINITION & EPIDEMIOLOGY ===\n")
            append("Clear definition, prevalence, incidence, risk factors\n\n")
            append("=== PATHOPHYSIOLOGY ===\n")
            append("Mechanism of disease, key pathways\n\n")
            append("=== CLASSIFICATION ===\n")
            append("Types, staging, grading systems with criteria\n\n")
            append("=== CLINICAL FEATURES ===\n")
            append("Symptoms, signs, physical examination findings. Use tables for DDx\n\n")
            append("=== DIAGNOSIS ===\n")
            append("Diagnostic criteria, investigations (labs, imaging, special tests). Sensitivity/specificity of key tests\n\n")
            append("=== MANAGEMENT ===\n")
            append("Step-by-step treatment. Drug names, doses, duration. Guidelines-based.\n\n")
            append("=== COMPLICATIONS ===\n")
            append("What can go wrong, monitoring, prevention\n\n")
            append("=== 📝 BOARD HIGH-YIELD NOTES ===\n")
            append("- Most commonly tested facts\n")
            append("- Classic board question patterns\n")
            append("- Tricky differentials\n")
            append("- \"Must-know\" numbers and criteria\n")
            append("- Common exam traps and how to avoid them\n\n")
            append("=== 🆕 RECENT UPDATES (2023-2026) ===\n")
            append("- New guidelines or recommendations\n")
            append("- New drugs or therapies approved\n")
            append("- Major trials that changed practice\n")
            append("- Emerging concepts\n\n")
            append("=== 📚 KEY REFERENCES ===\n")
            append("- Specific Harrison's chapters and page ranges\n")
            append("- Specific Davidson's chapters\n")
            append("- Key guideline documents (AHA, ESC, KDIGO, etc.)\n\n")
            append("Make it COMPREHENSIVE. This should be a complete reference for the topic. Minimum 2000 words.")
        }

        val raw = callApi(systemPrompt, userPrompt)
        return parseContent(topicTitle, "", raw)
    }

    private fun parseContent(title: String, systemId: String, raw: String): TopicContent {
        val sections = mutableListOf<Section>()
        val parts = raw.split("===")
        var currentTitle = "Overview"
        var currentContent = StringBuilder()

        for (part in parts) {
            val trimmed = part.trim()
            if (trimmed.isEmpty()) continue

            val lines = trimmed.lines()
            val firstLine = lines.firstOrNull()?.trim() ?: ""

            if (firstLine.length < 80 && (firstLine.uppercase() == firstLine || firstLine.contains("📝") || firstLine.contains("🆕") || firstLine.contains("📚"))) {
                // This is a section header
                if (currentContent.isNotBlank()) {
                    val type = when {
                        currentTitle.contains("BOARD") || currentTitle.contains("📝") -> "board_note"
                        currentTitle.contains("UPDATE") || currentTitle.contains("🆕") -> "update"
                        currentTitle.contains("REFERENCE") || currentTitle.contains("📚") -> "reference"
                        else -> "text"
                    }
                    sections.add(Section(currentTitle.replace("📝", "").replace("🆕", "").replace("📚", "").trim(), currentContent.toString().trim(), type))
                }
                currentTitle = firstLine
                currentContent = StringBuilder()
                if (lines.size > 1) {
                    currentContent.append(lines.drop(1).joinToString("\n"))
                }
            } else {
                currentContent.append("\n").append(trimmed)
            }
        }
        // Last section
        if (currentContent.isNotBlank()) {
            val type = when {
                currentTitle.contains("BOARD") || currentTitle.contains("📝") -> "board_note"
                currentTitle.contains("UPDATE") || currentTitle.contains("🆕") -> "update"
                currentTitle.contains("REFERENCE") || currentTitle.contains("📚") -> "reference"
                else -> "text"
            }
            sections.add(Section(currentTitle.replace("📝", "").replace("🆕", "").replace("📚", "").trim(), currentContent.toString().trim(), type))
        }

        if (sections.isEmpty()) {
            sections.add(Section("Content", raw, "text"))
        }

        return TopicContent(id = "", systemId = systemId, title = title, sections = sections)
    }

    private suspend fun callApi(systemPrompt: String, userPrompt: String): String = suspendCoroutine { cont ->
        if (apiKey.isBlank()) {
            cont.resume("⚠️ **No API key configured.**\n\nGo to Settings and enter your OpenRouter API key.\n\nGet a free key at: [openrouter.ai/keys](https://openrouter.ai/keys)\n\nFree models available: Gemini 2.0 Flash, DeepSeek V3")
            return@suspendCoroutine
        }
        val messages = listOf(
            mapOf("role" to "system", "content" to systemPrompt),
            mapOf("role" to "user", "content" to userPrompt)
        )
        val body = gson.toJson(mapOf("model" to model, "messages" to messages, "max_tokens" to 8192, "temperature" to 0.3))
        val req = Request.Builder().url(URL)
            .post(body.toRequestBody("application/json".toMediaType()))
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .addHeader("HTTP-Referer", "https://github.com/rroot4546-a11y/MedBoard")
            .addHeader("X-Title", "MedBoard").build()
        http.newCall(req).enqueue(object : Callback {
            override fun onFailure(c: Call, e: IOException) { cont.resume("❌ Network error: ${e.message}") }
            override fun onResponse(c: Call, r: Response) {
                try {
                    val rb = r.body?.string() ?: ""
                    if (!r.isSuccessful) {
                        cont.resume(when (r.code) {
                            401 -> "❌ Invalid API key. Check Settings."
                            402 -> "❌ No credits. Add credits at openrouter.ai"
                            429 -> "❌ Rate limited. Wait and retry."
                            else -> "❌ Error ${r.code}: ${rb.take(200)}"
                        }); return
                    }
                    val j = JsonParser.parseString(rb).asJsonObject
                    val ch = j.getAsJsonArray("choices")
                    if (ch != null && ch.size() > 0) cont.resume(ch.get(0).asJsonObject.getAsJsonObject("message").get("content").asString)
                    else cont.resume("No response.")
                } catch (e: Exception) { cont.resume("❌ Parse error: ${e.message}") }
            }
        })
    }
}
