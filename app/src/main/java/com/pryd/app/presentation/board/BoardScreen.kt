package com.pryd.app.presentation.board

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardScreen(
    viewModel: BoardViewModel = hiltViewModel(),
    onAddClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Pendientes", "En Curso", "Finalizadas")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Tablero", fontWeight = FontWeight.Medium)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Nueva actividad",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            PrimaryTabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Medium else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            when (selectedTab) {
                0 -> ActivityColumn(
                    activities = uiState.pending,
                    onMoveToInProgress = { viewModel.moveToInProgress(it) }
                )
                1 -> ActivityColumn(
                    activities = uiState.inProgress,
                    onMoveToCompleted = { viewModel.moveToCompleted(it) }
                )
                2 -> CompletedColumn(
                    activities = uiState.completed,
                    onDelete = { viewModel.deleteActivity(it) },
                    onClearAll = { viewModel.clearCompleted() }
                )
            }
        }
    }
}