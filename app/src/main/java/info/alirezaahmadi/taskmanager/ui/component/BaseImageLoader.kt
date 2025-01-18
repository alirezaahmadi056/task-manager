package info.alirezaahmadi.taskmanager.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import info.alirezaahmadi.taskmanager.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BaseImageLoader(
    modifier: Modifier = Modifier,
    model:Any,
    contentScale: ContentScale
) {
    GlideImage(
        model = model,
        modifier = modifier,
        contentDescription = "",
        contentScale = contentScale
    ){
        it.placeholder(R.drawable.image_placeholder)
    }
}