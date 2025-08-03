package com.example.project.repository

import com.example.project.data.remote.ApiService
import com.example.project.data.remote.RetrofitInstance

class MealsRepository {


    private val api = RetrofitInstance.getInstance().create(ApiService::class.java)


    suspend fun getMealsByFirstLetter(letter: String) =
        api.getMealsByFirstLetter(letter)


    suspend fun searchMeals(query: String) =
        api.searchMeals(query)


    suspend fun getMealDetails(id: String) =
        api.getMealDetails(id)


}