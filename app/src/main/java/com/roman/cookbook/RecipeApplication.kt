package com.roman.cookbook

import android.app.Application

class RecipeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RecipeRepository.initialize(this)
    }
}