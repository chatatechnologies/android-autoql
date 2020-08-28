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

object DashboardView
{
	private const val MATCH_PARENT = -1
	private const val WRAP_CONTENT = -2

	private fun getLinearLayoutBase(context: Context): LinearLayout
	{
		return LinearLayout(context).apply {
			layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
			orientation = LinearLayout.VERTICAL
		}
	}

	fun getHeaderDashboard(context: Context)
	{
		getLinearLayoutBase(context).apply {

			val tvTitle = TextView(context).apply {
				layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
				setTextColor(ContextCompat.getColor(context, R.color.blue))
				setTypeface(typeface, Typeface.BOLD)
				setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
			}
			val view = View(context).apply {
				layoutParams = LinearLayout.LayoutParams(dpToPx(100f), dpToPx(100f))
				setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
				margin(bottom = 5f, top = 5f)
			}

			addView(tvTitle)
			addView(view)
		}
	}

	//region row execute
	fun getRowExecute(context: Context)
	{
		val rlRoot = RelativeLayout(context)
		val llRoot = getLinearLayoutBase(context).apply {
			margin(8f, 1f, 8f, 8f)
			paddingAll(8f)
		}




		rlRoot.addView(llRoot)
	}
	//endregion
}