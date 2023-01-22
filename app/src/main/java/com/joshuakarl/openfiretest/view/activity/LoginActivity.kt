package com.joshuakarl.openfiretest.view.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.joshuakarl.openfiretest.R
import com.joshuakarl.openfiretest.api.OpenFireAPI
import com.joshuakarl.openfiretest.view.fragment.LoginFragment
import com.kusu.loadingbutton.LoadingButton
import dagger.hilt.android.AndroidEntryPoint
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var openFireAPI: OpenFireAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun launchHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        // We don't want to go back to Login
        finishAffinity()
        startActivity(intent)
    }

    fun showSnackbarError(error: String, length: Int = Snackbar.LENGTH_LONG) {
        val anchor = findViewById<CoordinatorLayout>(R.id.loginCoordinatorLayout)
        val snackbar = Snackbar.make(anchor, error, length)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()
        // Also log in console
        Log.e(TAG, error)
    }

    fun login(username: String, password: String): Boolean {
        var loginError = ""

        if (username.isEmpty() or username.isEmpty()) loginError =
            getString(R.string.cannot_be_blank_error, getString(R.string.prompt_email))
        if (password.isEmpty() or password.isEmpty()) loginError =
            getString(R.string.cannot_be_blank_error, getString(R.string.prompt_password))

        if (loginError.isEmpty() or loginError.isBlank()) {
            openFireAPI.login(username, password)
            return true
        }

        showSnackbarError(loginError)
        return false
    }

    fun showLoading(button: LoadingButton) {
        button.isClickable = false
        button.showLoading()
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}