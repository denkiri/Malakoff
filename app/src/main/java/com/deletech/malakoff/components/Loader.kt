package com.deletech.malakoff.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.deletech.malakoff.ui.theme.md_theme_light_primary

@Composable
 fun Loader() {
    Column {
        LinearProgressIndicator(
            color = md_theme_light_primary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}