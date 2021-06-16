package com.roman.cookbook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.roman.cookbook.model.Ingredient
import com.roman.cookbook.model.PreparationStep
import com.roman.cookbook.model.Recipe
import com.roman.cookbook.model.RecipeIngredientsSteps
import java.util.*

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getRecipes(): LiveData<List<Recipe>>
    @Query("SELECT * FROM recipe WHERE recipeId=(:id)")
    fun getRecipe(id: UUID): LiveData<Recipe?>
    @Insert
    fun insertRecipe(vararg recipes: Recipe)
    @Query("SELECT * FROM recipe WHERE recipeId=(:id)")
    fun getRecipeIngredientsSteps(id: UUID): LiveData<RecipeIngredientsSteps?>
    @Insert
    fun insertIngredients(vararg ingredient: Ingredient)
    @Insert
    fun insertPreparationStep(vararg preparationStep: PreparationStep)
}