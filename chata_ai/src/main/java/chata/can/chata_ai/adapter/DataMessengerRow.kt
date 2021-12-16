package chata.can.chata_ai.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.marginAll
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.view.container.Layout.getLinearLayout
import chata.can.chata_ai.view.container.Layout.getRelativeLayout
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams

object DataMessengerRow
{
	fun getRowQueryBuilder(context: Context): RelativeLayout
	{
		return getRelativeLayout(context).apply {
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			//region main container
			addView(getRelativeLayout(context).apply {
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				margin(end = 50f)

				//region llContent
				addView(getLinearLayout(context).apply {
					layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
					marginAll(5f)
					orientation = LinearLayout.VERTICAL
					id = R.id.llContent
					//region tvMsg
					addView(TextView(context).apply {
						layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
						margin(10f,5f,0f,5f)
						text = context.resources.getString(R.string.you_can_ask_me)
						id = R.id.tvMsg
					})
					//endregion
					//region llQueries & rvExplore
					addView(getRelativeLayout(context).apply {
						layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
						//region llQueries
						addView(getLinearLayout(context).apply {
							layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
							margin(2f, 0f, 2f)
							orientation = LinearLayout.HORIZONTAL
							id = R.id.llQueries

							//region ivBackExplore
							addView(ImageView(context).apply {
								layoutParams = getLinearLayoutParams(dpToPx(20f), dpToPx(20f))
								setImageResource(R.drawable.ic_back)
								id = R.id.ivBackExplore
							})
							//endregion
							//region view
							addView(View(context).apply {
								layoutParams = getLinearLayoutParams(dpToPx(0.5f), -1)
								setBackgroundColor(Color.BLACK)
								margin(end = 5f)
							})
							//endregion

							//region current explore
							addView(getLinearLayout(context).apply {
								layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
								orientation = LinearLayout.VERTICAL

								//region tvCurrentExplore
								addView(TextView(context).apply {
									layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
									setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
									setTypeface(typeface, Typeface.BOLD)
									id = R.id.tvCurrentExplore
								})
								//endregion
								//region rvQueries
								addView(RecyclerView(context).apply {
									layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
									id = R.id.rvQueries
									isVerticalScrollBarEnabled = false
									isHorizontalScrollBarEnabled = false
								})
								//endregion
							})
						//endregion
						})
						//endregion
						//region rvExplore
						addView(RecyclerView(context).apply {
							layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
							isVerticalScrollBarEnabled = false
							isHorizontalScrollBarEnabled = false
							margin(2f, end = 2f)
							paddingAll(22f, right = 22f)
							id = R.id.rvExplore
						})
						//endregion
					})
					//endregion
					//region link data
					addView(TextView(context).apply {
						layoutParams = getLinearLayoutParams(LayoutParams.WRAP_CONTENT_ONLY)
						margin(10f, 5f, 0f, 5f)
						id = R.id.tvLink
					})
					//endregion
				})
				//endregion
			})
			//endregion
		}
	}
}