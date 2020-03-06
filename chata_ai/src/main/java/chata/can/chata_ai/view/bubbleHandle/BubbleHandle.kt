package chata.can.chata_ai.view.bubbleHandle

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.BubbleData.heightDefault
import chata.can.chata_ai.pojo.BubbleData.marginBottomDefault
import chata.can.chata_ai.pojo.BubbleData.marginLeftDefault
import chata.can.chata_ai.pojo.BubbleData.marginRightDefault
import chata.can.chata_ai.pojo.BubbleData.marginTopDefault
import chata.can.chata_ai.pojo.BubbleData.widthDefault
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

	private val defaultPlacement = 4
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
			layoutParams.height = heightDefault
			layoutParams.width = widthDefault
			if (layoutParams is ViewGroup.MarginLayoutParams)
			{
				(layoutParams as ViewGroup.MarginLayoutParams).setMargins(
					marginLeftDefault, marginTopDefault, marginRightDefault, marginBottomDefault)
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