package com.baladi.app.ui.screens.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.baladi.app.R
import com.baladi.app.ui.components.EmptyState
import com.baladi.app.ui.components.TabHeader

@Composable
fun PostScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TabHeader(stringResource(R.string.post_title))
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EmptyState(
                icon = Icons.Outlined.AddAPhoto,
                title = stringResource(R.string.post_empty_title),
                message = stringResource(R.string.post_empty_message),
            )
        }
    }
}
