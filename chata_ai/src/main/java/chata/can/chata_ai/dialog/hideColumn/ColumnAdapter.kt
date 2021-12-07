package chata.can.chata_ai.dialog.hideColumn

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.holder.Holder
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.QueryBase

class ColumnAdapter(
	private val model: BaseModelList<ColumnQuery>,
	private val queryBase: QueryBase,
	private val view: ColumnChanges.AllColumn
): BaseAdapter(model), ColumnChanges.SingleColumn
{
	private lateinit var tvColumnName: TextView
	private lateinit var cbCheck: CheckBox

	private fun getColumn(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT)

			//region inner child
			addView(RelativeLayout(context).apply {
				layoutParams = RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT)
				margin(12f, end = 12f)

				tvColumnName = TextView(context).apply {
					layoutParams = RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
						addRule(RelativeLayout.START_OF, R.id.cbCheck)
					}
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
					id = R.id.tvColumnName
				}
				addView(tvColumnName)

				cbCheck = CheckBox(context).apply {
					layoutParams = RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
						addRule(RelativeLayout.END_OF)
					}
					id = R.id.cbCheck
				}
				addView(cbCheck)
			})
			//endregion
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
//		val layoutInflater = LayoutInflater.from(parent.context)
//		return ColumnHolder(layoutInflater.inflate(R.layout.row_column, parent, false), this)
		return ColumnHolder(
			getColumn(parent.context), this)
	}

	override fun changeVisible(position: Int, value: Boolean)
	{
		model[position]?.let {
			if (it.isVisible != value)
			{
				it.isVisible = value
			}
		}
		view.changeAllColumn(hasSameStatus())
	}

	private fun hasSameStatus(): Boolean
	{
		model.getData().filter { it.isVisible }.run {
			return if (count() == model.countData()) first().isVisible else false
		}
	}

	fun hasChanges(): Boolean
	{
		var hasChanges = false
		val aCompare = queryBase.aColumn
		for (index in 0 until model.countData())
		{
			model[index]?.let { value ->
				if (value.isVisible != aCompare[index].isVisible)
				{
					aCompare[index].isVisible = value.isVisible
					hasChanges = true
				}
			}
		}
		return hasChanges
	}
}