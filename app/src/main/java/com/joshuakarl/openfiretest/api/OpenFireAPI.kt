package com.joshuakarl.openfiretest.api

import android.content.SharedPreferences
import android.util.Log
import com.joshuakarl.openfiretest.model.Account
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smackx.iqregister.AccountManager
import org.jxmpp.jid.parts.Localpart
import javax.inject.Inject


class OpenFireAPI @Inject constructor(
    private val pref: SharedPreferences,
    private val connection: XMPPTCPConnection,
    private val accountManager: AccountManager)
{
    fun login(username: String, password: String) {
        try {
            connect()

            connection.login(username, password)

            val presence = Presence(Presence.Type.available)
            // If user is away, dnd, etc
            presence.mode = pref.getString(Account.LAST_PRESENCE_MODE, Presence.Mode.available.name)
                ?.let { Presence.Mode.valueOf(it) }
            presence.status = pref.getString(Account.LAST_PRESENCE_STATUS, "")
            connection.sendStanza(presence)

            Log.d(TAG, "Connecting...")
        } catch (e: Exception) {
            // TODO just print for now
            e.printStackTrace()
        }
    }

    fun register(username: String, password: String): Boolean {
        return try {
            connect()

            accountManager.sensitiveOperationOverInsecureConnection(true)
            accountManager.createAccount(Localpart.from(username), password)

            Log.d(TAG, "Creating account for $username...")

            true
        } catch (e: Exception) {
            // TODO just print for now
            e.printStackTrace()

            false
        }
    }

    fun addConnectionListener(listener: ConnectionListener) {
        connection.addConnectionListener(listener)
    }

    private fun connect() {
        if (!connection.isConnected) connection.connect()
    }

    private fun disconnect() {
        if (connection.isConnected) connection.disconnect()
    }

    companion object {
        private const val TAG = "OpenFireAPI"
    }
}