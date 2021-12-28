package chata.can.chata_ai.dialog.twiceDrill

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.view.GuideLine
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams

object TwiceDrill
{
	fun getDesign(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			id = R.id.rlParent
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			paddingAll(4f, right = 4f)
			//region rlTitle
			addView(RelativeLayout(context).apply {
				id = R.id.rlTitle
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				margin(4f, 16f, 4f, 16f)
				//region ivCancel
				addView(ImageView(context).apply {
					id = R.id.ivCancel
					layoutParams = getRelativeLayoutParams(dpToPx(24f), dpToPx(24f)).apply {
						addRule(RelativeLayout.ALIGN_PARENT_END)
						addRule(RelativeLayout.CENTER_VERTICAL)
					}
					setImageResource(R.drawable.ic_cancel)
				})
				//endregion
				//region tvTitle
				addView(TextView(context).apply {
					gravity = Gravity.CENTER
					id = R.id.tvTitle
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT).apply {
						addRule(RelativeLayout.CENTER_IN_PARENT)
						addRule(RelativeLayout.START_OF, R.id.ivCancel)
					}
					setTypeface(typeface, Typeface.BOLD)
					textSize(16f)
				})
				//endregion
			})
			//endregion
			//region vBorder
			addView(View(context).apply {
				id = R.id.vBorder
				layoutParams = getRelativeLayoutParams(-1, dpToPx(1f)).apply {
					addRule(RelativeLayout.BELOW, R.id.rlTitle)
				}
			})
			//endregion
			//region layout
			addView(ConstraintLayout(context).apply {
				id = R.id.layout
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY).apply {
					addRule(RelativeLayout.BELOW, R.id.vBorder)
				}
				//region guide
				val guide = GuideLine.getGuideLine(context, R.id.guide, ConstraintLayout.LayoutParams.VERTICAL).apply {
					setGuidelinePercent(0.47f)
				}
				addView(guide)
				//endregion
				//region guide1
				val guide1 = GuideLine.getGuideLine(context, R.id.guide1, ConstraintLayout.LayoutParams.VERTICAL).apply {
					setGuidelinePercent(0.53f)
				}
				addView(guide1)
				//endregion
				//region guideHide
				val guideHide = GuideLine.getGuideLine(context, R.id.guideHide, ConstraintLayout.LayoutParams.VERTICAL).apply {
					setGuidelinePercent(0.06f)
				}
				addView(guideHide)
				//endregion
				//region rlDrillDown1
				addView(RelativeLayout(context).apply {
					id = R.id.rlDrillDown1
					marginAll(4f)

				})
				val set = ConstraintSet()
				set.clone(this)
				set.connect()
				set.applyTo(this)
				//endregion
			})
			//endregion
		}
	}
}