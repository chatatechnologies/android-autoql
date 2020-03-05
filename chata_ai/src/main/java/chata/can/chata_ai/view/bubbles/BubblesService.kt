package chata.can.chata_ai.view.bubbles

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.*
import android.view.Gravity
import android.view.WindowManager

class BubblesService: Service()
{
	private val binder = BubblesServiceBinder()
	private val bubbles = ArrayList<BubbleLayout>()
	private var windowManager: WindowManager ?= null

	override fun onBind(intent: Intent?): IBinder? = binder

	override fun onUnbind(intent: Intent?): Boolean
	{
		for (bubble in bubbles)
		{
			recycleBubble(bubble)
		}
		bubbles.clear()
		return super.onUnbind(intent)
	}

	private fun recycleBubble(bubble: BubbleLayout)
	{
		Handler(Looper.getMainLooper()).post {
			getWindowManager()?.removeView(bubble)
			for (cachedBubble in bubbles)
			{
				if (cachedBubble == bubble)
				{
					bubbles.remove(cachedBubble)
					break
				}
			}
		}
	}

	private fun getWindowManager(): WindowManager?
	{
		if (windowManager == null)
		{
			windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
		}
		return windowManager
	}

	fun addBubble(bubble: BubbleLayout, x: Int, y: Int)
	{
		getWindowManager()?.let { bubble.setWindowManager(it) }
		bubble.setViewParams(buildLayoutParamsForBubble(x, y))
		bubbles.add(bubble)
		addViewToWindow(bubble)
	}

	private fun addViewToWindow(view: BubbleBaseLayout)
	{
		Handler(Looper.getMainLooper()).post { getWindowManager()?.addView(view, view.getViewParams()) }
	}

	private fun buildLayoutParamsForBubble(x: Int, y: Int): WindowManager.LayoutParams
	{
		val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
		else
		{
			@Suppress("DEPRECATION")
			WindowManager.LayoutParams.TYPE_PHONE
		}

		val params = WindowManager.LayoutParams(
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.WRAP_CONTENT,
			type,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.TRANSPARENT)
		params.gravity = Gravity.TOP or Gravity.START
		params.x = x
		params.y = y
		return params
	}

	inner class BubblesServiceBinder: Binder() {
		fun getService(): BubblesService
		{
			return this@BubblesService
		}
	}
}