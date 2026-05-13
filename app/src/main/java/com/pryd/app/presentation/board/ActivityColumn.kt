package com.pryd.app.presentation.board

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pryd.app.domain.model.Activity
import com.pryd.app.presentation.components.ActivityCard

@Composable
fun ActivityColumn(
    activities: List<Activity>,
    onMoveToInProgress: ((String) -> Unit)? = null,
    onMoveToCompleted: ((String) -> Unit)? = null,
    onDelete: (String) -> Unit
) {
    if (activities.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Sin actividades",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(activities, key = { it.id }) { activity ->
                ActivityCard(
                    activity = activity,
                    onMoveClick = {
                        onMoveToInProgress?.invoke(activity.id)
                        onMoveToCompleted?.invoke(activity.id)
                    },
                    onDeleteClick = { onDelete(activity.id) }
                )
            }
        }
    }
}

@Composable
fun CompletedColumn(
    activities: List<Activity>,
    onDelete: (String) -> Unit,
    onClearAll: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(activities, key = { it.id }) { activity ->
            ActivityCard(
                activity = activity,
                onMoveClick = null,
                onDeleteClick = { onDelete(activity.id) }
            )
        }
        if (activities.isNotEmpty()) {
            item {
                Button(
                    onClick = onClearAll,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Limpiar finalizadas")
                }
            }
        }
    }
}