package com.pryd.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val priority: String,
    val status: String,
    val dueDate: Long?,
    val createdAt: Long,
    val completedAt: Long?
)
