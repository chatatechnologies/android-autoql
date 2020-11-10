package chata.can.chata_ai.dialog.drillDown

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog
import chata.can.chata_ai.dialog.DrillDownContract
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.view.gif.GifView

class DrillDownDialog(
	context: Context,
	private val queryBase: QueryBase)
	: BaseDialog(context, R.layout.dialog_drill_down),
	DrillDownContract
{
	private lateinit var rlParent: View
	private lateinit var tvTitle: TextView
	private lateinit var ivCancel: ImageView
	private lateinit var ivLoad: GifView
	private lateinit var wbDrillDown: WebView

	private val presenter = DrillDownPresenter(queryBase, this)

	override fun onCreateView()
	{
		super.onCreateView()
		setData()
	}

	override fun setViews()
	{
		rlParent = findViewById(R.id.rlParent)
		tvTitle = findViewById(R.id.tvTitle)
		ivCancel = findViewById(R.id.ivCancel)
		ivLoad = findViewById(R.id.ivLoad)
		wbDrillDown = findViewById(R.id.wbDrillDown)
	}

	override fun setColors()
	{
		ThemeColor.currentColor.run {
			context.let {
				tvTitle.setTextColor(it.getParsedColor(drawerTextColorPrimary))
				rlParent.setBackgroundColor(it.getParsedColor(drawerBackgroundColor))
				ivCancel.setColorFilter(it.getParsedColor(R.color.chata_drawer_background_color_dark))
			}
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	override fun loadDrillDown(queryBase: QueryBase)
	{
		if (queryBase.contentHTML.isEmpty())
		{
			queryBase.viewDrillDown = this@DrillDownDialog
		}
		else
		{
			wbDrillDown.run {
				settings.javaScriptEnabled = true
				clearCache(true)
				loadDataWithBaseURL(null, queryBase.contentHTML,"text/html","UTF-8", null)
				webViewClient = object: WebViewClient()
				{
					override fun onPageFinished(view: WebView?, url: String?)
					{
						ivLoad.visibility = View.GONE
						wbDrillDown.visibility = View.VISIBLE
					}
				}
			}
		}
	}

	private fun setData()
	{
		tvTitle.text = queryBase.query

		ivCancel.setOnClickListener {
			dismiss()
		}
		presenter.getQueryDrillDown()
	}
}