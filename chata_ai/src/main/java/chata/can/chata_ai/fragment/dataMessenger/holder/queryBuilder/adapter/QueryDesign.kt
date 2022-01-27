package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.extension.textSize
import chata.can.chata_ai.view.container.LayoutParams

object QueryDesign {
	fun getRowQuery(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			setBackgroundColor(Color.WHITE)
			id = R.id.rlParent
			layoutParams = LayoutParams.getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			paddingAll(top = 4f)
			//region tvQueryExplore
			addView(TextView(context).apply {
				id = R.id.tvQueryExplore
				layoutParams = LayoutParams.getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT).apply {
					addRule(RelativeLayout.CENTER_IN_PARENT)
					addRule(RelativeLayout.START_OF, R.id.ivPlay)
				}
				textSize(16f)
			})
			//endregion
			//region ivPlay
			addView(ImageView(context).apply {
				id = R.id.ivPlay
				layoutParams = LayoutParams.getRelativeLayoutParams(dpToPx(20f), dpToPx(20f)).apply {
					addRule(RelativeLayout.ALIGN_PARENT_END)
					addRule(RelativeLayout.CENTER_VERTICAL)
				}
				margin(4f, end = 4f)
				setImageResource(R.drawable.ic_play)
				visibility = View.INVISIBLE
			})
			//endregion
		}
	}
}