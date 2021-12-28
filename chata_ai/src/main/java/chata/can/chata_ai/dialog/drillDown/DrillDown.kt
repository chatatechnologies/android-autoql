package chata.can.chata_ai.dialog.drillDown

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.marginAll
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.extension.textSize
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams
import chata.can.chata_ai.view.gif.KGifView

object DrillDown
{
	fun getDesign(context: Context): RelativeLayout
	{
		//region rlParent
		return RelativeLayout(context).apply {
			id = R.id.rlParent
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			paddingAll(4f, right = 4f)
			//region rlTitle
			addView(RelativeLayout(context).apply {
				id = R.id.rlTitle
				layoutParams = getRelativeLayoutParams(-1, dpToPx(36f))
				//region tvTitle
				addView(TextView(context).apply {
					gravity = Gravity.CENTER
					id = R.id.tvTitle
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT).apply {
						addRule(RelativeLayout.CENTER_IN_PARENT)
					}
					setTypeface(typeface, Typeface.BOLD)
					textSize(16f)
				})
				//endregion
				//region ivCancel
				addView(ImageView(context).apply {
					id = R.id.ivCancel
					layoutParams = getRelativeLayoutParams(dpToPx(24f), dpToPx(24f)).apply {
						addRule(RelativeLayout.CENTER_VERTICAL)
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					setImageResource(R.drawable.ic_cancel)
				})
				//endregion
			})
			//endregion
			//region rlTitle
			addView(RelativeLayout(context).apply {
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY).apply {
					addRule(RelativeLayout.BELOW, R.id.rlTitle)
				}
				marginAll(5f)
				paddingAll(10f)
				//region ivLoad
				addView(KGifView(context).apply {
					id = R.id.ivLoad
					layoutParams = getRelativeLayoutParams(dpToPx(160f), dpToPx(160f)).apply {
						addRule(RelativeLayout.CENTER_IN_PARENT)
					}
				})
				//endregion
				//region wbDrillDown
				addView(WebView(context).apply {
					id = R.id.wbDrillDown
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
					visibility = View.GONE
				})
				//endregion
			})
			//endregion
		}
		//endregion
	}
}