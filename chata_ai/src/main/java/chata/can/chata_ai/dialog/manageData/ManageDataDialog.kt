package chata.can.chata_ai.dialog.manageData

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.chat.ColumnQuery
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class ManageDataDialog(
	private val context1: Context,
	private val typeData: TypeColumnData,
	private val content: String,
	private val webView: View,
	private val queryBase: QueryBase?= null
): ManageDialogView
{
	private lateinit var dialog: AlertDialog
	private lateinit var btnApply: Button
	private lateinit var rvColumn: RecyclerView
	private val model = BaseModelList<FilterColumn>()
	private lateinit var adapter: FilterColumnAdapter
	private lateinit var llParent: View
	private val aCurrency1 = ArrayList<FilterColumn>()
	private val aQuality1 = ArrayList<FilterColumn>()

	fun showDialog()
	{
		llParent = LinearLayout(context1).apply {
			layoutParams = LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT)
			ThemeColor.currentColor.run {
				background = DrawableBuilder.setGradientDrawable(pDrawerBackgroundColor, 18f)
			}
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
			addView(rvColumn)

			btnApply = Button(context1).apply {
				layoutParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT).apply {
					weight = 0f
				}
				id = R.id.btnApply
				isAllCaps = false
				paddingAll(8f)
				setText(R.string.apply)
			}
			addView(btnApply)
		}

		setColors()
		setData()

		dialog = AlertDialog.Builder(context1).create().apply {
			setView(llParent)
			window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
			show()
		}
	}

	private fun setColors()
	{
		context1.run {
			btnApply.run {
				background = getBackgroundColor(
					getParsedColor(R.color.blue_chata_circle), getParsedColor(R.color.blue_chata_circle))
				setTextColor(getParsedColor(R.color.white))
			}
		}
	}

	private fun setData()
	{
		queryBase?.run {
			if (typeData == TypeColumnData.SELECTABLE)
			{
				model.add(FilterColumn("Currency", isOnlyText = true))
				for (pair in aCurrency)
				{
					val fc = FilterColumn(pair.second.displayName, pair.second.isSelected)
					model.add(fc)
					aCurrency1.add(fc)
				}
				model.add(FilterColumn("Quantity", isOnlyText = true))
				for (pair in aQuality)
				{
					val fc = FilterColumn(pair.second.displayName, pair.second.isSelected)
					model.add(fc)
					aQuality1.add(fc)
				}
				btnApply.setOnClickListener { setCategoriesIgnore() }
			}
			else
			{
				for (pair in aCommon)
				{
					val isSelected = pair.second.displayName == content
					model.add(
						FilterColumn(
							pair.second.displayName,
							isSelected,
							isOnlyText = true,
							allowClick = true,
							indexColumn = pair.first
						)
					)
				}
				btnApply.visibility = View.GONE
			}
		}
		adapter = FilterColumnAdapter(model, this, aCurrency1, aQuality1)
		rvColumn.layoutManager = LinearLayoutManager(context1)
		rvColumn.adapter = adapter
	}

	private fun updateFilterColumn(
		aFilter: ArrayList<FilterColumn>,
		aCurrency: ArrayList<Pair<Int, ColumnQuery>>)
	{
		for (index in aFilter.indices)
		{
			val filterColumn = aFilter[index]
			val filterBase = aCurrency[index].second
			filterBase.isSelected = filterColumn.isSelected
		}
	}

	private fun setCategoriesIgnore()
	{
		var isCurrency = true
		val array = ArrayList<Int>()
		val arrayOut = ArrayList<Int>()
		val aSource = when
		{
			aCurrency1.any { it.isSelected } ->
			{
				isCurrency = true
				aCurrency1
			}
			aQuality1.any { it.isSelected } ->
			{
				isCurrency = false
				aQuality1
			}
			else -> ArrayList()
		}
		queryBase?.run {
			updateFilterColumn(aCurrency1, aCurrency)
			updateFilterColumn(aQuality1, aQuality)
		}
		for (index in aSource.indices)
		{
			if (!aSource[index].isSelected)
				array.add(index)
			else
				arrayOut.add(index)
		}
		val hasOnlyOne = if (aSource.size - array.size == 1)
		{
			val aMinus = arrayOut.minus(array)
			", ${aMinus[0]}"
		} else ""
		Handler(Looper.getMainLooper()).postDelayed({
			(webView as? WebView)?.loadUrl("javascript:setMultiCategory($array, $isCurrency$hasOnlyOne);")
		}, 100)
		dialog.dismiss()
	}

	override fun setIndexData(indexRoot: Int, indexCommon: Int)
	{
		Handler(Looper.getMainLooper()).postDelayed({
			(webView as? WebView)?.loadUrl("javascript:setIndexData($indexRoot, $indexCommon)")
		}, 100)
		dialog.dismiss()
	}

	override fun statusApply(isEnable: Boolean)
	{
		btnApply.run {
			isEnabled = isEnable
			alpha = if(isEnable) 1f else 0.2f
		}
	}

	private fun getBackgroundColor(color: Int, borderColor: Int) =
		DrawableBuilder.setGradientDrawable(color, 12f, 3, borderColor)
}