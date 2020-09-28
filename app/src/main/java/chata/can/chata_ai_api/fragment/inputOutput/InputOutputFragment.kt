package chata.can.chata_ai_api.fragment.inputOutput

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import chata.can.chata_ai.activity.dataMessenger.adapter.AutoCompleteAdapter
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.pojo.ScreenData
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.chat.FullSuggestionQuery
import chata.can.chata_ai.pojo.chat.QueryBase
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs
import chata.can.chata_ai.view.textViewSpinner.model.SpinnerTextView
import chata.can.chata_ai.view.textViewSpinner.model.Suggestion
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
	private lateinit var llSuggestion: LinearLayout
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
//			etQuery.setText("total revenue by area 2019")
			etQuery.setText("Totel salas")
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
			llSuggestion = findViewById(R.id.llSuggestion)
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

	override fun showSuggestion(aItems: ArrayList<String>)
	{
		llSuggestion.visibility = View.VISIBLE
		rvLoad.visibility = View.GONE
		llSuggestion.removeAllViews()
		for (index in 0 until aItems.size)
		{
			val suggestion = aItems[index]
			val tv = buildSuggestionView(llSuggestion.context, suggestion)
			llSuggestion.addView(tv)
		}
	}

	override fun showFullSuggestion(fullSuggestion: FullSuggestionQuery)
	{
		llSuggestion.visibility = View.VISIBLE
		rvLoad.visibility = View.GONE
		llSuggestion.removeAllViews()
		llSuggestion.addView(buildFullSuggestionView(llSuggestion.context, fullSuggestion.aSuggestion))
	}

	private fun buildFullSuggestionView(
		context: Context,
		aSuggestion: ArrayList<Suggestion>
	): LinearLayout
	{
		return LinearLayout(context).apply {
			layoutParams = LinearLayout.LayoutParams(-1, -2)
			margin(top = 12f, start = 12f, end = 12f)
			paddingAll(5f)
			orientation = LinearLayout.VERTICAL
			val tvContent = TextView(context).apply {
				layoutParams = LinearLayout.LayoutParams(-2, -2)
				margin(5f,5f,5f,5f)
				text = getString(R.string.msg_full_suggestion)
			}
			//region stvContent
			val stvContent = SpinnerTextView(context).apply {
				layoutParams = LinearLayout.LayoutParams(-1, dpToPx(32f))
				paddingAll(5f)
			}
			stvContent.setText(aSuggestion)
			//endregion
			val rlRunQuery = RelativeLayout(context).apply {
				layoutParams = LinearLayout.LayoutParams(-1, dpToPx(32f))
				val tvRunQuery = TextView(context).apply {
					val params = RelativeLayout.LayoutParams(-2, -2)
					params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
					layoutParams = params
					id = R.id.tvRunQuery
					setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
					text = getString(R.string.run_query)
				}
				addView(tvRunQuery)
				val ivRunQuery = ImageView(context).apply {
					val params = RelativeLayout.LayoutParams(dpToPx(20f), dpToPx(20f))
					params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
					params.addRule(RelativeLayout.START_OF, R.id.tvRunQuery)
					layoutParams = params
					setImageResource(R.drawable.ic_play)
				}
				addView(ivRunQuery)
				backgroundGrayWhite()
				setOnClickListener {
					val query = stvContent.text
					if (query.isNotEmpty())
					{
						showLoading()
						presenter.getQuery(query)
					}
				}
			}

			addView(tvContent)
			addView(stvContent)
			addView(rlRunQuery)
			backgroundGrayWhite()
		}
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
		llSuggestion.visibility = View.GONE
		vBlank.visibility = View.GONE
	}

	private fun initViews()
	{
		rvLoad.visibility = View.GONE
		tvContent.visibility = View.GONE
		wbOutput.visibility = View.GONE
		llSuggestion.visibility = View.GONE
	}

	private fun buildSuggestionView(context: Context, content: String) = TextView(context).apply {
		backgroundGrayWhite()
		layoutParams = LinearLayout.LayoutParams(-1, -2)
		margin(5f, 5f, 5f)
		gravity = Gravity.CENTER_HORIZONTAL
		setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
		setPadding(15,15,15,15)
		text = content
		setOnClickListener {
			showLoading()
			presenter.getQuery(content)
		}
	}
}