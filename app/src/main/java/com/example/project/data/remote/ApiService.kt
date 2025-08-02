package com.example.project.data.remote



import com.example.project.data.model.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("search.php")
    suspend fun getMealsByFirstLetter(
        @Query("f") letter: String
    ): MealsResponse


    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String
    ): MealsResponse



}
