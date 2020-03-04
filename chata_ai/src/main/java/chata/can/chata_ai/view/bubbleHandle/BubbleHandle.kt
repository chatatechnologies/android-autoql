package chata.can.chata_ai.view.bubbleHandle

import android.content.Context
import android.util.AttributeSet
import chata.can.chata_ai.R
import chata.can.chata_ai.view.bubbles.BubbleLayout
import chata.can.chata_ai.view.circle.CircleImageView

class BubbleHandle: BubbleLayout, BubbleHandleContract
{
	private lateinit var circleImageView: CircleImageView

	private val defaultPlacement = 0
	private var placement = defaultPlacement
	private var statusPlacement = StatePlacement.Right

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
			placement = getInt(R.styleable.BubbleHandle_bh_placement, defaultPlacement)
			recycle()
		}

		setPlacement()
		initCircleImageView()
	}

	override fun setPlacement()
	{
		when(placement)
		{

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