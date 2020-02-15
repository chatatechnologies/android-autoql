package chata.can.chata_ai.view.drawerHandle

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import chata.can.chata_ai.R

class DrawerHandle: RelativeLayout
{
	private lateinit var ivDrawerHandle: ImageView

	constructor(cContext: Context) : super(cContext)
	{
		inflateView(cContext, null)
	}

	constructor(cContext: Context, attrs: AttributeSet) : super(cContext, attrs)
	{
		inflateView(cContext, attrs)
	}

	constructor(cContext: Context, attrs: AttributeSet, defStyleAttr: Int) : super(cContext, attrs, defStyleAttr)
	{
		inflateView(cContext, attrs)
	}

	private fun inflateView(cContext: Context, attrs: AttributeSet?, defStyle: Int = 0)
	{
		View.inflate(cContext, R.layout.view_drawer_handle, this)
		initViews()

		val typedArray = cContext.obtainStyledAttributes(attrs, R.styleable.DrawerHandle, defStyle, 0)
		typedArray.recycle()
	}

	private fun initViews()
	{
		ivDrawerHandle = findViewById(R.id.ivDrawerHandle)
	}

	/**
	 * attributes
	 */
	private var aRules = ArrayList<Int>()
	private var placementValue = EnumPlacement.right

	enum class EnumPlacement
	{
		left,
		right,
		top,
		bottom
	}

	fun placement(placement: EnumPlacement)
	{
		(ivDrawerHandle.layoutParams as? LayoutParams)?.let {

			for (rule in aRules)
			{
				it.removeRule(rule)
			}

			aRules.clear()
			when(placement)
			{
				EnumPlacement.bottom ->
				{
					aRules.add(CENTER_HORIZONTAL)
					aRules.add(ALIGN_PARENT_BOTTOM)
				}
				EnumPlacement.left ->
				{
					aRules.add(CENTER_VERTICAL)
				}
				EnumPlacement.right ->
				{
					aRules.add(CENTER_VERTICAL)
					aRules.add(ALIGN_PARENT_BOTTOM)
				}
				EnumPlacement.top ->
				{
					aRules.add(CENTER_HORIZONTAL)
				}
			}
			for (rule in aRules)
			{
				it.addRule(rule)
			}
			ivDrawerHandle.layoutParams = it
		}
	}

	//it.removeRule()
}