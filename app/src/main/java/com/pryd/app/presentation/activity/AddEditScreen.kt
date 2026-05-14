package com.pryd.app.presentation.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

private val PriorityHigh   = Color(0xFFE53935)
private val PriorityMedium = Color(0xFFFF9800)
private val PriorityLow    = Color(0xFF1E88E5)

private data class PriorityOption(
    val key: String,
    val label: String,
    val color: Color
)

private val priorities = listOf(
    PriorityOption("HIGH",   "Alta",  PriorityHigh),
    PriorityOption("MEDIUM", "Media", PriorityMedium),
    PriorityOption("LOW",    "Baja",  PriorityLow),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddEditViewModel = hiltViewModel()
) {
    var title       by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority    by remember { mutableStateOf("HIGH") }

    val maxDescription = 500

    LaunchedEffect(Unit) {
        viewModel.savedEvent.collect { onNavigateBack() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva actividad", fontWeight = FontWeight.Medium) },
                actions = {
                    TextButton(onClick = onNavigateBack) {
                        Text("Cancelar", color = PriorityLow)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // — Título —
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                RequiredLabel("Título")
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Ej. Revisar diseño de la pantalla de inicio") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PriorityLow,
                        unfocusedBorderColor = Color(0xFFDDE1E7)
                    )
                )
            }

            // — Descripción —
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Descripción", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                OutlinedTextField(
                    value = description,
                    onValueChange = { if (it.length <= maxDescription) description = it },
                    placeholder = { Text("Añade una descripción detallada de la actividad...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    maxLines = 6,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PriorityLow,
                        unfocusedBorderColor = Color(0xFFDDE1E7)
                    )
                )
                Text(
                    text = "${description.length}/$maxDescription",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            // — Prioridad —
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                RequiredLabel("Prioridad")
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    priorities.forEach { option ->
                        PriorityChip(
                            label    = option.label,
                            color    = option.color,
                            selected = priority == option.key,
                            onClick  = { priority = option.key }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.save(
                        title       = title,
                        description = description,
                        priority    = priority,
                        dueDate     = null
                    )
                },
                enabled  = title.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape  = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PriorityLow,
                    contentColor   = Color.White
                )
            ) {
                Text("Guardar actividad", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun PriorityChip(
    label: String,
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) color else Color(0xFFDDE1E7)
    val bgColor     = if (selected) color.copy(alpha = 0.08f) else Color.Transparent

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .border(1.5.dp, borderColor, RoundedCornerShape(50))
            .background(bgColor, RoundedCornerShape(50))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, CircleShape)
        )
        Text(label, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun RequiredLabel(text: String) {
    Text(
        text = buildAnnotatedString {
            append(text)
            append(" ")
            withStyle(SpanStyle(color = PriorityHigh)) { append("*") }
        },
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}