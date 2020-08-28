package chata.can.chata_ai_api

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.view.gif.GifView

object DashboardView
{
	private const val MATCH_PARENT = -1
	private const val WRAP_CONTENT = -2

	private const val GRAVITY_CENTER = 17

	private fun getLinearLayoutBase(context: Context): LinearLayout
	{
		return LinearLayout(context).apply {
			layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
			orientation = LinearLayout.VERTICAL
		}
	}

	private fun getHeaderDashboard(context: Context): LinearLayout
	{
		return getLinearLayoutBase(context).apply {

			val tvTitle = TextView(context).apply {
				layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
				setTextColor(ContextCompat.getColor(context, R.color.blue))
				setTypeface(typeface, Typeface.BOLD)
				setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
				id = R.id.tvTitle
			}
			val view = View(context).apply {
				layoutParams = LinearLayout.LayoutParams(dpToPx(100f), dpToPx(1f))
				setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
				margin(bottom = 5f, top = 5f)
			}

			addView(tvTitle)
			addView(view)
		}
	}

	private fun getExecute(context: Context, idView: Int): TextView
	{
		return TextView(context).apply {
			layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(180f))
			gravity = GRAVITY_CENTER
			val message = context.getString(R.string.execute_run_dashboard)
			text = message
			id = idView
		}
	}

	//region row execute
	fun getRowExecute(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			val ll1 = getLinearLayoutBase(context).apply {
				margin(8f, 8f, 8f, 8f)
				paddingAll(8f)
				id = R.id.ll1

				val llHeader = getHeaderDashboard(context)
				val tvExecute = getExecute(context, R.id.tvExecute)
				val iView = View(context).apply {
					layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(1f))
					margin(start = 4f, end = 4f)
					id = R.id.iView
				}
				val tvExecute2 = getExecute(context, R.id.tvExecute2)

				addView(llHeader)
				addView(tvExecute)
				addView(iView)
				addView(tvExecute2)
			}
			addView(ll1)
		}
	}
	//endregion
	//region row loading
	fun getRowLoading(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			val ll1 = getLinearLayoutBase(context).apply {
				margin(8f, 8f, 8f, 8f)
				paddingAll(8f)
				id = R.id.ll1

				val llHeader = getHeaderDashboard(context)
				val rlLoad = RelativeLayout(context).apply {
					layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(180f))
					id = R.id.rlLoad

					val gifView = GifView(context).apply {
						val layoutParams1 = RelativeLayout.LayoutParams(dpToPx(80f), dpToPx(80f))
						layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT)
						layoutParams = layoutParams1
					}
					addView(gifView)
				}

				addView(llHeader)
				addView(rlLoad)
			}
			addView(ll1)
		}
	}
	//endregion
	//region row support
	fun getRowContent(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			val ll1 = getLinearLayoutBase(context).apply {
				margin(8f, 8f, 8f, 8f)
				paddingAll(8f)
				id = R.id.ll1

				val llHeader = getHeaderDashboard(context)
				val tvContent = TextView(context).apply {
					layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, dpToPx(80f))
					gravity = GRAVITY_CENTER
					setTextColor(ContextCompat.getColor(context, R.color.black))
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
					id = R.id.tvContent
				}

				addView(llHeader)
				addView(tvContent)
			}
			addView(ll1)
		}
	}
	//endregion
	//region
	fun getRowSuggestion(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			val ll1 = getLinearLayoutBase(context).apply {
				margin(8f, 8f, 8f, 8f)
				paddingAll(8f)
				id = R.id.ll1

				val llHeader = getHeaderDashboard(context)
				val llRoot = getLinearLayoutBase(context).apply {
					margin(start = 12f, end = 12f)
					paddingAll(5f)

					val tvContent = TextView(context).apply {
						layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
						margin(5f, 5f, 5f, 5f)
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
						id = R.id.tvContent
					}
					val llSuggestion = getLinearLayoutBase(context).apply {
						margin(5f, 5f, 5f, 5f)
						id = R.id.llSuggestion
					}
					addView(tvContent)
					addView(llSuggestion)
				}
				addView(llHeader)
				addView(llRoot)
			}
			addView(ll1)
		}
	}
	//endregion
}