package com.joshuakarl.openfiretest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.jivesoftware.smack.ConnectionConfiguration
import javax.inject.Singleton
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import java.net.InetAddress

@Module
@InstallIn(SingletonComponent::class)
object OpenFireModule {
    private const val HOST = "192.168.1.5"
    private const val PORT = 5222
    private const val DOMAIN = "joshu-pc"

    @Provides
    @Singleton
    fun providesOpenFire(): XMPPTCPConnection {
        val config = XMPPTCPConnectionConfiguration.builder()
            .setHostAddress(InetAddress.getByName(HOST))
            .setPort(PORT)
            .setXmppDomain(DOMAIN)
            .setSendPresence(true)
            // Disabled for now, we are just testing
            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
            .build()

        return XMPPTCPConnection(config)
    }
}