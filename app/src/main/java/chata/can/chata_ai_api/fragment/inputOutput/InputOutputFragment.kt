package chata.can.chata_ai_api.fragment.inputOutput

import android.annotation.SuppressLint
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import chata.can.chata_ai.activity.dataMessenger.adapter.AutoCompleteAdapter
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs
import chata.can.chata_ai_api.BaseFragment
import chata.can.chata_ai_api.BuildConfig
import chata.can.chata_ai_api.R

class InputOutputFragment: BaseFragment(), InputOutputContract
{
	companion object {
		const val nameFragment = "QueryInput/QueryOutput"
		fun newInstance() = InputOutputFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_input_output)
		}
	}

	private lateinit var llQuery: View
	private lateinit var ivChata: ImageView
	private lateinit var etQuery: AutoCompleteTextView
	private lateinit var rvLoad: View
	private lateinit var tvContent: TextView
	private lateinit var wbOutput: WebView
	private lateinit var vBlank: View

	private lateinit var adapterAutoComplete: AutoCompleteAdapter

	private lateinit var presenter: InputOutputPresenter

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		activity?.let { presenter = InputOutputPresenter(it, this) }
		initViews()
		if (BuildConfig.DEBUG)
		{
//			etQuery.setText("all sales")
			etQuery.setText("total revenue by area 2019")
//			etQuery.setText("Bottom two customers")
		}
	}

	override fun initListener()
	{
		etQuery.addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					if (SinglentonDrawer.mIsEnableAutocomplete)
					{
						presenter.getAutocomplete(string)
					}
				}
			}
		})
	}

	override fun initViews(view: View)
	{
		with(view){
			llQuery = findViewById(R.id.llQuery)
			ivChata = findViewById(R.id.ivChata)
			etQuery = findViewById(R.id.etQuery)
			rvLoad = findViewById(R.id.rvLoad)
			tvContent = findViewById(R.id.tvContent)
			wbOutput = findViewById(R.id.wbOutput)
			vBlank = findViewById(R.id.vBlank)
		}
	}

	override fun setColors()
	{
		activity?.run {
			ivChata.setColorFilter(getParsedColor(R.color.blue_chata_circle))

			val white = getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
			val gray = getParsedColor(ThemeColor.currentColor.drawerColorPrimary)
			llQuery.background = DrawableBuilder.setGradientDrawable(white,64f,1, gray)

			adapterAutoComplete = AutoCompleteAdapter(this, R.layout.row_spinner)

			etQuery.run {
				threshold = 1
				setAdapter(adapterAutoComplete)

				setTextColor(getParsedColor(ThemeColor.currentColor.drawerColorPrimary))
				setHintTextColor(getParsedColor(ThemeColor.currentColor.drawerHoverColor))

				val displayMetrics = DisplayMetrics()
				ScreenData.defaultDisplay.getMetrics(displayMetrics)
				val width = displayMetrics.widthPixels
				dropDownWidth = width

				onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
					parent?.let {
						it.adapter?.let { adapter ->
							val text = adapter.getItem(position).toString()
							etQuery.setText(text)
							setRequestQuery()
						}
					}
				}

				setOnEditorActionListener { _, _, _ ->
					setRequestQuery()
					false
				}
			}
		}
	}

	override fun setDataAutocomplete(aData: ArrayList<String>)
	{
		adapterAutoComplete.clear()
		if (aData.isNotEmpty())
		{
			adapterAutoComplete.addAll(aData)

			val size = Point()
			ScreenData.defaultDisplay.getSize(size)
			val maxHeight = size.y * 0.35

			val count = adapterAutoComplete.count
			val height = ScreenData.densityByDP * (if (count < 2) 2 else adapterAutoComplete.count) * 40

			etQuery.dropDownHeight =
				if (height < maxHeight)
					height.toInt()
				else
					maxHeight.toInt()
		}
		adapterAutoComplete.notifyDataSetChanged()
	}

	private fun setRequestQuery()
	{
		val query = etQuery.text.toString()
		if (query.isNotEmpty())
		{
			hideKeyboard()
			etQuery.setText("")
			showLoading()
			if (SinglentonDrawer.mIsEnableQuery)
			{
				presenter.getSafety(query)
			}
			else
			{
				presenter.getQuery(query)
			}
		}
	}

	override fun showText(text: String)
	{
		rvLoad.visibility = View.GONE
		tvContent.visibility = View.VISIBLE
		tvContent.text = text
	}

	@SuppressLint("SetJavaScriptEnabled")
	override fun loadDrillDown(queryBase: QueryBase)
	{
		wbOutput.run {
			settings.javaScriptEnabled = true
			clearCache(true)
			loadDataWithBaseURL(null, queryBase.contentHTML,"text/html","UTF-8", null)
			webViewClient = object: WebViewClient()
			{
				override fun onPageFinished(view: WebView?, url: String?)
				{
					rvLoad.visibility = View.GONE
					wbOutput.visibility = View.VISIBLE
				}
			}
		}
	}

	private fun showLoading()
	{
		rvLoad.visibility = View.VISIBLE
		tvContent.visibility = View.GONE
		wbOutput.visibility = View.GONE
		vBlank.visibility = View.GONE
	}

	private fun initViews()
	{
		rvLoad.visibility = View.GONE
		tvContent.visibility = View.GONE
		wbOutput.visibility = View.GONE
	}
}