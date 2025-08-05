package com.example.project.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.model.Meal
import com.example.project.repository.MealsRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val repository = MealsRepository()


    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    init {
        loadMealsForAllLetters()
    }

    private fun loadMealsForAllLetters() {

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val letters = ('a'..'z')
                val allMeals = mutableListOf<Meal>()

                for (letter in letters) {
                    try {
                        val response = repository.getMealsByFirstLetter(letter.toString())
                        response.meals?.let { allMeals.addAll(it) }
                    } catch (e: Exception) {
                        Log.e("API_TEST", "Error for letter $letter: ${e.message}")
                    }
                }

                _meals.value = allMeals

            } catch (e: Exception) {
                Log.e("API_TEST", "Error: ${e.message}", e)
            }
        finally {
            _isLoading.value = false
        }
        }
    }
}
