package com.example.project.ui.main
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.project.data.local.FavoriteRecipe
import com.example.project.repository.FavoritesRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FavoritesRepository(application)

    val favorites: LiveData<List<FavoriteRecipe>> = repository.getAllFavorites()

    fun addToFavorites(meal: FavoriteRecipe) {
        viewModelScope.launch {
            repository.addToFavorites(meal)
        }
    }

    fun removeFromFavorites(meal: FavoriteRecipe) {
        viewModelScope.launch {
            repository.removeFromFavorites(meal)
        }
    }

    suspend fun isFavorite(id: String): Boolean {
        return repository.isMealFavorite(id)
    }
}