package chata.can.chata_ai_api.test

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai_api.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class BubbleActivity: BaseActivity(R.layout.activity_bubble)
{
	override fun onCreateView()
	{
//		findViewById<MaterialButtonToggleGroup>(R.id.toggleButton)?.run {
//			var lastId = this.checkedButtonId
//			addOnButtonCheckedListener { group, checkedId, isChecked ->
//				group.check(checkedId)
//				if (lastId != checkedId)
//					group.uncheck(lastId)
//				lastId = checkedId
//			}
//		}

		findViewById<LinearLayout>(R.id.llMain)?.run {
			val blue = getParsedColor(R.color.blue_chata_circle)
			val white = getParsedColor(R.color.white)
			//region
			addView(
				MaterialButtonToggleGroup(this@BubbleActivity).apply {
					layoutParams = LinearLayout.LayoutParams(-2, -2)
					val ids = ArrayList<Int>()
					for (i in 1..3)
					{
						addView(
							MaterialButton(this@BubbleActivity).apply {
								val redHex = Color.red(blue)
								val greenHex = Color.green(blue)
								val blueHex = Color.blue(blue)
								val newColor = Color.argb(76, redHex, greenHex, blueHex)

								val a1 = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())
								val b1 = intArrayOf(newColor, white)

								backgroundTintList = ColorStateList(a1, b1)
								val tmpId = View.generateViewId()
								ids.add(tmpId)
								id = tmpId
								isAllCaps = false
								setTextColor(blue)
								strokeWidth = 3
								strokeColor = ColorStateList.valueOf(blue)
								text = "Button $i"
							}
						)
					}
					check(ids[0])
					isSingleSelection = true

					var lastId = this.checkedButtonId
					addOnButtonCheckedListener { group, checkedId, _ ->
						group.check(checkedId)
						if (lastId != checkedId)
							group.uncheck(lastId)
						lastId = checkedId
					}
				}
			)
			//endregion
			addView(
				MaterialButton(this@BubbleActivity).apply {
					val drawable = StateListDrawable().apply {
						addState(intArrayOf(), ColorDrawable(Color.WHITE))
						addState(intArrayOf(android.R.attr.state_checked), ColorDrawable(Color.RED))
					}
					background = drawable
					strokeColor = ColorStateList.valueOf(blue)
					setTextColor(blue)
					text = "Example"
				}
			)
		}
	}
}