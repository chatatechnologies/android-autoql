package chata.can.chata_ai.view.bubbles

import android.view.View
import android.view.WindowManager

class BubblesLayoutCoordinator
{
	companion object {
		private var INSTANCE: BubblesLayoutCoordinator? = null
		fun getInstance(): BubblesLayoutCoordinator?
		{
			if (INSTANCE == null)
			{
				INSTANCE = BubblesLayoutCoordinator()
			}
			return INSTANCE
		}
	}

	private var trashView: BubbleTrashLayout ?= null
	private var windowManager: WindowManager ?= null
	private var bubblesService: BubblesService ?= null



	fun notifyBubblePositionChanged(bubble: BubbleLayout, x: Int, y: Int)
	{
		trashView?.let {
			it.visibility = View.VISIBLE
			if (checkIfBubbleIsOverTrash(bubble))
			{
				it.applyMagnetism()
				it.vibrate()
				applyTrashMagnetismToBubble(bubble)
			}
			else
			{
				it.releaseMagnetism()
			}
		}
	}

	private fun applyTrashMagnetismToBubble(bubble: BubbleLayout)
	{
		getTrashContent()?.let {
			val trashCenterX = it.left + (it.measuredWidth / 2)
			val trashCenterY = it.top + (it.measuredHeight / 2)
			val x = (trashCenterX - (bubble.measuredWidth) / 2)
			val y = (trashCenterY - (bubble.measuredHeight) / 2)
			bubble.getViewParams()?.x = x
			bubble.getViewParams()?.y = y
			windowManager?.updateViewLayout(bubble, bubble.getViewParams())
		}
	}

	private fun checkIfBubbleIsOverTrash(bubble: BubbleLayout): Boolean
	{
		var result = false
		if (trashView?.visibility == View.VISIBLE)
		{
			getTrashContent()?.let {
					trashContentView ->
				val trashWidth = trashContentView.measuredWidth
				val trashHeight = trashContentView.measuredHeight
				val trashLeft = (trashContentView.left - (trashWidth / 2))
				val trashRight = (trashContentView.left + trashWidth + (trashWidth / 2))
				val trashTop = (trashContentView.top - (trashHeight / 2))
				val trashBottom = (trashContentView.top + trashHeight + (trashHeight / 2))
				val bubbleWidth = bubble.measuredWidth
				val bubbleHeight = bubble.measuredHeight
				val bubbleLeft = bubble.getViewParams()?.x ?: 0
				val bubbleRight = bubbleLeft + bubbleWidth
				val bubbleTop = bubble.getViewParams()?.y ?: 0
				val bubbleBottom = bubbleTop + bubbleHeight
				if (bubbleLeft >= trashLeft && bubbleRight <= trashRight)
				{
					if (bubbleTop >= trashTop && bubbleBottom <= trashBottom)
					{
						result = true
					}
				}
			}
		}
		return result
	}

	fun notifyBubbleRelease(bubble: BubbleLayout)
	{
		trashView?.let {
			if (checkIfBubbleIsOverTrash(bubble))
			{
				bubblesService?.removeBubble(bubble)
			}
			it.visibility = View.GONE
		}
	}

	class Builder(service: BubblesService) {
		private var layoutCoordinator: BubblesLayoutCoordinator ?= null

		init {
			layoutCoordinator = getInstance()
			layoutCoordinator?.bubblesService = service
		}

		fun setTrashView(trashView: BubbleTrashLayout): Builder
		{
			layoutCoordinator?.trashView = trashView
			return this
		}

		fun setWindowManager(windowManager: WindowManager): Builder
		{
			layoutCoordinator?.windowManager = windowManager
			return this
		}

		fun build(): BubblesLayoutCoordinator?
		{
			return layoutCoordinator
		}
	}

	private fun getTrashContent(): View?
	{
		return trashView?.getChildAt(0)
	}
}