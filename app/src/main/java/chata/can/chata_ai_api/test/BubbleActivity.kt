package chata.can.chata_ai_api.test

import android.widget.LinearLayout
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai_api.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class BubbleActivity: BaseActivity(R.layout.activity_bubble)
{
	override fun onCreateView()
	{
		findViewById<LinearLayout>(R.id.llMain)?.run {
//			addView(
//				TextInputLayout(context).apply {
//					//layoutParams = getLinearLayoutParams(-1, -2)
//					hint = "Project ID"
//					boxBackgroundColor = getParsedColor(R.color.white)
//					boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
//					//setBoxCornerRadii(5f, 5f, 5f, 5f)
//					//margin(20.5f, 10.5f, 20.5f, 10.5f)
//					//helperText = "* Required"
//					addView(TextInputEditText(this.context).apply {
//						//layoutParams = getLinearLayoutParams(-1, -2)
//					})
//				}
//			)
		}
	}
}