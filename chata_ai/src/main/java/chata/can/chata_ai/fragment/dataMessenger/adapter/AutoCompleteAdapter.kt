package chata.can.chata_ai.fragment.dataMessenger.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.nullValue

class AutoCompleteAdapter(context: Context, resource: Int): ArrayAdapter<String>(context, resource)
{
	private val aData = ArrayList<String>()

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.row_spinner, nullValue)
		val tv = view.findViewById<TextView>(android.R.id.text1)
		tv.text = getItem(position)
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