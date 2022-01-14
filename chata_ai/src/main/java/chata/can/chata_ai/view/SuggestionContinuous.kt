package chata.can.chata_ai.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.textViewSpinner.model.Suggestion
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.JustifyContent

class SuggestionContinuous: RelativeLayout// FlexboxLayout
{
	private var flexboxLayout: FlexboxLayout = FlexboxLayout(context).apply {
		setBackgroundColor(Color.MAGENTA)
		layoutParams = LayoutParams(-1, dpToPx(40f))

		flexWrap = FlexWrap.WRAP
		justifyContent = JustifyContent.CENTER
	}
	private lateinit var spSelect: Spinner

	private var textDisplayed = ""
	private var aText: ArrayList<Suggestion> = ArrayList()

	constructor(context: Context): super(context)

	constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

	@SuppressLint("CustomViewStyleable")
	constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)
	{
		addView(flexboxLayout)

		paddingAll(top = 4f, bottom = 4f)
	}

	val text: String
		get() = textDisplayed

	fun setText(aText: ArrayList<Suggestion> ?= null) {
		aText?.let { this.aText = it }
		//region build content for suggestion
		this.aText.let {
			it.forEach { suggestion ->
				addView(getItem(context, suggestion))
			}
		}
		//endregion
	}

	fun setWindowManager() {

	}

	private fun getItem(context: Context, suggestion: Suggestion) = TextView(context).apply {
		layoutParams = LayoutParams(-2, -2)
		setTextColor(context.getParsedColor(R.color.blue_chata_circle))
		paddingAll(12f, 6f, 12f, 6f)
		textDisplayed += suggestion.text
		text = suggestion.text
		suggestion.aSuggestion?.let { aSuggestion ->
			background = getBackgroundColor()
			setOnClickListener {
				callOptionsSuggestion(suggestion, aSuggestion)
			}
		} ?: run {
			background = null
		}
	}

	private fun callOptionsSuggestion(suggestion: Suggestion, aData: ArrayList<Pair<String, String>>?) {

	}

	private fun getBackgroundColor() = ThemeColor.currentColor.run {
		DrawableBuilder.setGradientDrawable(pDrawerBackgroundColor, 18f, 3, pDrawerBorderColor)
	}
}