package chata.can.chata_ai.view

import android.content.Context
import android.util.AttributeSet
import chata.can.chata_ai.view.container.LayoutParams.MATCH_PARENT_WRAP_CONTENT
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.JustifyContent

class SuggestionContinuous: FlexboxLayout
{
	constructor(context: Context): super(context) {
		init()
	}

	constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

	constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)
	{
		init()
	}

	private fun init() {
		layoutParams = getViewGroupLayoutParams(MATCH_PARENT_WRAP_CONTENT)
		flexWrap = FlexWrap.WRAP
		justifyContent = JustifyContent.CENTER
	}
}