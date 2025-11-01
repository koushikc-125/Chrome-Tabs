package com.example.chrometabscreens.screen

sealed interface TabScreenAction {
    data class OnPageChange(val pageNo: Int) : TabScreenAction
    data class OnDeleteButtonClick(val pageNo: Int,val index: Int) : TabScreenAction
    data object OnIncognitoItemAdd : TabScreenAction
    data object OnRecentItemAdd : TabScreenAction
    data object OnCollectionItemAdd : TabScreenAction
}