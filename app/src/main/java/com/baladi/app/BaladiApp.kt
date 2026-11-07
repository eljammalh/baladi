package com.baladi.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.baladi.app.data.SettingsRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaladiApp : Application() {

    @Inject
    lateinit var settings: SettingsRepository

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(settings.themeMode.value.nightMode)
    }
}
