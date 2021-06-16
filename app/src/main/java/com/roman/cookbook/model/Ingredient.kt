package com.roman.cookbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Ingredient (@PrimaryKey val ingredientId: UUID = UUID.randomUUID(),
                       var productName: String = "",
                       var amount: String = "",
                       var fromRecipeId : UUID = UUID.randomUUID())