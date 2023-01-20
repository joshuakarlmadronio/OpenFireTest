package com.joshuakarl.openfiretest.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joshuakarl.openfiretest.R
import com.joshuakarl.openfiretest.api.OpenFireAPI
import dagger.hilt.android.AndroidEntryPoint
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}