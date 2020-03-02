package chata.can.chata_ai.view.bubbles

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.*
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

class BubblesService: Service()
{
	private val binder = BubblesServiceBinder()
	private val bubbles = ArrayList<BubbleLayout>()
	private var bubblesTrash: BubbleTrashLayout ?= null
	private var windowManager: WindowManager ?= null
	private var layoutCoordinator: BubblesLayoutCoordinator ?= null

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
					bubble.notifyBubbleRemoved()
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
		val layoutParams = buildLayoutParamsForBubble(x, y)
		getWindowManager()?.let { bubble.setWindowManager(it) }
		bubble.setViewParams(layoutParams)
		layoutCoordinator?.let { bubble.setLayoutCoordinator(it) }
		bubbles.add(bubble)
		addViewToWindow(bubble)
	}

	fun addTrash(trashLayoutResourceId: Int)
	{
		if (trashLayoutResourceId != 0)
		{
			bubblesTrash = BubbleTrashLayout(this)
			windowManager?.let { bubblesTrash?.setWindowManager(it) }
			bubblesTrash?.setViewParams(buildLayoutParamsForTrash())
			bubblesTrash?.visibility = View.GONE
			LayoutInflater.from(this).inflate(trashLayoutResourceId, bubblesTrash, true)
			bubblesTrash?.let { addViewToWindow(it) }
			initializeLayoutCoordinator()
		}
	}

	private fun initializeLayoutCoordinator()
	{
		getWindowManager()?.let {
				itWindowManager ->
			bubblesTrash?.let {
					itBubblesTrash ->
				layoutCoordinator = BubblesLayoutCoordinator.Builder(this)
					.setWindowManager(itWindowManager)
					.setTrashView(itBubblesTrash)
					.build()
			}
		}
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

	private fun buildLayoutParamsForTrash(): WindowManager.LayoutParams
	{
		val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
		else
		{
			@Suppress("DEPRECATION")
			WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
		}

		val x = 0
		val y = 0
		val params = WindowManager.LayoutParams(
			WindowManager.LayoutParams.MATCH_PARENT,
			WindowManager.LayoutParams.MATCH_PARENT,
			type,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.TRANSPARENT)
		params.x = x
		params.y = y
		return params
	}

	fun removeBubble(bubble: BubbleLayout)
	{
		recycleBubble(bubble)
	}

	inner class BubblesServiceBinder: Binder() {
		fun getService(): BubblesService
		{
			return this@BubblesService
		}
	}
}