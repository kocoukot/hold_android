package com.hold.common.compose.theme

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
        color = Color(0xFFFFFFFF)
    ),
//
//    val robotoRegular16: TextStyle = TextStyle(
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        color = Color(0xFF3C4042),
//        fontFamily = FontFamily(Font(R.font.roboto_regular)),
//        fontWeight = FontWeight.Normal
//    ),
//
//    val robotoMedium: TextStyle = TextStyle(
//        fontSize = 14.sp,
//        lineHeight = 16.sp,
//        color = Color.White,
//        letterSpacing = 0.02.sp,
//        fontWeight = FontWeight.Medium,
//        fontFamily = FontFamily(Font(R.font.roboto_medium)),
//    ),
//
//    val robotoMediumTitle: TextStyle = TextStyle(
//        fontSize = 17.sp,
//        lineHeight = 20.sp,
//        color = Color(0xFF3C4042),
//        letterSpacing = -(0.02.sp),
//        fontWeight = FontWeight.Medium,
//        fontFamily = FontFamily(Font(R.font.roboto_medium)),
//    ),
//
//    val robotoMediumBlack13: TextStyle = TextStyle(
//        fontSize = 13.sp,
//        lineHeight = 16.sp,
//        color = Color(0xFF3C4042),
//        letterSpacing = 0.02.sp,
//        fontWeight = FontWeight.Medium,
//        fontFamily = FontFamily(Font(R.font.roboto_medium)),
//    ),
//
//    val robotoBold: TextStyle = TextStyle(
//        color = Color.White,
//        fontFamily = FontFamily(Font(R.font.roboto_bold)),
//        fontSize = 16.sp,
//        lineHeight = 32.sp,
//        fontWeight = FontWeight.Bold,
//    ),
//
//    val robotoBoldTitle: TextStyle = TextStyle(
//        color = Color.White,
//        fontFamily = FontFamily(Font(R.font.roboto_bold)),
//        fontSize = 16.sp,
//        fontWeight = FontWeight.Bold,
//        lineHeight = 24.sp,
//    ),
//
//    val toolBarTitleStyle: TextStyle = TextStyle(
//        color = Color(0xFF3C4042),
//        fontFamily = FontFamily(Font(R.font.roboto_medium)),
//        fontSize = 17.sp,
//        fontWeight = FontWeight.Medium,
//        lineHeight = 20.sp,
//        letterSpacing = -(0.02.sp)
//    ),
//
//
)