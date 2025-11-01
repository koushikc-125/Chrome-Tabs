@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chrometabscreens.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chrometabscreens.designsystem.components.ApplicationScaffold
import com.example.chrometabscreens.designsystem.components.ChromeCard
import com.example.chrometabscreens.designsystem.theme.ChromeTabScreensTheme
import com.example.chrometabscreens.screen.component.TitleBarIndicator

@Composable
fun TabScreenRoot(
    viewModel: TabScreenViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TabScreen(
        state = state, onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun TabScreen(
    state: TabScreenUiState,
    onAction: (TabScreenAction) -> Unit,
) {
    val pagerState = rememberPagerState(state.currentPage) { 3 }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            onAction(TabScreenAction.OnPageChange(pagerState.currentPage))
        }
    }

    ApplicationScaffold(
        title = {
            TitleBarIndicator(pagerState)
        },
        onNavigationButtonClick = {
            when (pagerState.currentPage) {
                0 -> onAction(TabScreenAction.OnIncognitoItemAdd)
                1 -> onAction(TabScreenAction.OnRecentItemAdd)
                2 -> onAction(TabScreenAction.OnCollectionItemAdd)
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            PageContent(
                page,
                state.incognitoItemNo,
                state.recentItemNo,
                state.collectionItemNo,
                paddingValues,
                { pageNo, index ->
                    onAction(
                        TabScreenAction.OnDeleteButtonClick(pageNo, index)
                    )
                }
            )
        }
    }
}

@Composable
private fun PageContent(
    page: Int,
    incognitoItemNo: List<Int>,
    recentItemNo: List<Int>,
    collectionItemNo: List<Int>,
    paddingValues: PaddingValues,
    onDeleteButtonPress: (Int, Int) -> Unit,
) {
    val count: List<Int> = when (page) {
        0 -> incognitoItemNo
        1 -> recentItemNo
        2 -> collectionItemNo
        else -> emptyList()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 12.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalItemSpacing = 12.dp,
            modifier = Modifier
                .widthIn(max = 850.dp)
                .fillMaxHeight()
        ) {
            items(
                count = count.size,
                key = { index -> "page-$page-item-${count[index]}" }
            ) { index ->
                val itemValue = count[index]

                ChromeCard(
                    {
                        onDeleteButtonPress(page, itemValue)
                    },
                    Modifier.animateItem()
                ) {
                    Text(
                        text = "Item No ${itemValue + 1}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun DemoContent(state: TabScreenUiState, page: Int) {
    Box(
        Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        val title = when (page) {
            0 -> "Item No ${state.incognitoItemNo}"
            1 -> "Item No ${state.recentItemNo}"
            2 -> "Item No ${state.collectionItemNo}"
            else -> "Unknown"
        }

        ChromeCard({}) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@PreviewLightDark
@Composable
private fun TabScreenPreview() {
    ChromeTabScreensTheme {
        val state = TabScreenUiState(
            incognitoItemNo = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        )
        TabScreen(state, onAction = {})
    }
}
