package com.example.project.ui.main

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.FragmentSearchBinding
import android.text.TextWatcher
import androidx.recyclerview.widget.GridLayoutManager


class SearchFragment : Fragment() {

    private lateinit var adapter: RecipeAdapter
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipeAdapter(emptyList()) { meal ->
            val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(meal.idMeal)
            findNavController().navigate(action)
        }

        binding.rvSearchResults.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSearchResults.adapter = adapter

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.length >= 1) {
                    viewModel.search(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })



        viewModel.searchResults.observe(viewLifecycleOwner) { meals ->
            adapter.updateData(meals)
            binding.tvNoResults.visibility = if (meals.isEmpty()) View.VISIBLE else View.GONE


            }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        }
    }
}
