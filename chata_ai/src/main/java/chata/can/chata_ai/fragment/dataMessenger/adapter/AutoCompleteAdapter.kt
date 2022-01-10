package chata.can.chata_ai.fragment.dataMessenger.adapter

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.extension.textSize
import chata.can.chata_ai.pojo.color.ThemeColor

class AutoCompleteAdapter(context: Context): ArrayAdapter<String>(context, 0)
{
	private val aData = ArrayList<String>()

	private fun getTextView(context: Context): TextView
	{
		return TextView(context).apply {
			layoutParams = ViewGroup.LayoutParams(-1, -2)
			setBackgroundColor(Color.TRANSPARENT)
			isSingleLine = true
			textAlignment = View.TEXT_ALIGNMENT_INHERIT
			ellipsize = TextUtils.TruncateAt.MARQUEE
			paddingAll(left = 20f, top = 4f, right = 14f, bottom = 4f)
			id = android.R.id.text1
			textSize(16f)
		}
	}

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: getTextView(context)
		view.findViewById<TextView>(android.R.id.text1).run {
			text = getItem(position)
			setTextColor(ThemeColor.currentColor.pDrawerTextColorPrimary)
		}
		return view
	}

	override fun add(string: String?)
	{
		aData.add(string ?: return)
		aData.sort()
		notifyDataSetChanged()
	}

	override fun addAll(collection: MutableCollection<out String>)
	{
		aData.addAll(collection)
		aData.sort()
		notifyDataSetChanged()
	}

	override fun clear()
	{
		aData.clear()
	}

	override fun getCount() = aData.size

	override fun getItem(position: Int): String
	{
		return if (position < 0 || position > aData.size)
			""
		else
			aData[position]
	}

	override fun getFilter() = CustomFilter()

	inner class CustomFilter: Filter()
	{
		override fun performFiltering(constraint: CharSequence?): FilterResults
		{
			val results = FilterResults()
			constraint?.let {
				results.values = aData
				results.count = aData.size
			}
			return results
		}

		override fun publishResults(constraint: CharSequence?, results: FilterResults?)
		{
			results?.let {
				if (it.count > 0)
				{
					notifyDataSetChanged()
				}
				else
				{
					notifyDataSetInvalidated()
				}
			} ?: run {
				notifyDataSetInvalidated()
			}
		}
	}
}