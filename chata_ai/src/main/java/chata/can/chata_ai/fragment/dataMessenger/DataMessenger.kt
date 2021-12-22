package chata.can.chata_ai.fragment.dataMessenger

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams
import chata.can.chata_ai.view.gif.KGifView

object DataMessenger
{
	fun getDesign(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
			//region llParent
			addView(LinearLayout(context).apply {
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
				orientation = LinearLayout.VERTICAL
				paddingAll(8f)
				id = R.id.llParent
				addView(RecyclerView(context).apply {
					layoutParams = getLinearLayoutParams(-1, 0).apply { weight = 1f }
					id = R.id.rvChat
				})
				//region inner relative
				addView(RelativeLayout(context).apply {
					layoutParams = getLinearLayoutParams(-1, dpToPx(36f))
					//region gifView
					addView(KGifView(context).apply {
						layoutParams = getRelativeLayoutParams(-2, -2).apply {
							addRule(RelativeLayout.CENTER_VERTICAL)
						}
						visibility = View.INVISIBLE
						id = R.id.gifView
					})
					//endregion
					//region ivRun
					addView(ImageView(context).apply {
						layoutParams = getRelativeLayoutParams(dpToPx(12f), dpToPx(12f)).apply {
							addRule(RelativeLayout.CENTER_VERTICAL)
							addRule(RelativeLayout.END_OF, R.id.gifView)
						}
						id = R.id.ivRun
						margin(end = 4f)
						setImageResource(R.drawable.ic_chata)
					})
					//endregion
					//region tvMsg
					addView(TextView(context).apply {
						layoutParams = getRelativeLayoutParams(-2, -2).apply {
							addRule(RelativeLayout.CENTER_VERTICAL)
							addRule(RelativeLayout.END_OF, R.id.ivRun)
						}
						id = R.id.tvMsg
						setTextColor(context.getParsedColor(R.color.chata_drawer_hover_color_dark))
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
						text = context.getString(R.string.msg_run_autoql)
					})
					//endregion
				})
				//endregion
				//region bottom relative
				addView(RelativeLayout(context).apply {
					layoutParams = getLinearLayoutParams(-1, dpToPx(40f))
					margin(5f, 7f, 5f)
				})
				//endregion
			})
			//endregion

			//region rlAlert
			addView(RelativeLayout(context).apply {
				setBackgroundColor(Color.TRANSPARENT)
				id = R.id.rlAlert
				layoutParams = getRelativeLayoutParams(-1, dpToPx(60f)).apply {
					addRule(RelativeLayout.CENTER_IN_PARENT)
				}
				visibility = View.GONE
				paddingAll(5f)
				//region llAlert
				addView(LinearLayout(context).apply {
					layoutParams = getRelativeLayoutParams(-2, -2).apply {
						addRule(RelativeLayout.CENTER_IN_PARENT)
					}
					setBackgroundColor(Color.WHITE)
					id = R.id.llAlert
					paddingAll(5f)
					//region ImageView
					addView(ImageView(context).apply {
						layoutParams = getLinearLayoutParams(dpToPx(48f), dpToPx(48f))
						id = R.id.ivAlert
						scaleType = ImageView.ScaleType.CENTER_INSIDE
						setImageResource(R.drawable.ic_done)
					})
					//endregion
					//region Button
					addView(Button(context).apply {
						layoutParams = getLinearLayoutParams(-1, -2)
						setBackgroundColor(Color.TRANSPARENT)
						id = R.id.tvAlert
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