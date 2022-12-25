package com.example.binlisttest.models

data class UiState(
    val isLoading: Boolean = false,
    val binInfo: BinInfo? = null,
    val binList: List<String> = emptyList()
)
