package com.roox.medboard.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Medical system (e.g. Cardiology, Respiratory) */
data class MedSystem(
    val id: String,
    val name: String,
    val icon: String,
    val color: String,
    val topicCount: Int,
    val topics: List<TopicSummary>
)

data class TopicSummary(
    val id: String,
    val title: String,
    val subtitle: String
)

/** Full topic content */
data class TopicContent(
    val id: String,
    val systemId: String,
    val title: String,
    val sections: List<Section>
)

data class Section(
    val title: String,
    val content: String,     // Markdown
    val type: String = "text" // text, board_note, update, table, key_point
)

/** Cached AI-generated content */
@Entity(tableName = "cached_topics")
data class CachedTopic(
    @PrimaryKey val topicId: String,
    val systemId: String,
    val title: String,
    val contentJson: String,  // JSON of TopicContent
    val timestamp: Long = System.currentTimeMillis()
)

/** Bookmarked topics */
@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey val topicId: String,
    val systemId: String,
    val title: String,
    val timestamp: Long = System.currentTimeMillis()
)

/** Reading progress */
@Entity(tableName = "reading_progress")
data class ReadingProgress(
    @PrimaryKey val topicId: String,
    val systemId: String,
    val scrollPosition: Int = 0,
    val completed: Boolean = false,
    val lastRead: Long = System.currentTimeMillis()
)
