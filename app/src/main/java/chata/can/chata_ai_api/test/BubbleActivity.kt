package chata.can.chata_ai_api.test

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
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
			addView(
				MaterialButtonToggleGroup(this@BubbleActivity).apply {
					layoutParams = LinearLayout.LayoutParams(-2, -2)

					for (i in 1..3)
					{
						//val ctxWrapper = ContextThemeWrapper(context, R.style.ButtonStyle)
						addView(
							MaterialButton(this@BubbleActivity).apply {
								id = View.generateViewId()
								isAllCaps = false
								text = "Button $i"
							}
						)
					}
				}
			)
//			addView(
//				TextView(context).apply {
//					text = "Hola"
//				}
//			)
		}
	}
}