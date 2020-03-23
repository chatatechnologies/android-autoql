package chata.can.chata_ai.activity.chat.adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class AutoCompleteAdapter(context: Context, resource: Int)
	: ArrayAdapter<String>(context, resource)
{
	private val aData = ArrayList<String>()

	override fun add(string: String?)
	{
		aData.add(string ?: return)
		aData.sort()
		notifyDataSetChanged()
	}

	override fun clear() = aData.clear()

	override fun getCount() = aData.size

	override fun getItem(position: Int): String?
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