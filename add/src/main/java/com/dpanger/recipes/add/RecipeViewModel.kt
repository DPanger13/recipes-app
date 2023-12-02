package com.dpanger.recipes.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpanger.recipes.data.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject internal constructor(
    private val saveRecipe: SaveRecipeUseCase
) : ViewModel() {
    var title by mutableStateOf("")
        private set

    private val _uiState = MutableStateFlow<AddRecipeUiState>(AddRecipeUiState.InProgress)
    val uiState = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        this.title = title
    }

    fun save() {
        viewModelScope.launch {
            _uiState.value = AddRecipeUiState.Saving
            _uiState.value = saveRecipe(title)
        }
    }
}
