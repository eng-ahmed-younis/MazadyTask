package com.example.mazadytask.presentation.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

internal val lightColors = AppColors(
    // Backgrounds
    screenBackground = Color(0xFFFFFFFF),
    cardBackground = Color(0xFFE5E5EA),
    topBarBackground = Color(0xFFDCDCDC),
    surfaceBackground = Color(0xFFF2F2F7),

    // Text Colors
    primaryText = Color(0xFF000000),
    secondaryText = Color(0xFF666666),
    tertiaryText = Color(0xFF999999),

    // UI Elements
    iconTint = Color(0xFF666666),
    divider = Color(0xFFE4E4E5),

    // Brand Colors
    primaryBlue = Color(0xFF007AFF),

    // Special
    white = Color(0xFFFFFFFF),
    black = Color(0xFF000000),
    transparent = Color(0x00000000),
)

internal val darkColors = AppColors(
    // Backgrounds
    screenBackground = Color(0xFF1C1C1E),
    cardBackground = Color(0xFF2C2C2E),
    topBarBackground = Color(0xFF414141),
    surfaceBackground = Color(0xFF000000),

    // Text Colors
    primaryText = Color(0xFFFFFFFF),
    secondaryText = Color(0xFFAAAAAA),
    tertiaryText = Color(0xFF999999),

    // UI Elements
    iconTint = Color(0xFFAAAAAA),
    divider = Color(0xFF3C3C43),

    // Brand Colors
    primaryBlue = Color(0xFF0A84FF),

    // Special
    white = Color(0xFFFFFFFF),
    black = Color(0xFF000000),
    transparent = Color(0x00000000),
)

@Stable
data class AppColors(
    // Backgrounds
    val screenBackground: Color,
    val cardBackground: Color,
    val topBarBackground: Color,
    val surfaceBackground: Color,

    // Text Colors
    val primaryText: Color,
    val secondaryText: Color,
    val tertiaryText: Color,

    // UI Elements
    val iconTint: Color,
    val divider: Color,

    // Brand Colors
    val primaryBlue: Color,

    // Special
    val white: Color,
    val black: Color,
    val transparent: Color,
)