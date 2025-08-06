package com.example.project.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.data.local.SharedPrefrence
import com.example.project.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = SharedPrefrence(requireContext())

        binding.btnRegister.setOnClickListener {


                val name = binding.etName.text.toString().trim()
                val email = binding.etEmailRegister.text.toString().trim()
                val password = binding.etPasswordRegister.text.toString().trim()

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter your data", Toast.LENGTH_SHORT).show()
                } else {
                    sharedPref.saveUser(email, password)
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment )
                }
            }

        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
