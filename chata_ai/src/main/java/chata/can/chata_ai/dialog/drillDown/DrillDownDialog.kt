package chata.can.chata_ai.dialog.drillDown

import android.content.Context
import android.webkit.WebView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.view.gif.GifView

class DrillDownDialog(context: Context, private val queryBase: QueryBase)
	: BaseDialog(context, R.layout.dialog_drill_down)
{
	private var tvTitle: TextView ?= null
	private var ivLoad: GifView ?= null
	private var wbDrillDown: WebView ?= null

	private val presenter = DrillDownPresenter(queryBase)

	override fun onCreateView()
	{
		super.onCreateView()
		setData()
	}

	override fun setViews()
	{
		tvTitle = findViewById(R.id.tvTitle)
		ivLoad = findViewById(R.id.ivLoad)
		wbDrillDown = findViewById(R.id.wbDrillDown)
	}

	override fun setColors()
	{

	}

	private fun setData()
	{
		tvTitle?.text = queryBase.query
		presenter.getQueryDrillDown()
	}
}