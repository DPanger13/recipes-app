package com.dpanger.recipes.add

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
@ExperimentalMaterial3Api
fun AddRecipeScreen(
    viewModel: RecipeViewModel,
    onRecipeSaved: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (viewModel.uiState.collectAsStateWithLifecycle().value) {
        AddRecipeUiState.InProgress -> {
            RecipeForm(
                modifier = modifier,
                title = viewModel.title,
                onTitleChanged = viewModel::updateTitle,
                onSaveClicked = viewModel::save
            )
        }
        AddRecipeUiState.Saved -> {
            onRecipeSaved()
        }
        AddRecipeUiState.Saving -> {
            CircularProgressIndicator(
                modifier = modifier
            )
        }
        AddRecipeUiState.SavingFailed -> {
            Text(
                text = stringResource(R.string.failed_to_save_recipe)
            )
        }
    }
}
