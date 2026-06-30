package com.test.anzapplication.feature_user_detail.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.test.anzapplication.feature.users.R
import com.test.anzapplication.feature_users.domain.model.User
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    user: User,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                title = { Text(text = stringResource(com.test.feature_user_detail.R.string.user_details)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            Log.d("PHOTO_URL", user.photo)
            AsyncImage(
                model = user.photo,
                contentDescription = user.name,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                onError = {
                    Log.e("PHOTO_URL", "Failed to load image", it.result.throwable)
                }
            )


            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(text = stringResource(com.test.feature_user_detail.R.string.email, user.email))
            Text(text = stringResource(com.test.feature_user_detail.R.string.company, user.company))
            Text(text = stringResource(com.test.feature_user_detail.R.string.phone, user.phone))
            Text(
                text = buildString {
                    appendLine(stringResource(com.test.feature_user_detail.R.string.address))
                    appendLine(user.address)
                    appendLine("${user.state} - ${user.zip}")
                    append(user.country)
                }
            )
        }
    }
}

