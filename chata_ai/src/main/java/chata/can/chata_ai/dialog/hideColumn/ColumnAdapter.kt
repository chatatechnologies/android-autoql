package chata.can.chata_ai.dialog.hideColumn

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import chata.can.chata_ai.R
import chata.can.chata_ai.adapter.BaseAdapter
import chata.can.chata_ai.extension.paddingAll
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
	private fun getView(context: Context): RelativeLayout
	{
		return RelativeLayout(context).apply {
			layoutParams = RelativeLayout.LayoutParams(-1, -2).apply {
				addRule(RelativeLayout.CENTER_VERTICAL)
				addRule(RelativeLayout.START_OF, R.id.cbCheck)
			}
			paddingAll(left = 12f, right = 12f)
			//region TextView
			addView(TextView(context).apply {
				layoutParams = RelativeLayout.LayoutParams(-1, -2)
				id = R.id.tvColumnName
				setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
			})
			//endregion
			//region CheckBox
			addView(CheckBox(ContextThemeWrapper(context, R.style.checkBoxStyle)).apply {
				layoutParams = RelativeLayout.LayoutParams(-2, -2).apply {
					addRule(RelativeLayout.ALIGN_PARENT_END)
				}
				id = R.id.cbCheck

			})
			//endregion
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
	{
		//val layoutInflater = LayoutInflater.from(parent.context)
		val view = getView(parent.context)
		//return ColumnHolder(layoutInflater.inflate(R.layout.row_column, nullValue), this)
		return ColumnHolder(view, this)
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