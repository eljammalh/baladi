package com.baladi.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.baladi.app.R

// Tajawal: a clean, modern Arabic + Latin typeface (downloaded into res/font by the build).
val Tajawal = FontFamily(
    Font(R.font.tajawal_regular, FontWeight.Normal),
    Font(R.font.tajawal_medium, FontWeight.Medium),
    Font(R.font.tajawal_bold, FontWeight.Bold),
    Font(R.font.tajawal_extrabold, FontWeight.ExtraBold),
)

private fun TextStyle.baladi(weight: FontWeight? = null): TextStyle = copy(
    fontFamily = Tajawal,
    fontWeight = weight ?: fontWeight,
    // Arabic script needs a little more vertical room than Latin.
    lineHeight = if (lineHeight.isSp) (lineHeight.value * 1.08f).sp else lineHeight,
)

private val base = Typography()

val BaladiTypography = Typography(
    displayLarge = base.displayLarge.baladi(FontWeight.ExtraBold),
    displayMedium = base.displayMedium.baladi(FontWeight.ExtraBold),
    displaySmall = base.displaySmall.baladi(FontWeight.Bold),
    headlineLarge = base.headlineLarge.baladi(FontWeight.ExtraBold),
    headlineMedium = base.headlineMedium.baladi(FontWeight.Bold),
    headlineSmall = base.headlineSmall.baladi(FontWeight.Bold),
    titleLarge = base.titleLarge.baladi(FontWeight.Bold),
    titleMedium = base.titleMedium.baladi(FontWeight.Bold),
    titleSmall = base.titleSmall.baladi(FontWeight.Medium),
    bodyLarge = base.bodyLarge.baladi(),
    bodyMedium = base.bodyMedium.baladi(),
    bodySmall = base.bodySmall.baladi(),
    labelLarge = base.labelLarge.baladi(FontWeight.Bold),
    labelMedium = base.labelMedium.baladi(FontWeight.Medium),
    labelSmall = base.labelSmall.baladi(FontWeight.Medium),
)
