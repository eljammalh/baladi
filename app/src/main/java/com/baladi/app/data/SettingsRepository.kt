package com.baladi.app.data

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.baladi.app.R
import com.baladi.app.data.model.Governorate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

enum class ThemeMode(val key: String, val nightMode: Int, @StringRes val labelRes: Int) {
    SYSTEM("system", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, R.string.settings_theme_system),
    LIGHT("light", AppCompatDelegate.MODE_NIGHT_NO, R.string.settings_theme_light),
    DARK("dark", AppCompatDelegate.MODE_NIGHT_YES, R.string.settings_theme_dark);

    companion object {
        fun fromKey(key: String?): ThemeMode = entries.firstOrNull { it.key == key } ?: SYSTEM
    }
}

enum class AppLanguage(val tag: String, @StringRes val labelRes: Int) {
    ARABIC("ar", R.string.settings_language_arabic),
    ENGLISH("en", R.string.settings_language_english),
}

/** Small on-device settings (theme, language, chosen governorate). */
@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val prefs = context.getSharedPreferences("baladi_settings", Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(ThemeMode.fromKey(prefs.getString(KEY_THEME, null)))
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _governorate = MutableStateFlow(Governorate.fromId(prefs.getString(KEY_GOVERNORATE, null)))
    /** null means "All of Jordan". */
    val governorate: StateFlow<Governorate?> = _governorate.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString(KEY_THEME, mode.key).apply()
        _themeMode.value = mode
        AppCompatDelegate.setDefaultNightMode(mode.nightMode)
    }

    fun setGovernorate(governorate: Governorate?) {
        prefs.edit().putString(KEY_GOVERNORATE, governorate?.id).apply()
        _governorate.value = governorate
    }

    fun currentLanguage(): AppLanguage {
        val tags = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        return if (tags.startsWith("en")) AppLanguage.ENGLISH else AppLanguage.ARABIC
    }

    fun setLanguage(language: AppLanguage) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language.tag))
    }

    /** On the very first launch the app opens in Arabic, whatever the phone language is. */
    fun applyDefaultLanguageOnce() {
        if (prefs.getBoolean(KEY_LANGUAGE_INITIALIZED, false)) return
        prefs.edit().putBoolean(KEY_LANGUAGE_INITIALIZED, true).apply()
        if (AppCompatDelegate.getApplicationLocales().isEmpty) {
            setLanguage(AppLanguage.ARABIC)
        }
    }

    private companion object {
        const val KEY_THEME = "theme_mode"
        const val KEY_GOVERNORATE = "governorate"
        const val KEY_LANGUAGE_INITIALIZED = "language_initialized"
    }
}
