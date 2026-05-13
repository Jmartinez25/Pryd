package com.pryd.app.presentation.activity

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddEditViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("MEDIUM") }
    var hasDueDate by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.savedEvent.collect {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva actividad") },
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) {
                        Text("Cancelar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Text("Prioridad", style = MaterialTheme.typography.labelLarge)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("HIGH", "MEDIUM", "LOW").forEach { p ->
                    FilterChip(
                        selected = priority == p,
                        onClick = { priority = p },
                        label = {
                            Text(
                                when (p) {
                                    "HIGH" -> "Alta"
                                    "MEDIUM" -> "Media"
                                    "LOW" -> "Baja"
                                    else -> p
                                }
                            )
                        }
                    )
                }
            }

            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(
                    checked = hasDueDate,
                    onCheckedChange = { hasDueDate = it }
                )
                Text("Agregar fecha límite")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.save(
                        title = title,
                        description = description,
                        priority = priority,
                        dueDate = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank()
            ) {
                Text("Guardar actividad")
            }
        }
    }
}