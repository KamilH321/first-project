package com.example.firstproject.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.firstproject.R

sealed class Tab(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector
) {
    object Settings : Tab(Routes.SETTINGS, R.string.tab_settings_title, Icons.Default.Settings)
    object Edit : Tab(Routes.EDIT, R.string.tab_edit_title, Icons.Default.Edit)
    object Messages : Tab(Routes.MESSAGES, R.string.tab_messages_title, Icons.AutoMirrored.Filled.List)
}

object Routes {
    const val SETTINGS = "settings"
    const val EDIT = "edit"
    const val MESSAGES = "messages"
}
