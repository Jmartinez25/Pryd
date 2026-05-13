package com.pryd.app.domain.model

data class Activity(
    val id: String,
    val title: String,
    val description: String?,
    val priority: String,
    val status: String,
    val dueDate: Long?,
    val createdAt: Long,
    val completedAt: Long?
)
