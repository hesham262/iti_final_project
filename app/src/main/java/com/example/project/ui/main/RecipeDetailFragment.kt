package com.example.project.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.project.R
import com.example.project.data.local.FavoriteRecipe
import com.example.project.databinding.FragmentMealDetailsBinding
import kotlinx.coroutines.launch
import com.google.android.material.snackbar.Snackbar

class RecipeDetailFragment: Fragment() {

    private var _binding: FragmentMealDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeDetailsViewModel by viewModels()

    private lateinit var favoritesViewModel: FavoritesViewModel

    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealId = arguments?.getString("mealId") ?: return
        viewModel.loadMealDetails(mealId)

        viewModel.meal.observe(viewLifecycleOwner) { meal ->
            meal?.let { currentMeal ->
                binding.tvMealName.text = currentMeal.strMeal
                Glide.with(this)
                    .load(currentMeal.strMealThumb)
                    .into(binding.imgMeal)

                binding.tvIngredients.text = collectIngredients(currentMeal)
                binding.tvInstructions.text = currentMeal.strInstructions ?: ""


                lifecycleScope.launch {
                    val isFav = favoritesViewModel.isFavorite(currentMeal.idMeal)
                    updateFavoriteIcon(isFav)

                    var favStatus = isFav

                    binding.btnFavorite.setOnClickListener {
                        lifecycleScope.launch {
                            if (favStatus) {
                                favoritesViewModel.removeFromFavorites(
                                    FavoriteRecipe(
                                        currentMeal.idMeal,
                                        currentMeal.strMeal,
                                        currentMeal.strMealThumb
                                    )
                                )
                                Snackbar.make(binding.root, " Removed from Fav Recipes", Snackbar.LENGTH_SHORT).show()
                                favStatus = false
                            } else {
                                favoritesViewModel.addToFavorites(
                                    FavoriteRecipe(
                                        currentMeal.idMeal,
                                        currentMeal.strMeal,
                                        currentMeal.strMealThumb
                                    )
                                )
                                Snackbar.make(binding.root, "Saved to Fav Recipes", Snackbar.LENGTH_SHORT).show()
                                favStatus = true
                            }

                            updateFavoriteIcon(favStatus)
                        }
                    }
                }
            }

            binding.btnWatchVideo.setOnClickListener {
                val videoUrl = meal?.strYoutube
                if (!videoUrl.isNullOrEmpty()) {
                    val dialog = VideoPlayerDialogFragment.newInstance(videoUrl)
                    dialog.show(parentFragmentManager, "video_player")
                } else {
                    Toast.makeText(requireContext(), "No Video For a Recipe", Toast.LENGTH_SHORT).show()
                }
            }
        }

        favoritesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[FavoritesViewModel::class.java]

    }

    private fun collectIngredients(meal: com.example.project.data.model.Meal): String {
        val ingredients = mutableListOf<String>()

        val ing = listOf(
            meal.strIngredient1 to meal.strMeasure1,
            meal.strIngredient2 to meal.strMeasure2,
            meal.strIngredient3 to meal.strMeasure3

        )

        for ((ingredient, measure) in ing) {
            if (!ingredient.isNullOrBlank()) {
                val line = if (!measure.isNullOrBlank()) "$ingredient - $measure" else ingredient
                ingredients.add(line)
            }
        }

        return ingredients.joinToString("\n")

    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.filled_heart)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.heart_outline)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
