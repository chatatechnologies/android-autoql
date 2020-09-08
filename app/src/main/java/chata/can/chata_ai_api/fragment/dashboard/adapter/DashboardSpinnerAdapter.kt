package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai_api.R

class DashboardSpinnerAdapter(context: Context, aData: List<String>)
	: ArrayAdapter<String>(context, 0, aData)
{
	var positionSelect = 0

	private val pBackgroundColors = Pair(R.color.selected_gray, ThemeColor.currentColor.drawerBackgroundColor)
	private val pLineColors = Pair(R.color.white, R.color.selected_gray)

	private fun backgroundByPosition(position: Int, pColors: Pair<Int, Int>): GradientDrawable
	{
		return GradientDrawable().apply {
			shape = GradientDrawable.RECTANGLE
			setColor(
				context.getParsedColor(
					if (position == positionSelect)
						pColors.first
					else
					{
						if (position == positionSelect - 1)
							R.color.white
						else
							pColors.second
					}
				)
			)
		}
	}

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: LayoutInflater.from(context).inflate(
			R.layout.row_spinner_dashboard, nullParent)
		val tv = view.findViewById<TextView>(R.id.tvDashboard)
		tv.text = getItem(position) ?: ""
		return view
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: LayoutInflater.from(context).inflate(
			R.layout.row_spinner_dashboard, nullParent)
		val tv = view.findViewById<TextView>(R.id.tvDashboard)
		val iView = view.findViewById<View>(R.id.iView)

		tv.text = getItem(position) ?: ""
		tv.background = backgroundByPosition(position, pBackgroundColors)

		iView.background = backgroundByPosition(position, pLineColors)
		return view
	}
}