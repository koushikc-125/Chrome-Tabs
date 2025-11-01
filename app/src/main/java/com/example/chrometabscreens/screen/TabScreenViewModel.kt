package com.example.chrometabscreens.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class TabScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(TabScreenUiState())
    val state = _state
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: TabScreenAction) {
        when (action) {
            is TabScreenAction.OnPageChange -> {
                _state.update {
                    it.copy(
                        currentPage = action.pageNo
                    )
                }
            }

            is TabScreenAction.OnIncognitoItemAdd -> {
                _state.update {
                    it.copy(
                        incognitoItemNo = it.incognitoItemNo + (it.incognitoItemNo.maxOrNull()
                            ?.plus(1) ?: 0)
                    )
                }
            }

            is TabScreenAction.OnRecentItemAdd -> {
                _state.update {
                    it.copy(
                        recentItemNo = it.recentItemNo + (it.recentItemNo.maxOrNull()
                            ?.plus(1) ?: 0)
                    )
                }
            }

            is TabScreenAction.OnCollectionItemAdd -> {
                _state.update {
                    it.copy(
                        collectionItemNo = it.collectionItemNo + (it.collectionItemNo.maxOrNull()
                            ?.plus(1) ?: 0)
                    )
                }
            }

            is TabScreenAction.OnDeleteButtonClick -> {
                _state.update {
                    when (action.pageNo) {
                        0 -> {
                            it.copy(incognitoItemNo = it.incognitoItemNo.filter { it -> it != action.index })
                        }

                        1 -> {
                            it.copy(recentItemNo = it.recentItemNo.filter { it -> it != action.index })
                        }

                        2 -> {
                            it.copy(collectionItemNo = it.collectionItemNo.filter { it -> it != action.index })
                        }

                        else -> it
                    }
                }
            }
        }

    }
}