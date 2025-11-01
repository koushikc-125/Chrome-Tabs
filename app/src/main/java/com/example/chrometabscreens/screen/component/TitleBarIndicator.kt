package com.example.chrometabscreens.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.chrometabscreens.designsystem.icon.ApplicationIcon
import kotlinx.coroutines.launch

@Composable
fun TitleBarIndicator(
    pagerState: PagerState,
) {
    val tabs = listOf(ApplicationIcon.Incognito, ApplicationIcon.Rectangle, ApplicationIcon.Grid)
    val contentPadding = ButtonDefaults.ContentPadding
    var tabWidth by remember { mutableStateOf(0.dp) }
    val scope = rememberCoroutineScope()

    Box(
        Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            Modifier.wrapContentWidth(),
            shape = RoundedCornerShape(12.dp),
            shadowElevation = 8.dp
        ) {
            TabItem(
                tabs,
                contentPadding,
                tabWidth,
                { it ->
                    tabWidth = it
                },
                {
                    scope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
            )
            OverlaySurface(pagerState, tabWidth, contentPadding)
            TabItem(
                tabs,
                contentPadding,
                tabWidth,
                { it ->
                    tabWidth = it
                },
                {
                    scope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
                Modifier
                    .drawWithContent {
                        val maskLeft = if (tabWidth > 0.dp) {
                            (pagerState.pageOffset * tabWidth).toPx()
                        } else 0f
                        val maskRight = maskLeft + tabWidth.toPx()

                        clipRect(
                            left = maskLeft,
                            right = maskRight
                        ) {
                            this@drawWithContent.drawContent()
                        }
                    },
                iconColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Composable
private fun TabItem(
    tabs: List<Int>,
    contentPadding: PaddingValues,
    itemWidth: Dp,
    onItemWidthChange: (Dp) -> Unit,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colorScheme.onSurface.copy(
        alpha = 0.6f
    ),
) {
    val density = LocalDensity.current

    Row(
        modifier
    ) {
        tabs.forEachIndexed { index, item ->
            Box(
                Modifier
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onItemClick(index) }
                    )
                    .onSizeChanged {
                        if (itemWidth == 0.dp) {
                            onItemWidthChange(with(density) { it.width.toDp() })
                        }
                    }
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .padding(contentPadding),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(item),
                    "icon",
                    tint = iconColor
                )
            }
        }
    }
}

@Composable
private fun OverlaySurface(
    pagerState: PagerState,
    tabWidth: Dp,
    contentPadding: PaddingValues,
) {
    Box(
        Modifier
            .graphicsLayer {
                this.translationX = if (tabWidth > 0.dp) {
                    (pagerState.pageOffset * tabWidth).toPx()
                } else 0f
            }
            .size(
                width = tabWidth,
                height = ButtonDefaults.MinHeight
            )
            .background(
                MaterialTheme.colorScheme.onSurface,
                RoundedCornerShape(12.dp)
            )
        //.padding(contentPadding)
    )
}

private val PagerState.pageOffset: Float
    get() = this.currentPage + this.currentPageOffsetFraction

