package com.pryd.app.presentation.navigation

sealed class Screen(val route: String) {
    object Board : Screen("board")
    object Pomodoro : Screen("pomodoro")
    object AddEditActivity : Screen("add_edit_activity")
}