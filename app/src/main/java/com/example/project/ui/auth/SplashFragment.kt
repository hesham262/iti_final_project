package com.example.project.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.project.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startContainer = view.findViewById<View>(R.id.startContainer)


//        startContainer.setOnClickListener {
//            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
//        }
    }
}
