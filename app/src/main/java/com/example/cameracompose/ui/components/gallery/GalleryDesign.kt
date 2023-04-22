package com.example.cameracompose.ui.components.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.cameracompose.R
import java.io.File


@Composable
fun GalleryDesign(
    images: List<File>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    columns: Int = 2,
    spacing: Dp = 8.dp
) {
    val imageModifier = Modifier
        .padding(spacing / 2)
        .fillMaxWidth(1f / columns)
        .aspectRatio(1f)

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.padding(spacing / 2)
    ) {
        items(images.size) { index ->
            val image = images[index]
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = imageModifier
                    .clickable { navController.navigate("detail/${image.name}") }
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = image,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = stringResource(id = R.string.gallery_image),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview
@Composable
fun GalleryDesignPreview() {
    val images = listOf(
        File("image1.jpg"),
        File("image2.jpg"),
        File("image3.jpg"),
        File("image3.jpg")
    )
    Column(Modifier.fillMaxSize()) {
        GalleryDesign(images = images, navController = rememberNavController())
    }
}

