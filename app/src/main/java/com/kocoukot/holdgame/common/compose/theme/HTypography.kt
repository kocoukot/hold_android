package com.kocoukot.holdgame.common.compose.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


data class HTypography internal constructor(
//
    val titleHeader: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.W600,
        color = Color(0xFFFFFFFF)
    ),

    val subtitleHeader: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 34.sp,
        fontWeight = FontWeight.W600,
        color = Color(0xFFFFFFFF)
    ),

    val titleSubHeader: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.W400,
        color = Color(0xFFFFFFFF)
    ),

    val commonText: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.W400,
        color = Color(0xFFFFFFFF)
    ),

    val textGray: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.W400,
        color = Color(0x80FFFFFF)
    ),
)