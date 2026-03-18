package com.roox.medboard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey val id: String,
    val systemId: String,
    val title: String,
    val sectionsJson: String,
    val isBookmarked: Boolean = false
)
