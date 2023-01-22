package com.joshuakarl.openfiretest.view.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.joshuakarl.openfiretest.R
import com.joshuakarl.openfiretest.api.OpenFireAPI
import com.joshuakarl.openfiretest.databinding.FragmentLoginBinding
import com.joshuakarl.openfiretest.view.activity.LoginActivity
import com.kusu.loadingbutton.LoadingButton
import dagger.hilt.android.AndroidEntryPoint
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    @Inject
    lateinit var openFireAPI: OpenFireAPI

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
        binding.SignInButton.setOnClickListener {
            // Listen on connection events
            val listener = object: ConnectionListener {
                override fun connected(connection: XMPPConnection?) {
                    Log.d(TAG, "Connected to the XMPP server")
                }
                override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {
                    Log.d(TAG, "Login auth successful")
                    (activity as? LoginActivity)?.launchHomeActivity()
                }
                override fun connectionClosed() { }
                override fun connectionClosedOnError(e: Exception?) {
                    e?.printStackTrace()
                    (activity as? LoginActivity)?.showSnackbarError("ERROR")
                }
            }
            openFireAPI.addConnectionListener(listener)

            val username = binding.textInputLayout.editText!!.text.toString()
            val password = binding.textInputLayout2.editText!!.text.toString()
            if ((activity as? LoginActivity)?.login(username, password) == true)
                (activity as? LoginActivity)?.showLoading(binding.SignInButton)
        }
    }

    private fun showRegisterFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        binding.root.findNavController().navigate(action)
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}