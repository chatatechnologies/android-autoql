package mx.bangapp.viewresize

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class SplitView: LinearLayout
{
	private var mHandleId = 0
	private var mPrimaryContentId = 0
	private var mSecondaryContentId = 0

	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
	{
		val viewAttrs = context.obtainStyledAttributes(R.styleable.SplitView)
		var e: RuntimeException ?= null

		mHandleId = viewAttrs.getResourceId(R.styleable.SplitView_handle, 0)
		if (mHandleId == 0) {
			e = IllegalArgumentException(viewAttrs.positionDescription +
				": The required attribute handle must refer to a valid child view")
		}

		mPrimaryContentId = viewAttrs.getResourceId(R.styleable.SplitView_primaryContent, 0)
		if (mPrimaryContentId == 0) {
			e = IllegalArgumentException(viewAttrs.positionDescription +
				": The required attribute primaryContent must refer to a valid child view")
		}

		mSecondaryContentId = viewAttrs.getResourceId(R.styleable.SplitView_secondaryContent, 0)
		if (mSecondaryContentId == 0) {
			e = IllegalArgumentException(viewAttrs.positionDescription +
				": The required attribute secondaryContent must refer to valid child view")
		}

		viewAttrs.recycle()
		e?.let {
			throw e
		}
	}

	override fun onFinishInflate()
	{
		super.onFinishInflate()
	}
}