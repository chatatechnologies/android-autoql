package chata.can.chata_ai.view.gif

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import java.io.InputStream

class GifDecoderView: AppCompatImageView
{
	private var mIsPlayingGif = false
	//private var mGifDecoder: GifDecoder ?= null
	private var mTmpBitmap: Bitmap ?= null
	private var mHandler = Handler()

	private var mUpdateResults = object: Runnable
	{
		override fun run()
		{
			mTmpBitmap?.let {
				if (!it.isRecycled)
				{
					//GifDecoderView.this.setImageBitmap(mTmpBitmap)
				}
			}
		}
	}

	constructor(context: Context, stream: InputStream): super(context)
	{
		playGif(stream)
	}

	private fun playGif(stream: InputStream)
	{

	}

	constructor(context: Context): super(context)

	constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

	constructor(context: Context, attrs: AttributeSet, defStyle: Int)
		: super(context, attrs, defStyle)
}