package chata.can.chata_ai.dialog.manageData

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.chat.TypeDataQuery
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class ManageDataDialog(
	private val context1: Context,
	private val typeData: TypeColumnData,
	private val queryBase: QueryBase?= null
)//: BaseDialog(context1, R.layout.dialog_manage_data, false), View.OnClickListener
{
	private lateinit var btnApply: Button
	private lateinit var rvColumn: RecyclerView
	val model = BaseModelList<FilterColumn>()
	private lateinit var adapter: FilterColumnAdapter

//	override fun onCreateView() {
//		super.onCreateView()
//		setData()
//	}

	//region custom build Dialog
	private lateinit var llParent: View
	//endregion

	companion object {

	}

	fun showDialog()
	{
		llParent = LinearLayout(context1).apply {
			layoutParams = LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT)
			orientation = LinearLayout.VERTICAL
			id = R.id.rlParent
			paddingAll(4f)
			rvColumn = RecyclerView(context1).apply {
				layoutParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					0).apply {
					weight = 1f
				}
				id = R.id.rvColumn
			}
//			btnApply = Button(ContextThemeWrapper(context1, android.R.attr.buttonBarButtonStyle)).apply {
			btnApply = Button(context1).apply {
				layoutParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT).apply {
						weight = 0f
				}
				id = R.id.btnApply
				paddingAll(8f)
				isAllCaps = false
			}
		}

		AlertDialog.Builder(context1).create().apply {
			setView(llParent)
			setCancelable(false)
			show()
		}
	}

	private fun setData()
	{
		queryBase?.run {
			if (typeData == TypeColumnData.SELECTABLE)
			{
				for (column in aCurrency)
				{
					model.add(FilterColumn(column.displayName))
				}
				for (column in aQuality)
				{
					model.add(FilterColumn(column.displayName, false))
				}

				//btnApply.setOnClickListener(this@ManageDataDialog)
			}
			else
			{
				for (column in aCommon)
				{
					model.add(FilterColumn(column.displayName, isOnlyText = true))
				}
				btnApply.visibility = View.GONE
			}
		}
		adapter = FilterColumnAdapter(model)
		rvColumn.layoutManager = LinearLayoutManager(context1)
		rvColumn.adapter = adapter

	}

//	override fun setColors()
//	{
//		context.run {
//			ThemeColor.currentColor.run {
//				btnApply.background = getBackgroundColor(
//					getParsedColor(R.color.blue_chata_circle), getParsedColor(R.color.blue_chata_circle))
//				btnApply.setTextColor(pDrawerTextColorPrimary)
//			}
//		}
//	}

//	override fun setViews()
//	{
//		btnApply = findViewById(R.id.btnApply)
//		rvColumn = findViewById(R.id.rvColumn)
//	}
//
//	override fun onClick(v: View?)
//	{
//		v?.run {
//			dismiss()
//		}
//	}

	private fun getBackgroundColor(color: Int, borderColor: Int) =
		DrawableBuilder.setGradientDrawable(color, 12f, 3, borderColor)
}