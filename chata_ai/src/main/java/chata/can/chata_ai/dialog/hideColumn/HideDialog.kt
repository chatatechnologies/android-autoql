package chata.can.chata_ai.dialog.hideColumn

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class HideDialog(
	context: Context,
	private val queryBase: QueryBase ?= null
) : BaseDialog(context, R.layout.dialog_hide, false), View.OnClickListener
{
	private lateinit var rlParent: View
	private lateinit var tvTitle: TextView
	private lateinit var ivCancel: ImageView
	private lateinit var vBorder: View
	private lateinit var tvColumnName: TextView
	private lateinit var tvVisibility: TextView
	private lateinit var cbAll: CheckBox
	private lateinit var rvColumn: RecyclerView
	private lateinit var btnCancel: Button
	private lateinit var btnApply: Button
	private lateinit var adapter: ColumnAdapter
	private val model = BaseModelList<Pair<*,*>>()

	override fun onCreateView()
	{
		super.onCreateView()
		setData()
		queryBase?.let { queryBase ->
			for (column in queryBase.aColumn)
			{
				val pair = Pair(column.displayName, column.isVisible)
				model.add(pair)
			}
			adapter = ColumnAdapter(model)
			rvColumn.layoutManager = LinearLayoutManager(context)
			rvColumn.adapter = adapter
		}
	}

	private fun setData()
	{
		tvTitle.text = context.getString(R.string.show_hide_column)
		ivCancel.setOnClickListener(this)
		btnCancel.setOnClickListener(this)
		btnApply.setOnClickListener(this)
	}

	override fun setViews()
	{
		rlParent = findViewById(R.id.rlParent)
		tvTitle = findViewById(R.id.tvTitle)
		ivCancel = findViewById(R.id.ivCancel)
		vBorder = findViewById(R.id.vBorder)
		tvColumnName = findViewById(R.id.tvColumnName)
		tvVisibility = findViewById(R.id.tvVisibility)
		cbAll = findViewById(R.id.cbAll)
		rvColumn = findViewById(R.id.rvColumn)
		btnCancel = findViewById(R.id.btnCancel)
		btnApply = findViewById(R.id.btnApply)
	}

	override fun setColors()
	{
		context.run {
			ThemeColor.currentColor.run {
				tvTitle.setTextColor(pDrawerTextColorPrimary)
				tvColumnName.setTextColor(pDrawerTextColorPrimary)
				tvVisibility.setTextColor(pDrawerTextColorPrimary)
				vBorder.setBackgroundColor(pDrawerBorderColor)
				btnApply.background = getBackgroundColor(
					getParsedColor(R.color.blue_chata_circle), getParsedColor(R.color.blue_chata_circle))
				btnApply.setTextColor(pDrawerTextColorPrimary)
				btnCancel.background = getBackgroundColor(pDrawerBackgroundColor, pDrawerBorderColor)
				btnCancel.setTextColor(pDrawerTextColorPrimary)
			}
		}
	}

	override fun onClick(view: View?)
	{
		view?.let { it ->
			when(it.id)
			{
				R.id.ivCancel, R.id.btnCancel -> { dismiss() }
				R.id.btnApply -> {  }
			}
		}
	}

	private fun getBackgroundColor(color: Int, borderColor: Int) =
		DrawableBuilder.setGradientDrawable(color, 12f, 3, borderColor)
}