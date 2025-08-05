package com.example.project.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project.data.local.FavoriteRecipe
import com.example.project.data.model.Meal
import com.example.project.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = RecipeAdapter(emptyList()) { meal ->
            val action = FavoritesFragmentDirections
                .actionFavoriteFragmentToRecipeDetailFragment(meal.idMeal)
            findNavController().navigate(action)
        }

        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            val meals = favorites.map { it.toMeal() }
            adapter.updateData(meals)

            binding.tvNoFavorites.visibility = if (meals.isEmpty()) View.VISIBLE else View.GONE
        }
    }



    fun FavoriteRecipe.toMeal(): Meal {
        return Meal(
            idMeal = id,
            strMeal = name,
            strMealThumb = imageUrl,
            strInstructions = null,
            strIngredient1 = null,
            strIngredient2 = null,
            strIngredient3 = null,
            strMeasure1 = null,
            strMeasure2 = null,
            strMeasure3 = null,
            strYoutube = null
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
