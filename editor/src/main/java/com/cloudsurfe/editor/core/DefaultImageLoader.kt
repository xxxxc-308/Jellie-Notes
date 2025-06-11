package com.cloudsurfe.editor.core

import androidx.compose.runtime.Composable

public object DefaultImageLoader : ImageLoader{

    @Composable
    override fun load(model: Any): ImageData? = null

}