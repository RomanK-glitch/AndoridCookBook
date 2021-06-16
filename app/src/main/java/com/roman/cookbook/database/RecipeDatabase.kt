package com.roman.cookbook.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.roman.cookbook.model.Ingredient
import com.roman.cookbook.model.PreparationStep
import com.roman.cookbook.model.Recipe

@Database(entities = [ Recipe::class, Ingredient::class, PreparationStep::class ], version = 2, exportSchema = false)
@TypeConverters(RecipeTypeConverters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}