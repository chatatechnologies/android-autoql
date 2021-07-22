package chata.can.chata_ai_api.test

import android.content.res.ColorStateList
import android.os.Build
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.pojo.base.BaseActivity
import chata.can.chata_ai_api.ButtonToggleGroup
import chata.can.chata_ai_api.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class BubbleActivity: BaseActivity(R.layout.activity_bubble)
{
	override fun onCreateView()
	{
		findViewById<MaterialButtonToggleGroup>(R.id.toggleButton)?.run {
			var lastId = this.checkedButtonId
			addOnButtonCheckedListener { group, checkedId, _ ->
				group.check(checkedId)
				if (lastId != checkedId)
					group.uncheck(lastId)
				lastId = checkedId
			}
		}

		findViewById<LinearLayout>(R.id.llMain)?.run {
			val blue = getParsedColor(R.color.blue_chata_circle)
			val white = getParsedColor(R.color.white)
			//region
			addView(
				ButtonToggleGroup(this@BubbleActivity).apply {
					layoutParams = LinearLayout.LayoutParams(-1, -2)
					margin(12f, end = 12f)
					val ids = ArrayList<Int>()
					for (i in 1..3)
					{
						addView(
							MaterialButton(this@BubbleActivity).apply {
								layoutParams = LinearLayout.LayoutParams(0, -2).apply { weight = 1f }
//								insetBottom = 0
//								insetTop = 0
								val alphaColor = ColorUtils.setAlphaComponent(blue, (0.3 * 255).toInt())

								val a1 = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())
								val b1 = intArrayOf(alphaColor, white)

								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
								{
									stateListAnimator = null
								}
								backgroundTintList = ColorStateList(a1, b1)
								val tmpId = View.generateViewId()
								ids.add(tmpId)
								id = tmpId
								isAllCaps = false
								setTextColor(blue)
								strokeWidth = 3
								strokeColor = ColorStateList.valueOf(blue)
								val textButton = "Button \n $i Hola"
								text = textButton
								//region icon
								icon = ContextCompat.getDrawable(context, R.drawable.ic_reload)
								iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
								iconPadding = dpToPx(8f)
								iconSize = dpToPx(16f)
								iconTint = ColorStateList.valueOf(blue)
								//endregion
							}
						)
					}
					isSingleSelection = true
					check(ids[0])//to select to first item

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
		}
	}
}