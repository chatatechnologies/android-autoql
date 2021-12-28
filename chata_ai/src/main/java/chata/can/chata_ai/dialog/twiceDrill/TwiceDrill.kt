package chata.can.chata_ai.dialog.twiceDrill

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.view.GuideLine
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams
import chata.can.chata_ai.view.gif.KGifView

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
					//region wbDrillDown1
					addView(WebView(context).apply {
						id = R.id.wbDrillDown1
						layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
						isVerticalScrollBarEnabled = false
						isHorizontalFadingEdgeEnabled = false
					})
					//endregion
					//region ivLoad1
					addView(RelativeLayout(context).apply {
						id = R.id.ivLoad1
						getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
					})
					//endregion
				})
				//endregion
				//region rlHide
				addView(RelativeLayout(context).apply {
					id = R.id.rlHide
					marginAll(4f)
					//region ivHide}
					addView(ImageView(context).apply {
						setBackgroundColor(Color.WHITE)
						id = R.id.ivHide
						layoutParams = getRelativeLayoutParams(dpToPx(56f), -1).apply {
							addRule(RelativeLayout.ALIGN_PARENT_END)
						}
						setImageResource(R.drawable.ic_hide_chart)
					})
					//endregion
				})
				//endregion
				//region rlDrillDown2
				addView(RelativeLayout(context).apply {
					id = R.id.rlDrillDown2
					marginAll(4f)
					//region wbDrillDown2
					addView(WebView(context).apply {
						id = R.id.wbDrillDown2
						layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
					})
					//endregion
					//region ivLoad2
					addView(RelativeLayout(context).apply {
						id = R.id.ivLoad2
						layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
						//region KGifView
						addView(KGifView(context).apply {
							layoutParams = getRelativeLayoutParams(dpToPx(80f), dpToPx(80f)).apply {
								addRule(RelativeLayout.CENTER_IN_PARENT)
							}
						})
						//endregion
					})
					//endregion
				})
				//endregion
			})
			//endregion
		}
	}
}