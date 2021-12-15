package chata.can.chata_ai.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.margin

object DataMessengerRow
{
	fun getRowQueryBuilder(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = ViewGroup.LayoutParams(-1, -2)
//			region main container
			addView(RelativeLayout(context).apply {
				layoutParams = RelativeLayout.LayoutParams(-1, -2)
				margin(end = 50f)

//				region linear container
				addView(LinearLayout(context).apply {
					layoutParams = RelativeLayout.LayoutParams(-1, -2)
					margin(5f)
					orientation = LinearLayout.VERTICAL
					id = R.id.llContent
					addView(TextView(context).apply {
						layoutParams = LinearLayout.LayoutParams(-2, -2)
						margin(10f,5f,0f,5f)
						text = context.resources.getString(R.string.you_can_ask_me)
						id = R.id.tvMsg
					})
				})
//					endregion

//				region options
				addView(RelativeLayout(context).apply {
					layoutParams = LinearLayout.LayoutParams(-2, -1)


				})
//				endregion

//				region link data
				addView(TextView(context).apply {
					layoutParams = LinearLayout.LayoutParams(-2, -2)
					margin(10f, 5f, 0f, 5f)
					id = R.id.tvLink
				})
//				endregion

			})
//			endregion

		}
	}
}