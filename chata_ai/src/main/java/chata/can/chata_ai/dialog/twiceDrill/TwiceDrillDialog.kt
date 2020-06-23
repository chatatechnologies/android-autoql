package chata.can.chata_ai.dialog.twiceDrill

import android.annotation.SuppressLint
import android.content.Context
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog
import chata.can.chata_ai.dialog.DrillDownContract
import chata.can.chata_ai.pojo.chat.QueryBase

class TwiceDrillDialog(
	context: Context,
	private val queryBase: QueryBase
): BaseDialog(context, R.layout.dialog_drill_down), DrillDownContract
{
	private val presenter = TwiceDrillPresenter(queryBase)

	override fun onCreateView()
	{
		super.onCreateView()
		setData()
	}

	override fun setViews()
	{

	}

	override fun setColors()
	{

	}

	@SuppressLint("SetJavaScriptEnabled")
	override fun loadDrillDown(queryBase: QueryBase)
	{

	}

	private fun setData()
	{
		presenter.getQueryDrillDown()
	}
}