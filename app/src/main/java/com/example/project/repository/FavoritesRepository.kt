package com.example.project.repository



import android.content.Context
import androidx.lifecycle.LiveData
import com.example.project.data.local.AppDatabase
import com.example.project.data.local.FavoriteRecipe

class FavoritesRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).recipeDao()

    suspend fun addToFavorites(meal: FavoriteRecipe) {
        dao.insertMeal(meal)
    }

    suspend fun removeFromFavorites(meal: FavoriteRecipe) {
        dao.deleteMeal(meal)
    }

    fun getAllFavorites(): LiveData<List<FavoriteRecipe>> {
        return dao.getAllFavorites()
    }

    suspend fun isMealFavorite(id: String): Boolean {
        return dao.getMealById(id) != null
    }
}
