@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chrometabscreens.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chrometabscreens.designsystem.icon.ApplicationIcon

@Composable
fun ApplicationScaffold(
    title: @Composable () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    titleBarColor: Color = Color.Transparent,
    onNavigationButtonClick: () -> Unit,
    navigationIcon: Int = ApplicationIcon.Add,
    actionButtonIcon: Int = ApplicationIcon.Option,
    content: @Composable (PaddingValues) -> Unit,
) {
    val gradientColors = listOf(
        backgroundColor, Color.Transparent
    )

    Scaffold(
        topBar = {
            Box(Modifier.fillMaxWidth().widthIn(max = 850.dp), contentAlignment = Alignment.Center){
                TopAppBar(
                    modifier = Modifier
                        .widthIn(max = 850.dp)
                        .background(brush = Brush.verticalGradient(gradientColors)),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = titleBarColor
                    ),
                    title = title,
                    navigationIcon = {
                        IconButton(onNavigationButtonClick) {
                            Icon(
                                painterResource(navigationIcon),
                                "Search",
                                tint = MaterialTheme.colorScheme.scrim
                            )
                        }
                    },
                    actions = {
                        IconButton({}) {
                            Icon(
                                painterResource(actionButtonIcon),
                                "Option",
                                tint = MaterialTheme.colorScheme.scrim
                            )
                        }
                    })
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}
