package com.pryd.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pryd.app.domain.model.Activity
import com.pryd.app.presentation.theme.PriorityHigh
import com.pryd.app.presentation.theme.PriorityLow
import com.pryd.app.presentation.theme.PriorityMedium

@Composable
fun ActivityCard(
    activity: Activity,
    onMoveClick: (() -> Unit)?
) {
    val priorityColor = when (activity.priority) {
        "HIGH"   -> PriorityHigh
        "MEDIUM" -> PriorityMedium
        else     -> PriorityLow
    }

    val priorityLabel = when (activity.priority) {
        "HIGH"   -> "Alta"
        "MEDIUM" -> "Media"
        else     -> "Baja"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
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

                Spacer(modifier = Modifier.height(4.dp))

                // Chip de prioridad con punto de color
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(priorityColor.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(priorityColor, CircleShape)
                    )
                    Text(
                        text = priorityLabel,
                        fontSize = 11.sp,
                        color = priorityColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Botones de acción
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxHeight()
                    .defaultMinSize(minHeight = 72.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                onMoveClick?.let {
                    IconButton(onClick = it) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Mover",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}