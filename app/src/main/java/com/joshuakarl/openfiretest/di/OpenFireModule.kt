package com.joshuakarl.openfiretest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.XMPPConnection
import javax.inject.Singleton
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.iqregister.AccountManager
import java.net.InetAddress

@Module
@InstallIn(SingletonComponent::class)
object OpenFireModule {
    private const val HOST = "192.168.254.105"
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

    @Provides
    @Singleton
    fun providesAccountManager(connection: XMPPTCPConnection): AccountManager =
        AccountManager.getInstance(connection)
}