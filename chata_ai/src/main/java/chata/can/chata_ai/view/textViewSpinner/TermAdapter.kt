package chata.can.chata_ai.view.textViewSpinner

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.Constant.nullParent
import chata.can.chata_ai.R

class TermAdapter(context: Context, private val aData: List<String>)
	: ArrayAdapter<String>(context,R.layout.row_term, aData)
{
	override fun getCount() = aData.size

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: LayoutInflater.from(context).inflate(
			R.layout.row_term, nullParent)
		view.findViewById<ImageView>(R.id.ivRemove).visibility = View.GONE
		return view
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: LayoutInflater.from(context).inflate(
			R.layout.row_term, nullParent)
		val iv = view.findViewById<ImageView>(R.id.ivRemove)
		val tv = view.findViewById<TextView>(R.id.tvRemove)

		val color = if (position == count - 1)
		{
			iv.visibility = View.VISIBLE
			Color.RED
		}
		else
		{
			iv.visibility = View.GONE
			Color.BLACK
		}

		iv.setColorFilter(color)
		tv.text = getItem(position) ?: ""
		tv.setTextColor(color)
		return view
	}
}