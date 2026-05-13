package com.pryd.app.presentation.board

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BoardScreen(
    viewModel: BoardViewModel = hiltViewModel(),
    onAddClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Pendientes", "En Curso", "Finalizadas")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> ActivityColumn(
                    activities = uiState.pending,
                    onMoveToInProgress = { viewModel.moveToInProgress(it) },
                    onDelete = { viewModel.deleteActivity(it) }
                )
                1 -> ActivityColumn(
                    activities = uiState.inProgress,
                    onMoveToCompleted = { viewModel.moveToCompleted(it) },
                    onDelete = { viewModel.deleteActivity(it) }
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