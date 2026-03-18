package com.roox.medboard.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roox.medboard.data.Section
import com.roox.medboard.databinding.ItemSectionBoardNoteBinding
import com.roox.medboard.databinding.ItemSectionReferenceBinding
import com.roox.medboard.databinding.ItemSectionTextBinding
import com.roox.medboard.databinding.ItemSectionUpdateBinding
import io.noties.markwon.Markwon

class SectionAdapter(
    private val markwon: Markwon
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var sections: List<Section> = emptyList()

    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_BOARD_NOTE = 1
        const val TYPE_UPDATE = 2
        const val TYPE_REFERENCE = 3
    }

    fun setSections(newSections: List<Section>) {
        sections = newSections
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (sections[position].type) {
            "board_note" -> TYPE_BOARD_NOTE
            "update" -> TYPE_UPDATE
            "reference" -> TYPE_REFERENCE
            else -> TYPE_TEXT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_BOARD_NOTE -> BoardNoteViewHolder(
                ItemSectionBoardNoteBinding.inflate(inflater, parent, false)
            )
            TYPE_UPDATE -> UpdateViewHolder(
                ItemSectionUpdateBinding.inflate(inflater, parent, false)
            )
            TYPE_REFERENCE -> ReferenceViewHolder(
                ItemSectionReferenceBinding.inflate(inflater, parent, false)
            )
            else -> TextViewHolder(
                ItemSectionTextBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = sections[position]
        val cleanTitle = cleanSectionTitle(section.title)
        val cleanContent = cleanContent(section.content)

        when (holder) {
            is TextViewHolder -> {
                holder.binding.tvSectionTitle.text = cleanTitle
                markwon.setMarkdown(holder.binding.tvSectionContent, cleanContent)
            }
            is BoardNoteViewHolder -> {
                holder.binding.tvSectionTitle.text = cleanTitle
                markwon.setMarkdown(holder.binding.tvSectionContent, cleanContent)
            }
            is UpdateViewHolder -> {
                holder.binding.tvSectionTitle.text = cleanTitle
                markwon.setMarkdown(holder.binding.tvSectionContent, cleanContent)
            }
            is ReferenceViewHolder -> {
                holder.binding.tvSectionTitle.text = cleanTitle
                markwon.setMarkdown(holder.binding.tvSectionContent, cleanContent)
            }
        }
    }

    override fun getItemCount() = sections.size

    private fun cleanSectionTitle(title: String): String {
        return title.replace(Regex("^#+\\s*"), "")
            .replace(Regex("===.*==="), "")
            .trim()
    }

    private fun cleanContent(content: String): String {
        var cleaned = content.replace(Regex("===\\s*[A-Z &()/-]+\\s*==="), "").trim()
        cleaned = fixMarkdownTables(cleaned)
        return cleaned
    }

    /**
     * Fix malformed markdown tables where separator row is missing.
     * Input:  | Col1 | Col2 |
     *         |------|------|  (may or may not be present)
     *         | val1 | val2 |
     *
     * Also handles tables where the separator uses dashes inside the pipe content.
     */
    private fun fixMarkdownTables(text: String): String {
        val lines = text.lines()
        val result = mutableListOf<String>()
        var i = 0

        while (i < lines.size) {
            val line = lines[i]

            // Detect a table header row: starts and ends with |, contains multiple |
            if (isTableRow(line)) {
                val tableLines = mutableListOf<String>()
                tableLines.add(line)

                // Peek next line
                val nextLine = if (i + 1 < lines.size) lines[i + 1] else ""

                if (isSeparatorRow(nextLine)) {
                    // Already has separator — collect table normally
                    tableLines.add(nextLine)
                    i += 2
                    while (i < lines.size && isTableRow(lines[i])) {
                        tableLines.add(lines[i])
                        i++
                    }
                } else {
                    // Missing separator — inject one
                    val colCount = line.split("|").filter { it.isNotBlank() }.size
                    val separator = "| " + List(colCount) { "---" }.joinToString(" | ") + " |"
                    tableLines.add(separator)
                    i++
                    while (i < lines.size && isTableRow(lines[i])) {
                        tableLines.add(lines[i])
                        i++
                    }
                }

                result.addAll(tableLines)
                result.add("") // blank line after table
            } else {
                result.add(line)
                i++
            }
        }

        return result.joinToString("\n")
    }

    private fun isTableRow(line: String): Boolean {
        val trimmed = line.trim()
        return trimmed.startsWith("|") && trimmed.endsWith("|") && trimmed.count { it == '|' } >= 3
    }

    private fun isSeparatorRow(line: String): Boolean {
        val trimmed = line.trim()
        return trimmed.startsWith("|") && trimmed.contains("---") && trimmed.contains("|")
    }

    class TextViewHolder(val binding: ItemSectionTextBinding) : RecyclerView.ViewHolder(binding.root)
    class BoardNoteViewHolder(val binding: ItemSectionBoardNoteBinding) : RecyclerView.ViewHolder(binding.root)
    class UpdateViewHolder(val binding: ItemSectionUpdateBinding) : RecyclerView.ViewHolder(binding.root)
    class ReferenceViewHolder(val binding: ItemSectionReferenceBinding) : RecyclerView.ViewHolder(binding.root)
}
