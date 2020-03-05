package chata.can.chata_ai.view.bubbleHandle

import android.content.Context
import chata.can.chata_ai.R
import chata.can.chata_ai.view.bubbles.BubbleLayout
import chata.can.chata_ai.view.bubbles.BubblesManager
import chata.can.chata_ai.view.circle.CircleImageView

class BubbleHandle(private val context: Context)
{
	private lateinit var bubblesManager: BubblesManager
	private lateinit var bubbleLayout: BubbleLayout
	private lateinit var circleImageView: CircleImageView

	companion object {
		const val TOP_PLACEMENT = 1
		const val BOTTOM_PLACEMENT = 2
		const val LEFT_PLACEMENT = 3
		const val RIGHT_PLACEMENT = 4
	}

	private val defaultPlacement = 0
	private var mPlacement = defaultPlacement

	init {
		BubblesManager.Builder(context)
			.setTrashLayout(R.layout.bubble_remove)
			.setInitializationCallback { initBubbleLayout() }
			.build()?.let {
				bubblesManager = it
			}
		if (::bubblesManager.isInitialized)
		{
			bubblesManager.initialize()
		}
	}

	fun setPlacement(placement: Int)
	{
		if (mPlacement != placement)
		{
			mPlacement = placement
			bubbleLayout.definePositionInScreen()
		}
	}

	private fun initBubbleLayout()
	{
		bubbleLayout = BubbleLayout(context)
		with(bubbleLayout)
		{
			initCircleImageView()
			setOnBubbleRemoveListener { /*showToast("Removed")*/ }
			setOnBubbleClickListener { /*showToast("Clicked")*/ }
			setShouldStickToWall(false)

			bubblesManager.addBubble(bubbleLayout, 0,0)
		}
	}

	private fun initCircleImageView()
	{
		circleImageView = CircleImageView(context)
		with(circleImageView)
		{
			bubbleLayout.addView(this)
			layoutParams.height = 210
			layoutParams.width = 210
			setImageResource(R.drawable.ic_bubble)
			setCircleBackgroundColorResource(R.color.white)
		}
	}

	fun onDestroy()
	{
		bubblesManager.recycle()
	}
}