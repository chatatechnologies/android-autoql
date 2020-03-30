package chata.can.chata_ai.view.circle

import android.graphics.Bitmap
import java.io.InputStream
import java.util.*

class GifDecoder
{
	val STATUS_OK = 0
	val STATUS_FORMAT_ERROR = 1
	val STATUS_OPEN_ERROR = 2

	protected val MAX_STACK_SIZE = 4096
	protected var input: InputStream ?= null
	protected var status = 0
	protected var width = 0
	protected var height = 0
	protected var gctFlag = false
	protected var gctSize = 0
	protected var loopCount = 0
	protected lateinit var gct: IntArray
	protected lateinit var lct: IntArray
	protected lateinit var act: IntArray
	protected var bgIndex = 0
	protected var bgColor = 0
	protected var lastBgColor = 0
	protected var pixelAspect = 0
	protected var lctFlag = false
	protected var interlace = false
	protected var lctSize = 0

	protected var ix = 0
	protected var iy = 0
	protected var iw = 0
	protected var ih = 0

	protected var lrx = 0
	protected var lry = 0
	protected var lrw = 0
	protected var lrh = 0

	protected var image: Bitmap ?= null
	protected var lastBitmap: Bitmap ?= null
	protected var block = ByteArray(256)
	protected var blockSize = 0
	protected var dispose = 0
	protected var lastDispose = 0
	protected var transparency = false
	protected var delay = 0
	protected var transIndex = 0

	protected var prefix: ShortArray ?= null
	protected var suffix: ByteArray ?= null
	protected var pixelStack: ByteArray ?= null
	protected var pixels: ByteArray ?= null
	protected var frames: Vector<GifFrame>? = null
	protected var frameCount = 0

	class GifFrame(val image: Bitmap, val delay: Int)
	{

	}

	fun getDelay(n: Int): Int
	{
		delay = -1
		if ( (n >= 0) && (n < frameCount) )
		{
			delay = frames?.elementAt(n)?.delay ?: 0
		}
		return delay
	}

	//fun getFrameCount() = frameCount

	fun getBitmap(): Bitmap
	{
		return getFrame(0)
	}

	//fun getLoopCount() = loopCount

	protected fun setPixels()
	{
		val dest = IntArray(width * height)
		if (lastDispose > 0)
		{
			if (lastDispose == 3)
			{
				//use image before last
				val n = frameCount - 2
				if (n > 0)
				{
					lastBitmap = getFrame(n - 1)
				}
				else
				{
					lastBitmap = null
				}
			}

			if (lastBitmap != null)
			{
				lastBitmap?.getPixels(dest, 0, width,0,0, width, height)
				if (lastDispose == 2)
				{
					var c = 0
					if (!transparency)
					{
						c = lastBgColor
					}
					for (i in 0 until lrh)
					{
						val n1 = (lry + i) * width + lrx
						val n2 = n1 + lrw

						for (k in n1 until n2)
						{
							dest[k] = c
						}
					}
				}
			}
		}

		var pass = 1
		var inc = 8
		var iline = 0
		for (i in 0 until ih) {
			var line = i
			if (interlace) {
				if (iline >= ih) {
					pass++
					when (pass) {
						2 -> iline = 4
						3 -> {
							iline = 2
							inc = 4
						}
						4 -> {
							iline = 1
							inc = 2
						}
						else -> {
						}
					}
				}
				line = iline
				iline += inc
			}
			line += iy
			if (line < height) {
				val k = line * width
				var dx = k + ix // start of line in dest
				var dlim = dx + iw // end of dest line
				if (k + width < dlim) {
					dlim = k + width // past dest edge
				}
				var sx = i * iw // start of line in source
				while (dx < dlim) {
					// map color and insert in destination
					val index = pixels!![sx++].toInt() and 0xff
					val c = act[index]
					if (c != 0) {
						dest[dx] = c
					}
					dx++
				}
			}
		}
		image = Bitmap.createBitmap(dest, width, height, Bitmap.Config.ARGB_4444)
	}

	fun getFrame(n: Int): Bitmap?
	{
		if (frameCount <= 0)
		{
			return null
		}
		val newN = n % frameCount

		return frames?.elementAt(newN)?.image
	}

	fun read(input: InputStream?): Int
	{
		init()
		if (input != null)
		{
			this.input = input
			readHeader()
			if (!err())
			{
				readContents()
				if (frameCount < 0)
				{
					status = STATUS_FORMAT_ERROR
				}
			}
		}
		else
		{
			status = STATUS_OPEN_ERROR
		}
		try
		{
			input?.close()
		}
		catch (ex: Exception)
		{

		}
		return status
	}

	protected fun decodeBitmapData()
	{
		val nullCode = -1
		val npix = iw * ih
		var available = 0
		var clear = 0
		var code_mask = 0
		var code_size = 0
		var end_of_information = 0
		var in_code = 0
		var old_code = 0
		var bits = 0
		var code = 0
		var count = 0
		var i = 0
		var datum = 0
		var data_size = 0
		var first = 0
		var top = 0
		var bi = 0
		var pi = 0
		val lengthPixels = pixels?.size ?: 0
		if ( (pixels == null) || (lengthPixels < npix))
		{
			pixels = ByteArray(npix)
		}

		if (prefix == null)
		{
			prefix = ShortArray(MAX_STACK_SIZE)
		}
		if (suffix == null)
		{
			suffix = ByteArray(MAX_STACK_SIZE)
		}
		if (pixelStack == null)
		{
			pixelStack = ByteArray(MAX_STACK_SIZE + 1)
		}
		data_size = read()
	}
}