package chata.can.chata_ai_api

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class ButtonToggleGroup: MaterialButtonToggleGroup
{
	constructor(context: Context): super(context)
	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

	override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
		super.addView(child, index, params)
		if (child is MaterialButton)
			child.maxLines = 2
	}
}