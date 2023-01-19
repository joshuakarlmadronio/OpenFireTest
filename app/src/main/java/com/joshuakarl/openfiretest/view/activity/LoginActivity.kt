package com.joshuakarl.openfiretest.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joshuakarl.openfiretest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}