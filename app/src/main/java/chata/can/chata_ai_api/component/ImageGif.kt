package chata.can.chata_ai_api.component

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import chata.can.chata_ai_api.R
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

@Composable
fun ImageGif() {
	val context = LocalContext.current
	val imageLoader = ImageLoader.Builder(context)

		.components {
			if (Build.VERSION.SDK_INT >= 28) {
				ImageDecoderDecoder.Factory()
			} else {
				add(GifDecoder.Factory())
			}
		}
		.build()

	Image(
		painter = rememberImagePainter(data = R.drawable.gif_balls, imageLoader = imageLoader),
		contentDescription = ""
	)

}

@Preview
@Composable
fun ImageGifPreview() {
	ImageGif()
}