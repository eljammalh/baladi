package com.baladi.app.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.baladi.app.R
import com.baladi.app.ui.components.EmptyState
import com.baladi.app.ui.components.TabHeader

@Composable
fun MessagesScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TabHeader(stringResource(R.string.tab_messages))
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EmptyState(
                icon = Icons.Outlined.ChatBubbleOutline,
                title = stringResource(R.string.empty_messages_title),
                message = stringResource(R.string.empty_messages_message),
            )
        }
    }
}
