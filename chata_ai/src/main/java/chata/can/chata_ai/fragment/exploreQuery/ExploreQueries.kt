package chata.can.chata_ai.fragment.exploreQuery

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getRelativeLayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getViewGroupLayoutParams

object ExploreQueries
{
	fun getDesign(context: Context): LinearLayout
	{
		return LinearLayout(context).apply {
			layoutParams = getViewGroupLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
			id = R.id.llParent
			orientation = LinearLayout.VERTICAL
			//region llQuery
			addView(RelativeLayout(context).apply {

			})
			//endregion
			//region middle LinearLayout
			addView(LinearLayout(context).apply {
				layoutParams = getLinearLayoutParams(-1, 0).apply { weight = 1f }
				orientation = LinearLayout.VERTICAL
				//region rvRelatedQueries
				addView(RecyclerView(context).apply {
					getLinearLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
					visibility = View.GONE
					id = R.id.rvRelatedQueries
				})
				//endregion
			})
			//endregion
			//region llPager
			addView(LinearLayout(context).apply {
				layoutParams = getLinearLayoutParams(-1, dpToPx(56f))
				orientation = LinearLayout.HORIZONTAL
				visibility = View.GONE
				//region tvPrevious
				addView(TextView(context).apply {
					id = R.id.tvPrevious
					layoutParams = getLinearLayoutParams(0, -2).apply { weight = 1f }
					gravity = Gravity.CENTER
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 32f)
					setTypeface(typeface, Typeface.BOLD)
					text = resources.getString(R.string.arrow_left)
				})
				//endregion
				//region PAGES
				//region RelativeLayout
				addView(RelativeLayout(context).apply {
					layoutParams = getLinearLayoutParams(0, -2).apply { weight = 1f }
					//region tvFirstPage
					addView(TextView(context).apply {
						id = R.id.tvFirstPage
						layoutParams = getRelativeLayoutParams(-2, dpToPx(30f)).apply {
							addRule(RelativeLayout.CENTER_IN_PARENT)
						}
						gravity = Gravity.CENTER
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
						setTypeface(typeface, Typeface.BOLD)
					})
					//endregion
				})
				//endregion
				//region RelativeLayout
				addView(RelativeLayout(context).apply {
					layoutParams = getLinearLayoutParams(0, -2).apply { weight = 1f }
					//region tvCenterPage
					addView(TextView(context).apply {
						id = R.id.tvCenterPage
						layoutParams = getRelativeLayoutParams(-2, dpToPx(30f)).apply {
							addRule(RelativeLayout.CENTER_IN_PARENT)
						}
						gravity = Gravity.CENTER
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
						setTypeface(typeface, Typeface.BOLD)
					})
					//endregion
				})
				//endregion
				//region RelativeLayout
				addView(RelativeLayout(context).apply {
					layoutParams = getLinearLayoutParams(0, -2).apply { weight = 1f }
					//region tvLastPage
					addView(TextView(context).apply {
						id = R.id.tvLastPage
						layoutParams = getRelativeLayoutParams(-2, dpToPx(30f)).apply {
							addRule(RelativeLayout.CENTER_IN_PARENT)
						}
						gravity = Gravity.CENTER
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
						setTypeface(typeface, Typeface.BOLD)
					})
					//endregion
				})
				//endregion
				//region tvNext
				addView(TextView(context).apply {
					id = R.id.tvNext
					layoutParams = getLinearLayoutParams(0, -2).apply { weight = 1f }
					gravity = Gravity.CENTER
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 32f)
					setTypeface(typeface, Typeface.BOLD)
					text = resources.getString(R.string.arrow_right)
				})
				//endregion
				//endregion
			})
			//region
		}
	}
}