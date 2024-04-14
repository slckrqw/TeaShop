package com.example.teashop.screen.screen.catalog_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CatalogViewModel constructor(): ViewModel() {
    private val _catalogUiState = MutableStateFlow(CatalogUiState())
    val catalogUiState: StateFlow<CatalogUiState> = _catalogUiState
}