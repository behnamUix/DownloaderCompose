package com.behnamuix.persianM3Calendar.ui.nav.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavSc(val route: String, val title: String, val icon: ImageVector) {
    object Calendar : BottomNavSc("calendar", "تثویم", Icons.Default.CalendarToday)

    object Downloader : BottomNavSc("download", "دانلودر", Icons.Default.Downloading)
    companion object {
        val btnNavItems = listOf(Downloader, Calendar)
    }

}