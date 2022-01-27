package chata.can.chata_ai.dialog.sql

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams
import com.google.android.material.button.MaterialButton

object DisplaySqlDesign
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
						addRule(RelativeLayout.ALIGN_PARENT_END)
						addRule(RelativeLayout.CENTER_VERTICAL)
					}
					setImageResource(R.drawable.ic_cancel)
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
			//region body RelativeLayout
			addView(RelativeLayout(context).apply {
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY).apply {
					addRule(RelativeLayout.ABOVE, R.id.btnOk)
					addRule(RelativeLayout.BELOW, R.id.vBorder)
				}
				marginAll(5f)
				paddingAll(10f)
				//region etQuery
				addView(TextView(context).apply {
					id = R.id.etQuery
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
					paddingAll(8f)
					isVerticalScrollBarEnabled = true
					isHorizontalScrollBarEnabled = false
				})
				//endregion
				//region ivCopy
				addView(ImageView(context).apply {
					id = R.id.ivCopy
					layoutParams = getRelativeLayoutParams(dpToPx(40f), dpToPx(32f)).apply {
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					margin(top = 8f, end = 8f)
					setImageResource(R.drawable.ic_clipboard)
					paddingAll(4f)
				})
				//endregion
			})
			//endregion
			//region btnOk
			addView(MaterialButton(context).apply {
				id = R.id.btnOk
				layoutParams = getRelativeLayoutParams(dpToPx(84f), dpToPx(48f)).apply {
					addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
					addRule(RelativeLayout.ALIGN_PARENT_END)
				}
				margin(end = 8f, bottom = 8f)
				isAllCaps = false
				text = resources.getString(R.string.ok)
			})
			//endregion
			//region rlAlert
			addView(RelativeLayout(context).apply {
				setBackgroundColor(Color.TRANSPARENT)
				id = R.id.rlAlert
				layoutParams = getRelativeLayoutParams(-1, dpToPx(60f)).apply {
					addRule(RelativeLayout.CENTER_HORIZONTAL)
				}
				visibility = View.GONE
				paddingAll(5f)
				//region llAlert
				addView(LinearLayout(context).apply {
					setBackgroundColor(Color.WHITE)
					id = R.id.llAlert
					layoutParams = getRelativeLayoutParams(LayoutParams.WRAP_CONTENT_ONLY).apply {
						addRule(RelativeLayout.CENTER_IN_PARENT)
					}
					paddingAll(5f)
					//region ivAlert
					addView(ImageView(context).apply {
						id = R.id.ivAlert
						layoutParams = getLinearLayoutParams(dpToPx(48f), dpToPx(48f))
						scaleType = ImageView.ScaleType.CENTER_INSIDE
						setImageResource(R.drawable.ic_done)
					})
					//endregion
					//region tvAlert
					addView(Button(context).apply {
						setBackgroundColor(Color.TRANSPARENT)
						id = R.id.tvAlert
						layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_MATCH_PARENT)
						isAllCaps = false
						setTextColor(Color.BLACK)
					})
					//endregion
				})
				//endregion
			})
			//endregion
		}
	}
}