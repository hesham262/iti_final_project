package com.example.project.data.local
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao{

    @Insert
    suspend fun insertMeal(meal: FavoriteRecipe)

    @Delete
    suspend fun deleteMeal(meal: FavoriteRecipe)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<FavoriteRecipe>>

    @Query("SELECT * FROM favorites WHERE id = :mealId ")
    suspend fun getMealById(mealId: String): FavoriteRecipe?
}
