package chata.can.chata_ai.fragment.exploreQuery

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.view.ContextThemeWrapper
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
import chata.can.chata_ai.view.gif.KGifView

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
				descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS
				isFocusableInTouchMode = true
				id = R.id.llQuery
				layoutParams = getRelativeLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
				paddingAll(8f)
				//region ivSearch
				addView(ImageView(context).apply {
					id = R.id.ivSearch
					gravity = Gravity.CENTER
					layoutParams = getRelativeLayoutParams(dpToPx(40f), dpToPx(40f)).apply {
						addRule(RelativeLayout.ALIGN_PARENT_END)
					}
					margin(8f, end = 8f)
					setImageResource(R.drawable.ic_search)
					scaleType = ImageView.ScaleType.FIT_CENTER
				})
				//endregion
				//region etQuery
				addView(EditText(context).apply {
					setBackgroundColor(Color.TRANSPARENT)
					hint = resources.getString(R.string.explore_queries_hint)
					id = R.id.etQuery
					inputType = InputType.TYPE_CLASS_TEXT
					layoutParams = getRelativeLayoutParams(-1, dpToPx(40f)).apply {
						addRule(RelativeLayout.START_OF, R.id.ivSearch)
					}
					paddingAll(16f,8f, 16f, 8f)
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
					{
						importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
					}
				})
				//endregion
			})
			//endregion
			//region middle LinearLayout
			addView(LinearLayout(context).apply {
				layoutParams = getLinearLayoutParams(-1, 0).apply { weight = 1f }
				orientation = LinearLayout.VERTICAL
				//region rvRelatedQueries
				val contextThemeWrapper = ContextThemeWrapper(context, R.style.ScrollbarRecyclerview)
				addView(RecyclerView(contextThemeWrapper).apply {
					getLinearLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
					visibility = View.GONE
					id = R.id.rvRelatedQueries
					isVerticalScrollBarEnabled = true
					isHorizontalScrollBarEnabled = true
				})
				//endregion
				//region rlGif
				addView(RelativeLayout(context).apply {
					id = R.id.rlGif
					layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_ONLY)
					visibility = View.GONE
					//region KGifView
					addView(KGifView(context).apply {
						layoutParams = getRelativeLayoutParams(LayoutParams.WRAP_CONTENT_ONLY).apply {
							addRule(RelativeLayout.CENTER_IN_PARENT)
						}
					})
					//endregion
				})
				//endregion
				//region tvMsg1
				addView(TextView(context).apply {
					gravity = Gravity.CENTER
					id = R.id.tvMsg1
					layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
					margin(8f, 8f, 8f)
					text = resources.getString(R.string.explore_queries_msg_1)
					visibility = View.VISIBLE
				})
				//endregion
				//region tvMsg2
				addView(TextView(context).apply {
					gravity = Gravity.CENTER
					id = R.id.tvMsg2
					layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
					margin(8f, 8f, 8f)
					text = resources.getString(R.string.explore_queries_msg_2)
					visibility = View.VISIBLE
				})
				//endregion
			})
			//endregion
			//region llPager
			addView(LinearLayout(context).apply {
				id = R.id.llPager
				layoutParams = getLinearLayoutParams(-1, dpToPx(56f))
				orientation = LinearLayout.HORIZONTAL
				visibility = View.GONE
				//region tvPrevious
				addView(TextView(context).apply {
					id = R.id.tvPrevious
					layoutParams = getLinearLayoutParams(0, -2).apply { weight = 1f }
					gravity = Gravity.CENTER
					textSize(32f)
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
						textSize(20f)
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
						textSize(20f)
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
						textSize(20f)
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
					textSize(32f)
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