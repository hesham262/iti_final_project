package com.example.project.ui.main



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.model.Meal
import com.example.project.repository.MealsRepository
import kotlinx.coroutines.launch

class RecipeDetailsViewModel: ViewModel() {

    private val repository = MealsRepository()

    private val _meal = MutableLiveData<Meal?>()
    val meal: LiveData<Meal?> get() = _meal

    fun loadMealDetails(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMealDetails(id)
                _meal.value = response.meals?.firstOrNull()
            } catch (e: Exception) {
                Log.e("API_TEST", "Error: ${e.message}", e)
            }
        }
    }
}
