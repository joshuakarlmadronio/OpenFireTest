package com.joshuakarl.openfiretest.di

import android.content.Context
import android.content.SharedPreferences
import com.joshuakarl.openfiretest.BuildConfig
import com.joshuakarl.openfiretest.R
import com.joshuakarl.openfiretest.api.OpenFireAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.preference_file_key, BuildConfig.APPLICATION_ID), Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesOpenFireAPI(pref: SharedPreferences, connection: XMPPTCPConnection): OpenFireAPI =
        OpenFireAPI(pref, connection)
}