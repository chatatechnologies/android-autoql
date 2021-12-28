package chata.can.chata_ai.fragment.dataMessenger.holder.queryBuilder.adapter

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.extension.textSize
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams

object OptionDesign
{
	fun getRowOption(context: Context): RelativeLayout {
		return RelativeLayout(context).apply {
			setBackgroundColor(Color.WHITE)
			id = R.id.rlParent
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			paddingAll(top = 4f)
			//region tvQueryRoot
			addView(TextView(context).apply {
				id = R.id.tvQueryRoot
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT).apply {
					addRule(RelativeLayout.CENTER_HORIZONTAL)
					addRule(RelativeLayout.START_OF, R.id.ivForwardExplore)
				}
				textSize(16f)
			})
			//endregion
			//region ivForwardExplore
			addView(ImageView(context).apply {
				id = R.id.ivForwardExplore
				layoutParams = getRelativeLayoutParams(dpToPx(20f), dpToPx(20f)).apply {
					addRule(RelativeLayout.ALIGN_PARENT_END)
					addRule(RelativeLayout.CENTER_HORIZONTAL)
				}
				setImageResource(R.drawable.ic_forward)
			})
			//endregion
		}
	}
}