package com.example.cameracompose.ui.components.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.cameracompose.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.io.File
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun GalleryDesign(
    images: List<File>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = images.size)

    HorizontalPager(state = pagerState, modifier = modifier) { page ->
        val image = images[page]

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(

                Modifier
                    .size(370.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffset
                                ).absoluteValue

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = image,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = stringResource(R.string.gallery_image),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(images.size) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(20.dp)
                    )
                }
            }
        }
    }
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction
}
