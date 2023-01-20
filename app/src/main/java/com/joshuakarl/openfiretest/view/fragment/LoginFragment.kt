package com.joshuakarl.openfiretest.view.fragment

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
                    // Proceed to Home Activity
                }
                override fun connectionClosed() { }
                override fun connectionClosedOnError(e: Exception?) {
                    val snackbar = Snackbar.make(view, "Error", Snackbar.LENGTH_INDEFINITE)
                    snackbar.show()
                }
            }
            openFireAPI.addConnectionListener(listener)

            login()
        }
    }

    private fun showRegisterFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        binding.root.findNavController().navigate(action)
    }

    private fun login() {
        var loginError = ""

        val username = binding.textInputLayout.editText!!.text.toString()
        if (username.isEmpty() or username.isEmpty()) loginError =
            getString(R.string.cannot_be_blank_error, getString(R.string.prompt_email))

        val password = binding.textInputLayout2.editText!!.text.toString()
        if (password.isEmpty() or password.isEmpty()) loginError =
            getString(R.string.cannot_be_blank_error, getString(R.string.prompt_password))

        if (loginError.isEmpty() or loginError.isBlank()) {
            openFireAPI.login(username, password)
            disable(binding.SignInButton)
        } else showSnackbarError(loginError)
    }

    private fun showSnackbarError(error: String, length: Int = Snackbar.LENGTH_LONG) {
        val snackbar = Snackbar.make(binding.root, error, length)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()
        // Also log in console
        Log.e(TAG, error)
    }

    private fun disable(view: View) {
        view.isClickable = false
        view.alpha = 0.5f
    }

    companion object {
        private const val TAG = "LoginFragment"
    }

}