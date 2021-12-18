package chata.can.chata_ai.activity.dm

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams

object DmDesign
{
	fun getDesign(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
			//region vBehind
			addView(View(context).apply {
				setBackgroundColor(
					ColorUtils.setAlphaComponent(Color.BLACK, (0.6*255).toInt())
				)
				visibility = View.GONE
				id = R.id.vBehind
			})
			//endregion
			//region llMenu
			addView(LinearLayout(context).apply {
				isClickable = true
				isFocusable = true
				layoutParams = getRelativeLayoutParams(dpToPx(32f), -1)
				setBackgroundColor(Color.TRANSPARENT)
				gravity = Gravity.CENTER
				orientation = LinearLayout.VERTICAL
				id = R.id.llMenu
				//region rlChat
				addView(RelativeLayout(context).apply {
					layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					id = R.id.rlChat
					//region ivChat
					addView(ImageView(context).apply {
						layoutParams = getRelativeLayoutParams(dpToPx(56f), -1)
						setImageResource(R.drawable.ic_side_explore)
						id = R.id.ivChat
					})
					//endregion
				})
				//endregion
				//region rlTips
				addView(RelativeLayout(context).apply {
					layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					id = R.id.rlTips
					//region ivTips
					addView(ImageView(context).apply {
						layoutParams = getRelativeLayoutParams(dpToPx(56f), -1)
						setImageResource(R.drawable.ic_side_chat)
						id = R.id.ivTips
					})
					//endregion
				})
				//endregion
				//region rlNotify
				addView(RelativeLayout(context).apply {
					layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					id = R.id.rlNotify
					//region ivTips
					addView(ImageView(context).apply {
						layoutParams = getRelativeLayoutParams(dpToPx(56f), -1)
						setImageResource(R.drawable.ic_side_notification)
						id = R.id.ivNotify
					})
					addView(TextView(context).apply {
						layoutParams = getRelativeLayoutParams(dpToPx(18f), dpToPx(18f)).apply {
							addRule(RelativeLayout.ALIGN_PARENT_END)
						}
						gravity = Gravity.CENTER
						margin(top = 8f, end = 8f)
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
						id = R.id.tvNotification
					})
					//endregion
				})
				//endregion
			})

			//endregion
			//region rlLocal

			//endregion
		}
	}
}