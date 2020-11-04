package chata.can.chata_ai.dialog.twiceDrill

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.webkit.JavascriptInterface
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

class TwiceDrillDialog(
	context: Context,
	private val queryBase: QueryBase,
	private var value1: String,
	private var value2: String = ""
): BaseDialog(context, R.layout.dialog_twice_drill_down), DrillDownContract
{
	private lateinit var rlParent: View
	private lateinit var ivCancel: ImageView
	private lateinit var tvTitle: TextView
	private lateinit var ivLoad1: View
	private lateinit var ivLoad2: View
	private lateinit var wbDrillDown1 : WebView
	private lateinit var wbDrillDown2 : WebView

	private val presenter = TwiceDrillPresenter(this, queryBase)

	override fun onCreateView()
	{
		super.onCreateView()
		setData()
	}

	override fun setViews()
	{
		rlParent = findViewById(R.id.rlParent)
		ivCancel = findViewById(R.id.ivCancel)
		tvTitle = findViewById(R.id.tvTitle)
		ivLoad1 = findViewById(R.id.ivLoad1)
		ivLoad2 = findViewById(R.id.ivLoad2)
		wbDrillDown1 = findViewById(R.id.wbDrillDown1)
		wbDrillDown2 = findViewById(R.id.wbDrillDown2)
	}

	override fun setColors()
	{
		ThemeColor.currentColor.run {
			tvTitle.context?.let {
				tvTitle.setTextColor(it.getParsedColor(drawerTextColorPrimary))
				rlParent.setBackgroundColor(it.getParsedColor(drawerBackgroundColor))
			}
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	override fun loadDrillDown(queryBase: QueryBase)
	{
		ivLoad2.visibility = View.VISIBLE
		wbDrillDown2.visibility = View.GONE

		if (queryBase.contentHTML.isEmpty())
		{
			queryBase.viewDrillDown = this@TwiceDrillDialog
		}
		else
		{
			wbDrillDown2.run {
				settings.javaScriptEnabled = true
				clearCache(true)
				loadDataWithBaseURL(null, queryBase.contentHTML,"text/html","UTF-8", null)
				webViewClient = object: WebViewClient()
				{
					override fun onPageFinished(view: WebView?, url: String?)
					{
						ivLoad2.visibility = View.GONE
						wbDrillDown2.visibility = View.VISIBLE
					}
				}
			}
		}
	}

	private fun setData()
	{
		ivCancel.setOnClickListener {
			dismiss()
		}

		tvTitle.text = queryBase.query
		ivCancel.setColorFilter(
			context.getParsedColor(R.color.chata_drawer_background_color_dark))
		loadWebView()
		presenter.getQueryDrillDown(value1, value2)
	}

	@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
	private fun loadWebView()
	{
		wbDrillDown1.run {
			settings.javaScriptEnabled = true
			clearCache(true)
			addJavascriptInterface(object
			{
				@JavascriptInterface
				fun boundMethod(content: String)
				{
					queryBase.run {
						when(displayType)
						{
							"bar", "column", "line", "pie" ->
							{
								val indexX = aXAxis.indexOf(content)
								if (indexX != -1)
								{
									value1 = aXDrillDown[indexX]
									presenter.getQueryDrillDown(value1)
								}
							}
							"heatmap" ->
							{
								val aValues = content.split("_")
								if (aValues.isNotEmpty())
								{
									val indexX = aXAxis.indexOf(aValues[0])
									if (indexX != -1)
									{
										value1 = aValues[0]
										value2 = aValues[1]
										presenter.getQueryDrillDown(value1, value2)
									}
								}
							}
						}
					}
				}
			},"Android")
			loadDataWithBaseURL(null, queryBase.contentHTML,"text/html","UTF-8", null)
			webViewClient = object: WebViewClient()
			{
				override fun onPageFinished(view: WebView?, url: String?)
				{
					ivLoad1.visibility = View.GONE
					wbDrillDown1.visibility = View.VISIBLE
				}
			}
		}
	}
}