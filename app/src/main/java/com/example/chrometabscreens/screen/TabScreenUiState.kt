package com.example.chrometabscreens.screen

data class TabScreenUiState(
    val currentPage: Int = 0,
    val incognitoItemNo: List<Int> = emptyList(),
    val recentItemNo: List<Int> = emptyList(),
    val collectionItemNo: List<Int> = emptyList(),
)
