package com.roman.cookbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.roman.cookbook.model.Recipe
import com.roman.cookbook.model.RecipeIngredientsSteps
import java.util.*

class RecipeDetailViewModel() : ViewModel() {
    private val recipeRepository = RecipeRepository.get()
    private val recipeIdLiveData = MutableLiveData<UUID>()

    var recipeLiveData: LiveData<RecipeIngredientsSteps?> =
        Transformations.switchMap(recipeIdLiveData) { recipeId ->
            recipeRepository.getRecipeIngredientsSteps(recipeId)
        }

    fun loadRecipe(recipeId: UUID) {
        recipeIdLiveData.value = recipeId
    }
}
