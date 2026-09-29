package com.odc.demarchesfacile.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorSchemeClair = lightColorScheme(
    primary = OrangePrincipal,
    onPrimary = Color.White,
    secondary = OrangeFonce,
    background = GrisFond,
    surface = Color.White,
    error = RougeErreur,
    onBackground = GrisTexte,
    onSurface = GrisTexte
)

private val ColorSchemeSombre = darkColorScheme(
    primary = OrangePrincipal,
    secondary = OrangeClair,
    error = RougeErreur
)

@Composable
fun DemarchesFacileTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) ColorSchemeSombre else ColorSchemeClair
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
