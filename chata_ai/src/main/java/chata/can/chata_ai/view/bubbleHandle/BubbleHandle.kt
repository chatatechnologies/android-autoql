package chata.can.chata_ai.view.bubbleHandle

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import chata.can.chata_ai.R
import chata.can.chata_ai.view.bubbles.BubbleLayout
import chata.can.chata_ai.view.circle.CircleImageView

class BubbleHandle: BubbleLayout
{
	private lateinit var circleImageView: CircleImageView

	companion object {
		const val RIGHT_PLACEMENT = 1
		const val BOTTOM_PLACEMENT = 2
		const val LEFT_PLACEMENT = 3
		const val TOP_PLACEMENT = 4
	}

	private val defaultPlacement = 0
	private var mPlacement = defaultPlacement

	constructor(oContext: Context): super(oContext)
	{
		initViews(oContext, null)
	}

	constructor(oContext: Context, attrs: AttributeSet): this(oContext, attrs, 0)

	constructor(oContext: Context, oAttrs: AttributeSet, iDefStyleAttr: Int)
		: super(oContext, oAttrs, iDefStyleAttr)
	{
		initViews(oContext, oAttrs, iDefStyleAttr)
	}

	private fun initViews(cContext: Context, oAttrs: AttributeSet?, iDefStyleAttr: Int = 0)
	{
		with(cContext.obtainStyledAttributes(oAttrs, R.styleable.BubbleHandle, iDefStyleAttr, 0))
		{
			var value = getInt(R.styleable.BubbleHandle_bh_placement, defaultPlacement)
			if (value > 0)
			{
				setPlacement(value)
			}
			recycle()
		}

		initCircleImageView()
	}

	fun setPlacement(placement: Int)
	{
		if (mPlacement != placement)
		{
			mPlacement = placement
			Log.e("PLACEMENT", "PLACEMENT: $mPlacement")
		}
	}

	private fun initCircleImageView()
	{
		with(CircleImageView(context))
		{
			circleImageView = this
			addView(this)
			layoutParams.height = 210
			layoutParams.width = 210
			setImageResource(R.drawable.ic_bubble)
			setCircleBackgroundColorResource(R.color.white)
		}
	}
}