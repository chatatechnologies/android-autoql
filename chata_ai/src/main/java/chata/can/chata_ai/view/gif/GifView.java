package chata.can.chata_ai.view.gif;

import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import chata.can.chata_ai.R;

public class GifView extends View
{
	private Movie gifMovie;
	private int movieWidth, movieHeight;
	private long mMovieStart;

	public GifView(Context context)
	{
		super(context);
		init(context);
	}

	public GifView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public GifView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context)
	{
		setFocusable(true);
		@SuppressLint("ResourceType") InputStream gifInputStream = context.getResources()
			.openRawResource(R.drawable.gif_balls);

		gifMovie = Movie.decodeStream(gifInputStream);
		movieWidth = gifMovie.width();
		movieHeight = gifMovie.height();
		setBackgroundColor(Color.GREEN);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		setMeasuredDimension(movieWidth, movieHeight);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		long now = android.os.SystemClock.uptimeMillis();
		if (mMovieStart == 0)
		{
			mMovieStart = now;
		}

		if (gifMovie != null) {

			int dur = gifMovie.duration();
			if (dur == 0) {
				dur = 1000;
			}
			int relTime = (int)((now - mMovieStart) % dur);
			gifMovie.setTime(relTime);
			gifMovie.draw(canvas, 0, 0);
			invalidate();
		}
	}
}