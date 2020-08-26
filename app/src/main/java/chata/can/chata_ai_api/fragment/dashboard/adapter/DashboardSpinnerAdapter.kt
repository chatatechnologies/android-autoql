package chata.can.chata_ai_api.fragment.dashboard.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai_api.R

class DashboardSpinnerAdapter(context: Context, aData: List<String>)
	: ArrayAdapter<String>(context, R.layout.row_spinner_dashboard, aData)
{
	var positionSelect = 0

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: LayoutInflater.from(context).inflate(
			R.layout.row_spinner_dashboard, nullParent)

		val tv = view.findViewById<TextView>(android.R.id.text1)

		tv.text = getItem(position) ?: ""

		if (position == positionSelect)
		{
			view.setBackgroundColor(Color.RED)
		}

		return view
	}
}