package com.roman.cookbook.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.roman.cookbook.R
import java.util.*

@Entity
data class Recipe (@PrimaryKey val recipeId: UUID = UUID.randomUUID(),
                   @DrawableRes var picture: Int = R.drawable.pasta,
                   var additionalIfo: String = "",
                   var name: String = "")