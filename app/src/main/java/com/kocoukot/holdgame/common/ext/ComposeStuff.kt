package com.kocoukot.holdgame.common.ext

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@ExperimentalCoilApi
@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier,
) {

    val painter = rememberImagePainter(
        data = url,
        builder = {
//            error(R.drawable.ic_busines_placeholder)
//            placeholder(drawableResId = R.drawable.ic_place_photo_placeholder)
            //todo
        },
    )

    Image(
        painter = painter,
        contentDescription = "contentDescription",
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}