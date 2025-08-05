package com.example.project.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project.R
import com.example.project.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: RecipeAdapter


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvMeals.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvMeals.visibility = View.VISIBLE
            }
        }


        adapter = RecipeAdapter(emptyList()){ meal ->



            val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(meal.idMeal)
            findNavController().navigate(action)
        }

        binding.rvMeals.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMeals.adapter = adapter

        viewModel.meals.observe(viewLifecycleOwner) { list ->
            adapter.updateData(list)

            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
            binding.rvMeals.startAnimation(animation)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
