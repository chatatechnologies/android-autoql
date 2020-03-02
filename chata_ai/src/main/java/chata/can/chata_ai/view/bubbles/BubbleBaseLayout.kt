package chata.can.chata_ai.view.bubbles

import android.content.Context
import android.util.AttributeSet
import android.view.WindowManager
import android.widget.FrameLayout

open class BubbleBaseLayout: FrameLayout
{
	private var windowManager: WindowManager ?= null
	private var params: WindowManager.LayoutParams ?= null
	private var layoutCoordinator: BubblesLayoutCoordinator ?= null

	fun setLayoutCoordinator(layoutCoordinator: BubblesLayoutCoordinator)
	{
		this.layoutCoordinator = layoutCoordinator
	}

	fun getLayoutCoordinator(): BubblesLayoutCoordinator?
	{
		return layoutCoordinator
	}

	fun setWindowManager(windowManager: WindowManager)
	{
		this.windowManager = windowManager
	}

	fun getWindowManager() = windowManager

	fun setViewParams(params: WindowManager.LayoutParams)
	{
		this.params = params
	}

	fun getViewParams() = this.params

	constructor(context: Context): super(context)

	constructor(context: Context, attrs: AttributeSet): super(context, attrs)

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
		: super(context, attrs, defStyleAttr)
}