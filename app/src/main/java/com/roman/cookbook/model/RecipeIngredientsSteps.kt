package com.roman.cookbook.model

import androidx.room.Embedded
import androidx.room.Relation
import com.roman.cookbook.model.Ingredient
import com.roman.cookbook.model.PreparationStep
import com.roman.cookbook.model.Recipe

data class RecipeIngredientsSteps (
    @Embedded val recipe : Recipe = Recipe(),
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "fromRecipeId"
    )
    val ingredientsList : List<Ingredient> = emptyList(),
    @Relation (
        parentColumn = "recipeId",
        entityColumn = "fromRecipeId"
    )
    val prepStepsList : List<PreparationStep> = emptyList()
)