package com.example.project.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.model.Meal
import com.example.project.repository.MealsRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    private val repository = MealsRepository()

    private val _searchResults = MutableLiveData<List<Meal>>()
    val searchResults: LiveData<List<Meal>> get() = _searchResults

    fun search(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.searchMeals(query)
                _searchResults.value = result.meals ?: emptyList()
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

}
