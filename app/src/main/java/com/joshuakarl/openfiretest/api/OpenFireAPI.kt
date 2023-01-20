package com.joshuakarl.openfiretest.api

import android.content.SharedPreferences
import android.util.Log
import com.joshuakarl.openfiretest.model.Account
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import javax.inject.Inject


class OpenFireAPI @Inject constructor(
    private val pref: SharedPreferences,
    private val connection: XMPPTCPConnection)
{
    fun login(username: String, password: String) {
        connect()

        connection.login(username, password)

        val presence = Presence(Presence.Type.available)
        // If user is away, dnd, etc
        presence.mode = pref.getString(Account.LAST_PRESENCE_MODE, Presence.Mode.available.name)
            ?.let { Presence.Mode.valueOf(it) }
        presence.status = pref.getString(Account.LAST_PRESENCE_STATUS, "")
        connection.sendStanza(presence)

        Log.d(TAG, "Connecting...")
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