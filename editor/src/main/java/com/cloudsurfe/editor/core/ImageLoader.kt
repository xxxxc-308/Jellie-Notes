package com.cloudsurfe.editor.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

public interface ImageLoader{

    @Composable
    public fun load(model: Any): ImageData?
}

public val LocalImageLoader: ProvidableCompositionLocal<ImageLoader> = staticCompositionLocalOf {
    DefaultImageLoader
}

public class ImageData(
    public val painter: Painter,
    public val contentDescription: String? = null,
    public val alignment: Alignment = Alignment.Center,
    public val contentScale: ContentScale = ContentScale.Fit,
    public val modifier: Modifier = Modifier.fillMaxWidth()
)