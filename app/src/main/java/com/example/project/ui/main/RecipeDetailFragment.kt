package com.example.project.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.project.databinding.FragmentMealDetailsBinding

class RecipeDetailFragment: Fragment() {

    private var _binding: FragmentMealDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeDetailsViewModel by viewModels()

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
            meal?.let {
                binding.tvMealName.text = it.strMeal
                Glide.with(this)
                    .load(it.strMealThumb)
                    .into(binding.imgMeal)


                binding.tvIngredients.text = collectIngredients(it)


                binding.tvInstructions.text = it.strInstructions ?: ""
            }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
