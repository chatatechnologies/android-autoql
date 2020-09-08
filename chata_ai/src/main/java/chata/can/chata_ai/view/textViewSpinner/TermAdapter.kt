package chata.can.chata_ai.view.textViewSpinner

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import chata.can.chata_ai.Constant.nullParent

class TermAdapter(context: Context, private val aData: List<String>)
	: ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, aData)
{
	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: LayoutInflater.from(context).inflate(
			android.R.layout.simple_spinner_item, nullParent)
		val tv = view.findViewById<TextView>(android.R.id.text1)
		tv.text = getItem(position) ?: ""
		if (position == aData.size - 1)
			tv.setTextColor(Color.RED)
		else
			tv.setTextColor(Color.BLACK)
		return view
	}
}