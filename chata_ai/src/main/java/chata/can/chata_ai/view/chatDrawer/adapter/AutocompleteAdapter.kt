package chata.can.chata_ai.view.chatDrawer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.pojo.nullValue

class AutocompleteAdapter(cContext: Context)
	: ArrayAdapter<String>(cContext, android.R.layout.simple_spinner_item)
{
	private val list = ArrayList<String>()

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
	{
		val view = convertView ?:LayoutInflater.from(context).inflate(R.layout.spinner_item, nullValue)
		view.findViewById<TextView>(android.R.id.text1)?.let {
			it.setTextColor(ContextCompat.getColor(context, R.color.black))
			it.text = getItem(position) ?: ""
		}
		return view
	}

	override fun add(obj: String?)
	{
		list.add(obj ?: "")
		list.sort()
		notifyDataSetChanged()
	}

	override fun addAll(collection: MutableCollection<out String>)
	{
		list.addAll(collection)
		list.sort()
		notifyDataSetChanged()
	}

	override fun clear() = list.clear()

	override fun getCount() = list.size

	override fun getItem(position: Int): String?
	{
		return if (position < 0 || position >= list.size) null
		else list[position]
	}

	override fun getFilter() = CustomerFilter()

	inner class CustomerFilter: Filter()
	{
		override fun performFiltering(constraint: CharSequence?): FilterResults
		{
			val results = FilterResults()
			constraint?.let {
				results.values = list
				results.count = list.size
			}
			return results
		}

		override fun publishResults(constraint: CharSequence?, results: FilterResults?)
		{
			results?.let {
				if (it.count > 0)
					notifyDataSetChanged()
				else
					notifyDataSetInvalidated()
			} ?: run {
				notifyDataSetInvalidated()
			}
		}
	}
}