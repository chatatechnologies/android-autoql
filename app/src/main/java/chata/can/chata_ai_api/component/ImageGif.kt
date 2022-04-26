package chata.can.chata_ai_api.component

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import chata.can.chata_ai_api.R
import coil.Coil
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize

@Composable
fun ImageGif() {
	val context = LocalContext.current
	val imageLoader = ImageLoader.Builder(context)
		.componentRegistry {
			if (Build.VERSION.SDK_INT >= 28) {
				ImageDecoderDecoder(context)
			} else {
				add(GifDecoder())
			}
		}
		.build()
	Coil.setImageLoader(imageLoader)
	Image(
		painter = rememberImagePainter(
			imageLoader = imageLoader,
			data = R.drawable.gif_balls,
			builder = {
				size(OriginalSize)
			}),
		contentDescription = ""
	)

}

@Preview
@Composable
fun ImageGifPreview() {
	ImageGif()
}