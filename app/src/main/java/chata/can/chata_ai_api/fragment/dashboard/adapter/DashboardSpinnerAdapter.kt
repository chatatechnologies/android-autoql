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
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai_api.R

class DashboardSpinnerAdapter(context: Context, aData: List<String>)
	: ArrayAdapter<String>(context, R.layout.row_spinner_dashboard, aData)
{
	var positionSelect = 0

	private fun backgroundByPosition(position: Int): GradientDrawable
	{
		return GradientDrawable().apply {
			shape = GradientDrawable.RECTANGLE
			setColor(
				ContextCompat.getColor(context,
					if (position == positionSelect)
						R.color.selected_gray
					else
						ThemeColor.currentColor.drawerBackgroundColor)
			)
		}
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: LayoutInflater.from(context).inflate(
			R.layout.row_spinner_dashboard, nullParent)

		val tv = view.findViewById<TextView>(android.R.id.text1)

		tv.text = getItem(position) ?: ""
		view.background = backgroundByPosition(position)
		return view
	}
}