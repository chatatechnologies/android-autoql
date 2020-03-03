package chata.can.chata_ai.view.bubbleHandle

import android.content.Context
import android.util.AttributeSet
import chata.can.chata_ai.R
import chata.can.chata_ai.view.bubbles.BubbleLayout
import chata.can.chata_ai.view.circle.CircleImageView

class BubbleHandle: BubbleLayout
{
	constructor(context: Context): super(context)
	{
		initViews()
	}

	constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr)
	{
		initViews()
	}

	private fun initViews()
	{
		with(CircleImageView(context))
		{
			addView(this)
			layoutParams.height = 210
			layoutParams.width = 210
			setImageResource(R.drawable.ic_bubble)
			setCircleBackgroundColorResource(R.color.white)
		}
	}
}