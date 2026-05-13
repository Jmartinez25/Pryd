package com.pryd.app.data.mapper

import com.pryd.app.data.local.entity.ActivityEntity
import com.pryd.app.domain.model.Activity

fun ActivityEntity.toDomain(): Activity {
    return Activity(
        id = id,
        title = title,
        description = description,
        priority = priority,
        status = status,
        dueDate = dueDate,
        createdAt = createdAt,
        completedAt = completedAt
    )
}

fun Activity.toEntity(): ActivityEntity {
    return ActivityEntity(
        id = id,
        title = title,
        description = description,
        priority = priority,
        status = status,
        dueDate = dueDate,
        createdAt = createdAt,
        completedAt = completedAt
    )
}