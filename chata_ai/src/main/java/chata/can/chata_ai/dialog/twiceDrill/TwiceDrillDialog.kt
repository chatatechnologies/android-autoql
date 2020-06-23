package chata.can.chata_ai.dialog.twiceDrill

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
import chata.can.chata_ai.pojo.chat.QueryBase

class TwiceDrillDialog(
	context: Context,
	private val queryBase: QueryBase
): BaseDialog(context, R.layout.dialog_twice_drill_down), DrillDownContract
{
	private lateinit var ivCancel: ImageView
	private lateinit var tvTitle: TextView
	private lateinit var ivLoad1: View
	private lateinit var wbDrillDown1 : WebView

	private val presenter = TwiceDrillPresenter(queryBase)

	override fun onCreateView()
	{
		super.onCreateView()
		setData()
	}

	override fun setViews()
	{
		ivCancel = findViewById(R.id.ivCancel)
		tvTitle = findViewById(R.id.tvTitle)
		ivLoad1 = findViewById(R.id.ivLoad1)
		wbDrillDown1 = findViewById(R.id.wbDrillDown1)
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
		ivCancel.setOnClickListener {
			dismiss()
		}

		tvTitle.text = queryBase.query
		loadWebView()
		presenter.getQueryDrillDown()
	}

	@SuppressLint("SetJavaScriptEnabled")
	private fun loadWebView()
	{
		wbDrillDown1.run {
			settings.javaScriptEnabled = true
			clearCache(true)
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