package chata.can.chata_ai.adapter

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.marginAll
import chata.can.chata_ai.extension.paddingAll

object BaseRow
{
	fun getRowBase(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = ViewGroup.LayoutParams(-1, -2)
			//region top container
			addView(RelativeLayout(context).apply {
				layoutParams = RelativeLayout.LayoutParams(-1, -2).apply {
					addRule(RelativeLayout.ALIGN_PARENT_END)
				}
				paddingAll(left = 50f)
				//region tv content top
				addView(TextView(context).apply {
					layoutParams = RelativeLayout.LayoutParams(-2,-2)
					marginAll(5f)
					paddingAll(8f)
					id = R.id.tvContentTop
				})
				//endregion
				id = R.id.rvContentTop
			})
			//endregion
			//region middle container
			addView(LinearLayout(context).apply {
				layoutParams = RelativeLayout.LayoutParams(-2, -2).apply {
					addRule(RelativeLayout.BELOW, R.id.rvContentTop)
				}
				orientation = LinearLayout.VERTICAL
				gravity = Gravity.END
				margin(end = 50f)
				id = R.id.llMainBase
				//region actions
				addView(RelativeLayout(context).apply {
					layoutParams = RelativeLayout.LayoutParams(-2, -2)
					margin(start = 5f, end = 5f)
					//region report button
					addView(ImageView(context).apply {
						layoutParams = RelativeLayout.LayoutParams(dpToPx(40f), dpToPx(40f))
						paddingAll(4f)
						id = R.id.ivReport
						setImageResource(R.drawable.ic_report)
					})
					//endregion
					//region delete button
					addView(ImageView(context).apply {
						layoutParams = RelativeLayout.LayoutParams(dpToPx(40f), dpToPx(40f)).apply {
							addRule(RelativeLayout.END_OF, R.id.ivReport)
						}
						paddingAll(4f)
						id = R.id.ivDelete
						setImageResource(R.drawable.ic_delete)
					})
					//endregion
					//region options
					addView(ImageView(context).apply {
						layoutParams = RelativeLayout.LayoutParams(dpToPx(40f), dpToPx(40f)).apply {
							addRule(RelativeLayout.END_OF, R.id.ivDelete)
						}
						paddingAll(4f)
						id = R.id.ivPoints
						setImageResource(R.drawable.ic_points)
					})
					//endregion
					id = R.id.rlDelete
				})
				//endregion

				//region content
				addView(RelativeLayout(context).apply {
					layoutParams = RelativeLayout.LayoutParams(-2, -2)
					paddingAll(left = 4f, right = 4f)
					addView(TextView(context).apply {
						layoutParams = RelativeLayout.LayoutParams(-2, -2)
						margin(1f)
						paddingAll(8f)
						id = R.id.tvContent
					})
					id = R.id.rvContent
				})
				//endregion
			})
			//endregion
		}
	}
}