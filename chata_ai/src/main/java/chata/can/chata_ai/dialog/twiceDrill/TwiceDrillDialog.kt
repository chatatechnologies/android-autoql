package chata.can.chata_ai.dialog.twiceDrill

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
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
): BaseDialog(context, R.layout.dialog_twice_drill_down, false), View.OnClickListener, DrillDownContract
{
	private lateinit var rlParent: View
	private lateinit var ivCancel: ImageView
	private lateinit var tvTitle: TextView
	private lateinit var vBorder: View
	private lateinit var layout: ConstraintLayout
	private lateinit var guide: Guideline
	private lateinit var guide1: Guideline
	private lateinit var guideHide: Guideline
	private lateinit var ivLoad1: View
	private lateinit var rlHide: View
	private lateinit var ivHide: ImageView
	private lateinit var ivLoad2: View
	private lateinit var rlDrillDown1: View
	private lateinit var wbDrillDown1 : WebView
	private lateinit var rlDrillDown2: View
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
		vBorder = findViewById(R.id.vBorder)
		layout = findViewById(R.id.layout)
		guide = findViewById(R.id.guide)
		guide1 = findViewById(R.id.guide1)
		guideHide = findViewById(R.id.guideHide)
		ivLoad1 = findViewById(R.id.ivLoad1)
		rlHide = findViewById(R.id.rlHide)
		ivHide = findViewById(R.id.ivHide)
		ivLoad2 = findViewById(R.id.ivLoad2)
		rlDrillDown1 = findViewById(R.id.rlDrillDown1)
		wbDrillDown1 = findViewById(R.id.wbDrillDown1)
		rlDrillDown2 = findViewById(R.id.rlDrillDown2)
		wbDrillDown2 = findViewById(R.id.wbDrillDown2)
	}

	override fun setColors()
	{
		ThemeColor.currentColor.run {
			context.let {
				tvTitle.setTextColor(pDrawerTextColorPrimary)
				rlParent.setBackgroundColor(pDrawerBackgroundColor)
				ivCancel.setColorFilter(pDrawerTextColorPrimary)
				vBorder.setBackgroundColor(pDrawerBorderColor)
				ivLoad1.setBackgroundColor(pDrawerBackgroundColor)
				ivLoad2.setBackgroundColor(pDrawerBackgroundColor)
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
						wbDrillDown2.visibility = View.VISIBLE
						Handler(Looper.getMainLooper()).postDelayed({
							ivLoad2.visibility = View.GONE
						}, 200)
					}
				}
			}
		}
	}

	override fun onClick(view: View?)
	{
		view?.let {
			when(it.id)
			{
				R.id.ivCancel -> dismiss()
				R.id.ivHide ->
				{
					val pIds = if (rlDrillDown1.visibility == View.VISIBLE)
					{
						rlDrillDown1.visibility = View.GONE
						val pCenter = Pair(ConstraintSet.PARENT_ID, guideHide.id)
						val pBottom = Pair(guideHide.id, guide1.id)
						Pair(pCenter, pBottom)
					}
					else
					{
						rlDrillDown1.visibility = View.VISIBLE
						val pCenter = Pair(guide.id, guide1.id)
						val pBottom = Pair(guide1.id, ConstraintSet.PARENT_ID)
						Pair(pCenter, pBottom)
					}
					ConstraintSet().run {
						clone(layout)
						val pCenter = pIds.first
						val pBottom = pIds.second
						connect(rlHide.id, ConstraintSet.TOP, pCenter.first, ConstraintSet.TOP)
						connect(rlHide.id, ConstraintSet.BOTTOM, pCenter.second, ConstraintSet.BOTTOM)

						connect(rlDrillDown2.id, ConstraintSet.TOP, pBottom.first, ConstraintSet.TOP)
						connect(rlDrillDown2.id, ConstraintSet.BOTTOM, pBottom.second, ConstraintSet.BOTTOM)
						applyTo(layout)
					}
				}
			}
		}
	}

	private fun setData()
	{
		ivCancel.setOnClickListener(this)
		ivHide.setOnClickListener(this)

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
							"heatmap", "stacked_bar", "stacked_column" ->
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
					wbDrillDown1.visibility = View.VISIBLE
					Handler(Looper.getMainLooper()).postDelayed({
						ivLoad1.visibility = View.GONE
					}, 200)
				}
			}
		}
	}
}