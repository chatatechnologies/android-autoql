package chata.can.chata_ai.fragment.dataMessenger.holder

import android.content.Context
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams

object ComponentHolder
{
	fun getTopContainer(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			id = R.id.rvContentTop
			layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			margin(5f)
			//region Content Top
			addView(TextView(context).apply {
				id = R.id.tvContentTop
				layoutParams = getRelativeLayoutParams(LayoutParams.WRAP_CONTENT_ONLY).apply {
					addRule(RelativeLayout.ALIGN_PARENT_END)
				}
				paddingAll(8f)
			})
		}
	}
}