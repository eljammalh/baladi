package com.baladi.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = Olive40,
    onPrimary = Color.White,
    primaryContainer = Olive90,
    onPrimaryContainer = Olive10,
    secondary = Wheat40,
    onSecondary = Color.White,
    secondaryContainer = Wheat90,
    onSecondaryContainer = Wheat10,
    tertiary = Terracotta40,
    onTertiary = Color.White,
    tertiaryContainer = Terracotta90,
    onTertiaryContainer = Terracotta10,
    background = Cream,
    onBackground = Ink,
    surface = SurfaceLight,
    onSurface = Ink,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    surfaceContainerLowest = SurfaceLight,
    surfaceContainerLow = ContainerLowLight,
    surfaceContainer = ContainerLight,
    surfaceContainerHigh = ContainerHighLight,
    surfaceContainerHighest = SurfaceVariantLight,
    error = ErrorLight,
)

private val DarkColors = darkColorScheme(
    primary = Olive80,
    onPrimary = Olive20,
    primaryContainer = Olive30,
    onPrimaryContainer = Olive90,
    secondary = Wheat80,
    onSecondary = Wheat20,
    secondaryContainer = Wheat30,
    onSecondaryContainer = Wheat90,
    tertiary = Terracotta80,
    onTertiary = Terracotta20,
    tertiaryContainer = Terracotta30,
    onTertiaryContainer = Terracotta90,
    background = Night,
    onBackground = OnNight,
    surface = SurfaceDark,
    onSurface = OnNight,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    surfaceContainerLowest = Night,
    surfaceContainerLow = ContainerLowDark,
    surfaceContainer = ContainerDark,
    surfaceContainerHigh = ContainerHighDark,
    surfaceContainerHighest = SurfaceVariantDark,
    error = ErrorDark,
)

val BaladiShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

@Composable
fun BaladiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = BaladiTypography,
        shapes = BaladiShapes,
        content = content,
    )
}
