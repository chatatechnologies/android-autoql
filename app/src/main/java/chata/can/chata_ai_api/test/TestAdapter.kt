package chata.can.chata_ai_api.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai_api.R

class TestAdapter(
	private val model: ArrayList<String>
): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
	override fun getItemCount() = model.size

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
	{
		if (holder is TestHolder)
		{
			holder.text1?.text = model[position]
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
	{
		val inflater = LayoutInflater.from(parent.context)
		return TestHolder(inflater.inflate(R.layout.simple_list_item, null))
	}
}