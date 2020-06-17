package chata.can.chata_ai.pojo.base

import android.view.View
import android.widget.AdapterView

interface ItemSelectedListener: AdapterView.OnItemSelectedListener
{
	fun onSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)

	override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
	{
		onSelected(parent, view, position, id)
	}

	override fun onNothingSelected(p0: AdapterView<*>?){}
}