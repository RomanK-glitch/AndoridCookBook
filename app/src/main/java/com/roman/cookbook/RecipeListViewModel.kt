package com.roman.cookbook

import androidx.lifecycle.ViewModel

class RecipeListViewModel: ViewModel() {
    private val recipeRepository = RecipeRepository.get()
    val recipesLiveData = recipeRepository.getRecipes()
}