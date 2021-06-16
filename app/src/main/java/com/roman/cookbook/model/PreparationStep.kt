package com.roman.cookbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class PreparationStep(@PrimaryKey val prepStepId: UUID = UUID.randomUUID(),
                           var prepStep: Int = 0,
                           var description: String = "",
                           var fromRecipeId: UUID = UUID.randomUUID())