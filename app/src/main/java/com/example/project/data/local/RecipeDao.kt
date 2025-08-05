package com.example.project.data.local
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: FavoriteRecipe)

    @Delete
    suspend fun deleteMeal(meal: FavoriteRecipe)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<FavoriteRecipe>>

    @Query("SELECT * FROM favorites WHERE id = :mealId LIMIT 1")
    suspend fun getMealById(mealId: String): FavoriteRecipe?
}
