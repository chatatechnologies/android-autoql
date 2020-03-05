package chata.can.chata_ai.view.bubbleHandle

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.setMargins
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
		if (mPlacement != placement && placement > 0)
		{
			mPlacement = placement
			bubbleLayout.definePositionInScreen(placement)
		}
	}

	private fun initBubbleLayout()
	{
		bubbleLayout = BubbleLayout(context)
		with(bubbleLayout)
		{
			if (layoutParams is ViewGroup.MarginLayoutParams)
			{
				(layoutParams as ViewGroup.MarginLayoutParams).setMargins(12, 12, 12, 12)
			}

			initCircleImageView()
			setOnBubbleClickListener { /*showToast("Clicked")*/ }
			setShouldStickToWall(true)

			ViewCompat.setElevation(this, 15f)
			bubblesManager.addBubble(bubbleLayout, 0,0)
		}
	}

	private fun initCircleImageView()
	{
		circleImageView = CircleImageView(context)
		with(circleImageView)
		{
			bubbleLayout.addView(this)
			layoutParams.height = 180
			layoutParams.width = 180
			if (layoutParams is ViewGroup.MarginLayoutParams)
			{
				(layoutParams as ViewGroup.MarginLayoutParams).setMargins(30, 30, 30, 30)
			}
			setImageResource(R.drawable.ic_bubble)
			setCircleBackgroundColorResource(R.color.red)
		}
	}

	fun onDestroy()
	{
		bubblesManager.recycle()
	}
}