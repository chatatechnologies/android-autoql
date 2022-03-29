package chata.can.chata_ai_api.fragment.dashboard.holder.suggestion

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import chata.can.chata_ai.Constant
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai_api.R

class SuggestionAdapter(
	context: Context,
	aData: List<String>
) : ArrayAdapter<String>(context, 0, aData)
{
	private val baseColor = R.color.white
	private val selectedColor = R.color.background_dashboard_select
	private var positionSelected = 0

	private fun backgroundByPosition(position: Int): GradientDrawable
	{
		return GradientDrawable().apply {
			shape = GradientDrawable.RECTANGLE
			setColor(
				context.getParsedColor(
					if (position == positionSelected)
						selectedColor
					else
						baseColor
				)
			)
		}
	}

	fun updatePositionSelected(newPosition: Int)
	{
		positionSelected = newPosition
	}

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: LayoutInflater.from(context).inflate(
			R.layout.row_spinner_dashboard, Constant.nullParent
		)
		val tv = view.findViewById<TextView>(R.id.tvDashboard)
		tv.text = getItem(position) ?: ""
		return view
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: LayoutInflater.from(context).inflate(
			R.layout.row_spinner_dashboard, Constant.nullParent
		)
		val tv = view.findViewById<TextView>(R.id.tvDashboard)

		tv.text = getItem(position) ?: ""
		val textColor = tv.getParsedColor(R.color.text_dashboard_spinner)
		tv.setTextColor(textColor)
		tv.background = backgroundByPosition(position)
		return view
	}
}