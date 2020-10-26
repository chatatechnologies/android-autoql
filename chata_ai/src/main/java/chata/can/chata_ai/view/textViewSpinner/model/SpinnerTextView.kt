package chata.can.chata_ai.view.textViewSpinner.model

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.widget.*
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.view.textViewSpinner.ClickableSpan
import chata.can.chata_ai.view.textViewSpinner.TermAdapter

class SpinnerTextView: RelativeLayout
{
	private fun init()
	{
		spSelect = Spinner(context).apply {
			background = null
			layoutParams = LayoutParams(-1, -2)
		}

		tvContent = TextView(context).apply {
			val backgroundColor = context.getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
			setBackgroundColor(backgroundColor)
			gravity = Gravity.CENTER
			highlightColor = Color.TRANSPARENT
			layoutParams = LayoutParams(-1, -2)
			setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
			viewTreeObserver?.addOnGlobalLayoutListener(object: OnGlobalLayoutListener
			{
				override fun onGlobalLayout()
				{
					tvContent?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
					val height = tvContent?.measuredHeight ?: 0
					spSelect?.dropDownVerticalOffset = height
				}
			})
		}

		addView(spSelect)
		addView(tvContent)
	}

	private fun getDataSuggestion(aData: ArrayList<String>): ArrayAdapter<String>
	{
		return TermAdapter(context, aData)
	}

	private fun callSpinnerClick(suggestion: Suggestion, aData: ArrayList<String>?)
	{
		aData?.let {
			if (lastData != aData)
			{
				lastData = aData
				spSelect?.adapter = getDataSuggestion(aData)
			}
			spSelect?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener
			{
				override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
				{
					if (suggestion.position != position)
					{
						if (position == aData.size - 1)
						{
							this@SpinnerTextView.aData.run {
								remove(suggestion)
								setText(this)
							}
						}
						else
						{
							parent?.getItemAtPosition(position)?.let {
								if (it is String)
								{
									val aIt = it.split(" (")
									val currentText = tvContent?.text ?: ""
									val currentSection = aIt[0]
									val newStart = suggestion.start
									val newEnd = suggestion.end
									val beforeSection = currentText.substring(newStart, newEnd)

									if (beforeSection != currentSection)
									{
										val newText = currentText.toString().replace(beforeSection, currentSection)
										suggestion.text = currentSection
										suggestion.position = position
										updateIndex(newText)
										setText()
									}
								}
							}
						}

					}
				}

				override fun onNothingSelected(p0: AdapterView<*>?) {}
			}
			spSelect?.performClick()
		}
	}

	val text: String
		get() = tvContent?.text.toString()

	fun setText(aData: ArrayList<Suggestion> ?= null)
	{
		aData?.let {
			this.aData = aData
		}

		this.aData.let { itData ->
			val text = itData.joinTo(StringBuilder(""), separator = "") {
				" ${it.text}"
			}.trim()
			tvContent?.run {
				val span = SpannableString(text)
				for (suggestion in this@SpinnerTextView.aData)
				{
					if (suggestion.aSuggestion != null)
					{
						span.setSpan(ClickableSpan(this, suggestion) {
							callSpinnerClick(suggestion, it.aSuggestion)
						}, suggestion.start, suggestion.end, 0)
					}
				}
				setText(span)
				movementMethod = LinkMovementMethod.getInstance()
			}
		}
	}

	private fun updateIndex(newText: String)
	{
		for (suggestion in aData)
		{
			suggestion.run {
				start = newText.indexOf(text)
				end = start + text.length
			}
		}
	}

	fun setWindowManager(_windowManager: WindowManager)
	{
		this.windowManager = _windowManager

		val displayMetrics = DisplayMetrics()

		val defaultDisplay = this.windowManager?.defaultDisplay
		defaultDisplay?.getMetrics(displayMetrics)
		val width = displayMetrics.widthPixels
		spSelect?.dropDownWidth = width
	}

	constructor(context: Context): super(context)
	{
		init()
	}

	constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

	constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)
	{
		init()
	}

	private var tvContent: TextView?= null
	private var spSelect: Spinner?= null

	private var windowManager: WindowManager?= null

	private lateinit var aData: ArrayList<Suggestion>
	private var lastData: ArrayList<String> ?= null
}