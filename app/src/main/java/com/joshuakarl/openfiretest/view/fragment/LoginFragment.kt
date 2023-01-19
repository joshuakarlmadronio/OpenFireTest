package com.joshuakarl.openfiretest.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.joshuakarl.openfiretest.R
import com.joshuakarl.openfiretest.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SignUpTextView.setOnClickListener { showRegisterFragment() }
    }

    private fun showRegisterFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        binding.root.findNavController().navigate(action)
    }

}