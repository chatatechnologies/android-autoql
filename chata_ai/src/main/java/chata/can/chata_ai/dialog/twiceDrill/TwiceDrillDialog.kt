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
import androidx.core.view.isVisible
import chata.can.chata_ai.R
import chata.can.chata_ai.dialog.BaseDialog
import chata.can.chata_ai.dialog.DrillDownContract
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder

class TwiceDrillDialog(
	context: Context,
	private val queryBase: QueryBase,
	private var value1: String,
	private var value2: String = ""
): BaseDialog(context, isFull = false,
	rootView = TwiceDrill.getDesign(context)
), View.OnClickListener, DrillDownContract
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
				ivHide.background =  DrawableBuilder.setGradientDrawable(
					pDrawerBackgroundColor, 18f,1, pDrawerTextColorPrimary)
			}
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	override fun loadDrillDown(queryBase: QueryBase)
	{
		updateView(ivLoad2, View.VISIBLE)
		updateView(wbDrillDown2, View.GONE)

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
						updateView(wbDrillDown2, View.VISIBLE)
						updateView(ivLoad2, View.GONE)
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
					val tIds = if (rlDrillDown1.isVisible)
					{
						updateView(rlDrillDown1, View.GONE)
						val pCenter = Pair(ConstraintSet.PARENT_ID, guideHide.id)
						val pBottom = Pair(guideHide.id, guide1.id)
						Triple(pCenter, pBottom, 180f)
					}
					else
					{
						updateView(rlDrillDown1, View.VISIBLE)
						val pCenter = Pair(guide.id, guide1.id)
						val pBottom = Pair(guide1.id, ConstraintSet.PARENT_ID)
						Triple(pCenter, pBottom, 0f)
					}
					ivHide.rotation = tIds.third
					ConstraintSet().run {
						clone(layout)
						val pCenter = tIds.first
						val pBottom = tIds.second
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
			ivCancel.getParsedColor(R.color.chata_drawer_background_color_dark))
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
				fun drillDown(content: String)
				{
					queryBase.run {
						when(displayType)
						{
							"TypeEnum.LINE" ->
							{
								if (isTri)
								{
									drillForTri(content, queryBase)
								}
								else
								{
									drillForBi(content, queryBase)
								}
							}
							"TypeEnum.BAR", "TypeEnum.COLUMN", "TypeEnum.PIE" ->
							{
								drillForBi(content, queryBase)
							}
							"TypeEnum.HEATMAP", "TypeEnum.BUBBLE", "TypeEnum.STACKED_BAR", "TypeEnum.STACKED_COLUMN", "stacked_line" ->
							{
								drillForTri(content, queryBase)
							}
						}
					}
				}

				private fun drillForBi(content: String, queryBase: QueryBase)
				{
					queryBase.run {
						val indexX = aXAxis.indexOf(content)
						if (indexX != -1)
						{
							value1 = aXDrillDown[indexX]

							updateView(ivLoad2, View.VISIBLE)
							updateView(wbDrillDown2, View.GONE)

							presenter.getQueryDrillDown(value1)
						}
					}
				}

				private fun drillForTri(content: String, queryBase: QueryBase)
				{
					queryBase.run {
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
			},"Android")
			loadDataWithBaseURL(null, queryBase.contentHTML,"text/html","UTF-8", null)
			webViewClient = object: WebViewClient()
			{
				override fun onPageFinished(view: WebView?, url: String?)
				{
					updateView(wbDrillDown1, View.VISIBLE)
					updateView(ivLoad1, View.GONE)
				}
			}
		}
	}

	/**
	 * update visibility view for constraint layout
	 * @param view
	 * @param status (GONE, VISIBLE, INVISIBLE)
	 */
	private fun updateView(view: View, status: Int)
	{
		Handler(Looper.getMainLooper()).postDelayed({
			view.visibility = status
		}, 200)
	}
}