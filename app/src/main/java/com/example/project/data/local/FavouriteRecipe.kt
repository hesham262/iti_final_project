package com.example.project.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteRecipe(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String
)