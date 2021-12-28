package chata.can.chata_ai.fragment.notification

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.extension.textSize
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams

object NotificationDesign
{
	fun getDesign(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
			//region llParent
			addView(LinearLayout(context).apply {
				id = R.id.llParent
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
				gravity = Gravity.CENTER_HORIZONTAL
				paddingAll(top = 260f)
				orientation = LinearLayout.VERTICAL
				//region iv1
				addView(ImageView(context).apply {
					id = R.id.iv1
					layoutParams = getLinearLayoutParams(dpToPx(180f), dpToPx(180f))
					setImageResource(R.drawable.ic_notification)
					visibility = View.GONE
				})
				//endregion
				//region tvLoading
				addView(TextView(context).apply {
					gravity = Gravity.CENTER
					id = R.id.tvLoading
					layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
					margin(16f, end = 16f)
					text = resources.getString(R.string.loading)
					textSize(14f)
				})
				//endregion
				//region btnTry
				addView(Button(context).apply {
					id = R.id.btnTry
					layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
					margin(top = 16f)
					text = resources.getString(R.string.try_again)
					isAllCaps = false
					visibility = View.GONE
				})
				//endregion
				//region tvMsg1
				addView(TextView(context).apply {
					gravity = Gravity.CENTER
					id = R.id.tvMsg1
					layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
					margin(top = 4f)
					text = resources.getString(R.string.stay_tuned)
					textSize(14f)
					visibility = View.GONE
				})
				//endregion
			})
			//endregion
			//region rvNotification
			addView(RecyclerView(context).apply {
				id = R.id.rvNotification
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
				visibility = View.GONE
			})
			//endregion
		}
	}
}