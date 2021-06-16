package com.roman.cookbook

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.roman.cookbook.database.RecipeDatabase
import com.roman.cookbook.model.Ingredient
import com.roman.cookbook.model.PreparationStep
import com.roman.cookbook.model.Recipe
import com.roman.cookbook.model.RecipeIngredientsSteps
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "recipe-database"

class RecipeRepository private constructor(context: Context) {

    private val database: RecipeDatabase = Room.databaseBuilder(
        context.applicationContext,
        RecipeDatabase::class.java,
        DATABASE_NAME
    )

//        .addCallback(object : RoomDatabase.Callback() {
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            val idList = listOf(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())
//            ioThread{
//
//            }
//        }
//    })

        .build()

//    private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
//
//    fun ioThread(f : () -> Unit) {
//        IO_EXECUTOR.execute(f)
//    }

    private val recipeDao = database.recipeDao()

    fun getRecipes(): LiveData<List<Recipe>> = recipeDao.getRecipes()

    fun getRecipe(id: UUID): LiveData<Recipe?> = recipeDao.getRecipe(id)

    fun getRecipeIngredientsSteps(id: UUID): LiveData<RecipeIngredientsSteps?> = recipeDao.getRecipeIngredientsSteps(id)

    companion object {
        private var INSTANCE: RecipeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = RecipeRepository(context)
            }
        }

        fun get(): RecipeRepository {
            return INSTANCE ?:
            throw IllegalStateException("RecipeRepository must be initialized")
        }
    }
}