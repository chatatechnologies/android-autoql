package chata.can.chata_ai.view.gif

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Movie
import android.util.AttributeSet
import android.view.View
import chata.can.chata_ai.R

class KGifView: View
{
	private var gifMovie: Movie ?= null
	private var movieWidth = 0
	private var movieHeight = 0
	private var mMovieStart = 0L

	constructor(context: Context): super(context)
	{
		init(context)
	}

	constructor(context: Context, attrs: AttributeSet): super(context, attrs)
	{
		init(context)
	}

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr)
	{
		init(context)
	}

	private fun init(context: Context)
	{
		isFocusable = true
		@SuppressLint("ResourceType")
		val gifInputStream = context.resources.openRawResource(R.drawable.gif_balls)
		gifMovie = Movie.decodeStream(gifInputStream).apply {
			movieWidth = width()
			movieHeight = height()
		}
	}

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
	{
		setMeasuredDimension(movieWidth, movieHeight)
	}

	override fun onDraw(canvas: Canvas?)
	{
		canvas?.let {
			val now = android.os.SystemClock.uptimeMillis()
			if (mMovieStart == 0L) mMovieStart = now

			gifMovie?.let { gifMovie ->
				var dur = gifMovie.duration()
				if (dur == 0) dur = 1000

				val realTime = ((now - mMovieStart) % dur).toInt()
				gifMovie.setTime(realTime)
				gifMovie.draw(it, 0f, 0f)
				invalidate()
			}
		}
	}
}