package chata.can.chata_ai.fragment.notification.adapter

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams
import chata.can.chata_ai.view.gif.KGifView

object NotificationView
{
	fun getRowNotification(context: Context): LinearLayout {
		val selectedGray = ThemeColor.currentColor.pDrawerTextColorPrimary
		return LinearLayout(context).apply {
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			clipToPadding = false
			orientation = LinearLayout.VERTICAL
			paddingAll(10f, 5f, 10f, 5f)
			//region rlParent
			addView(RelativeLayout(context).apply {
				id = R.id.rlParent
				layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				//region iView
				addView(View(context).apply {
					minimumHeight = 0
					layoutParams = getRelativeLayoutParams(dpToPx(4f), -1)
					id = R.id.iView
				})
				//endregion
				//region ivTop
				addView(LinearLayout(context).apply {
					id = R.id.ivTop
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT).apply {
						addRule(RelativeLayout.END_OF, R.id.iView)
					}
					orientation = LinearLayout.VERTICAL
					paddingAll(10f)
					//region tvTitle
					addView(TextView(context).apply {
						id = R.id.tvTitle
						layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
					})
					//endregion
					//region tvBody
					addView(TextView(context).apply {
						id = R.id.tvBody
						layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
					})
					//endregion
					//region tvDate
					addView(TextView(context).apply {
						id = R.id.tvDate
						layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
					})
					//endregion
				})
				//endregion
				//region rlBottom
				addView(LinearLayout(context).apply {
					id = R.id.rlBottom
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT).apply {
						addRule(RelativeLayout.BELOW, R.id.ivTop)
						addRule(RelativeLayout.END_OF, R.id.iView)
					}
					orientation = LinearLayout.VERTICAL
					//region View
					addView(View(context).apply {
						setBackgroundColor(selectedGray)
						layoutParams = getLinearLayoutParams(-1, dpToPx(0.2f))
						margin(top = 8f, bottom = 8f)
					})
					//endregion
					//region tvQuery
					addView(TextView(context).apply {
						gravity = Gravity.CENTER
						id = R.id.tvQuery
						layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
						setTypeface(typeface, Typeface.BOLD)
					})
					//endregion
					//region View
					addView(View(context).apply {
						setBackgroundColor(selectedGray)
						layoutParams = getLinearLayoutParams(-1, dpToPx(0.2f))
						margin(24f, 4f, 24f)
					})
					//endregion
					//region rlLoad
					addView(RelativeLayout(context).apply {
						id = R.id.rlLoad
						layoutParams = getLinearLayoutParams(-1, dpToPx(200f))
						//region KGifView
						addView(KGifView(context).apply {
							layoutParams = getRelativeLayoutParams(dpToPx(80f), dpToPx(80f)).apply {
								addRule(RelativeLayout.CENTER_IN_PARENT)
							}
						})
						//endregion
					})
					//endregion
					//region tvContent
					addView(TextView(context).apply {
						gravity = Gravity.CENTER
						id = R.id.tvContent
						layoutParams = getLinearLayoutParams(-1, dpToPx(200f))
						visibility = View.GONE
						margin(16f, end = 16f)
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
					})
					//endregion
					//region wbQuery
					addView(WebView(context).apply {
						id = R.id.wbQuery
						layoutParams = getLinearLayoutParams(-1, dpToPx(200f))
						visibility = View.GONE
					})
					//endregion
				})
				//endregion
			})
			//endregion
		}
	}
}