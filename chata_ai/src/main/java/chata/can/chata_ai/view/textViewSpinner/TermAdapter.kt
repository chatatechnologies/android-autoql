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
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.color.ThemeColor

class TermAdapter(context: Context, private val aData: List<String>)
	: ArrayAdapter<String>(context,R.layout.row_term, aData)
{
	private var _background = 0
	private var _textColor = 0
	private var _dangerColor = 0

	init {
		with(ThemeColor.currentColor) {
			_background = context.getParsedColor(drawerBackgroundColor)
			_textColor = context.getParsedColor(drawerTextColorPrimary)
			_dangerColor = context.getParsedColor(dangerColor)
		}
	}

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
		val llParent = view.findViewById<View>(R.id.llParent)
		val iv = view.findViewById<ImageView>(R.id.ivRemove)
		val tv = view.findViewById<TextView>(R.id.tvRemove)

		llParent.setBackgroundColor(_background)

		val color = if (position == count - 1)
		{
			iv.visibility = View.VISIBLE
			_dangerColor
		}
		else
		{
			iv.visibility = View.GONE
			_textColor
		}

		iv.setColorFilter(color)
		tv.text = getItem(position) ?: ""
		tv.setTextColor(color)
		return view
	}
}