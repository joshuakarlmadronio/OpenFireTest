package com.joshuakarl.openfiretest.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.joshuakarl.openfiretest.R
import com.joshuakarl.openfiretest.api.OpenFireAPI
import com.joshuakarl.openfiretest.databinding.FragmentRegisterBinding
import com.joshuakarl.openfiretest.view.activity.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    @Inject
    lateinit var openFireAPI: OpenFireAPI

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.LoginFragmentTextView.setOnClickListener { showLoginFragment() }
        binding.RegisterButton.setOnClickListener {
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

            val username = binding.usernameTextInputLayout.editText!!.text.toString()
            val password = binding.passwordTextInputLayout.editText!!.text.toString()
            val confirmPassword = binding.confirmPasswordTextInputLayout.editText!!.text.toString()
            // No listener?
            if (register(username, password, confirmPassword)) {
                if ((activity as? LoginActivity)?.login(username, password) == true)
                    (activity as? LoginActivity)?.showLoading(binding.RegisterButton)
            }
        }
    }

    private fun showLoginFragment() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        binding.root.findNavController().navigate(action)
    }

    private fun register(username: String, password: String, confirmPassword: String): Boolean {
        // Validate
        if (username.isEmpty() or username.isEmpty()) {
            (activity as? LoginActivity)?.showSnackbarError(
                getString(R.string.cannot_be_blank_error, getString(R.string.prompt_email)))
            return false
        }
        if (password.isEmpty() or password.isEmpty() or confirmPassword.isEmpty() or confirmPassword.isBlank()) {
            (activity as? LoginActivity)?.showSnackbarError(
                getString(R.string.cannot_be_blank_error, getString(R.string.prompt_password)))
            return false
        }
        if (password.compareTo(confirmPassword) != 0) {
            (activity as? LoginActivity)?.showSnackbarError(getString(R.string.passwords_do_not_match))
            return false
        }

        return openFireAPI.register(username, password)
    }

    companion object {
        private const val TAG = "RegisterFragment"
    }
}