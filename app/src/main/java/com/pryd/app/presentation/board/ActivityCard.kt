package com.pryd.app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pryd.app.domain.model.Activity
import com.pryd.app.presentation.theme.PriorityHigh
import com.pryd.app.presentation.theme.PriorityLow
import com.pryd.app.presentation.theme.PriorityMedium

@Composable
fun ActivityCard(
    activity: Activity,
    onMoveClick: (() -> Unit)?,
    onDeleteClick: () -> Unit
) {
    val priorityColor = when (activity.priority) {
        "HIGH" -> PriorityHigh
        "MEDIUM" -> PriorityMedium
        "LOW" -> PriorityLow
        else -> PriorityLow
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Priority indicator stripe
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .defaultMinSize(minHeight = 64.dp)
            ) {
                // Color is handled by the card background
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (!activity.description.isNullOrBlank()) {
                    Text(
                        text = activity.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Priority chip
                    SuggestionChip(
                        onClick = {},
                        label = {
                            Text(
                                text = when (activity.priority) {
                                    "HIGH" -> "Alta"
                                    "MEDIUM" -> "Media"
                                    "LOW" -> "Baja"
                                    else -> ""
                                },
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                    // Due date if present
                    activity.dueDate?.let {
                        SuggestionChip(
                            onClick = {},
                            label = {
                                Text(
                                    text = "Fecha",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                    }
                }
            }

            // Action buttons
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                onMoveClick?.let {
                    IconButton(onClick = it) {
                        Text("→")
                    }
                }
                IconButton(onClick = onDeleteClick) {
                    Text("🗑️")
                }
            }
        }
    }
}