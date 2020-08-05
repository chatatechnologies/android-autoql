package chata.can.chata_ai_api

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.*
import android.util.SparseBooleanArray
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import chata.can.chata_ai_api.model.DemoParameter
import chata.can.chata_ai_api.model.TypeInput
import java.util.*
import java.util.regex.Pattern

object CustomViews
{
	val mViews = linkedMapOf<String, SparseBooleanArray>()

	fun getSwitch(context: Context, value: String, idView: Int) = Switch(context).apply {
		layoutParams = LinearLayout.LayoutParams(-2, -2)
		gravity = Gravity.CENTER_HORIZONTAL
		isChecked = value == "true"
		id = idView
	}

	fun getEditText(context: Context, demoParam: DemoParameter) = EditText(context).apply {
		background = GradientDrawable().apply {
			shape = GradientDrawable.RECTANGLE
			setColor(ContextCompat.getColor(context, R.color.white))
			cornerRadius = 15f
			setStroke(3, (ContextCompat.getColor(context, R.color.borderEditText)))
		}
		layoutParams = LinearLayout.LayoutParams(-1, -2)
		(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
		gravity = Gravity.CENTER_HORIZONTAL
		id = demoParam.idView
		if (demoParam.value.isNotEmpty() &&
			demoParam.value != "true" && demoParam.value != "false")
		{
			setText(demoParam.value)
		}
		if (demoParam.hint.isNotEmpty())
		{
			hint = demoParam.hint
			setLines(1)
			setSingleLine()
			imeOptions = EditorInfo.IME_ACTION_DONE
		}

		when(demoParam.typeInput)
		{
			TypeInput.INTEGER ->
			{
				inputType = InputType.TYPE_CLASS_NUMBER
			}
			TypeInput.EMAIL ->
			{
				inputType = InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS).or(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
			}
			TypeInput.PASSWORD ->
			{
				inputType = InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_PASSWORD)
			}
			else ->
			{
				inputType = InputType.TYPE_CLASS_TEXT
				typeface = Typeface.MONOSPACE
			}
		}
	}

	fun getButton(context: Context, demoParam: DemoParameter, onClickListener: View.OnClickListener) =
		TextView(context).apply {
		setBackgroundColor(ContextCompat.getColor(context, R.color.colorButton))
		layoutParams = LinearLayout.LayoutParams(-1, 90)
		(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
		gravity = Gravity.CENTER
		setTextColor(ContextCompat.getColor(context, R.color.textButton))
		id = demoParam.idView
		if (id != 0)
		{
			setOnClickListener(onClickListener)
		}
		text = demoParam.label
	}


	fun getSegment(context: Context, demoParam: DemoParameter, onClickListener: View.OnClickListener)
		: LinearLayout
	{
		val subView = LinearLayout(context)
		val sizeOptions = demoParam.options.size
		if (sizeOptions > 0)
		{
			with(subView)
			{
				layoutParams = LinearLayout.LayoutParams(-1, -2, sizeOptions.toFloat())
				(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
				orientation = LinearLayout.HORIZONTAL

				for (iterator in 0 until sizeOptions)
				{
					val option = demoParam.options[iterator]
					val tv = TextView(context)
					tv.id = option.idView
					tv.setOnClickListener(onClickListener)
					tv.layoutParams = LinearLayout.LayoutParams(0, 90).apply {
						weight = 1f
					}
					tv.gravity = Gravity.CENTER
					tv.text = option.text
					tv.tag = demoParam.label

					mViews[demoParam.label]?.put(tv.id, option.isActive) ?: run {
						val newSparse = SparseBooleanArray()
						newSparse.put(tv.id, option.isActive)
						mViews.put(demoParam.label, newSparse)
					}
					this.addView(tv)
				}
			}
		}
		return subView
	}

	//region new color
	fun setNewColor(context: Context, valueColor: String, indexColor: Int) = EditText(context).apply {
		try
		{
			setBackgroundColor(Color.parseColor(valueColor))
		}
		finally
		{
			layoutParams = LinearLayout.LayoutParams(-1, 120)
			(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
			gravity = Gravity.CENTER
			setTextColor(Color.WHITE)
			setText(valueColor)
			tag = indexColor

			filters = aFilters
			inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
		}
	}
	//endregion
	//region FILTERS
	private val inputFilterText = object: InputFilter {
		override fun filter(
			source: CharSequence?,
			start: Int,
			end: Int,
			dest: Spanned?,
			dstart: Int,
			dend: Int
		): CharSequence {
			val pattern = Pattern.compile("^\\p{XDigit}+$")
			val sb = StringBuilder()
			for (i in start until end)
			{
				val chart = source?.elementAt(i) ?: ' '
				if (chart == '#')
				{
					sb.append(chart)
					continue
				}

				if (!Character.isLetterOrDigit(chart) && Character.isSpaceChar(chart))
				{
					return ""
				}

				val matcher = pattern.matcher(java.lang.String.valueOf(chart))
				if (!matcher.matches())
				{
					return ""
				}

				sb.append(chart)
			}
			return sb.toString().toUpperCase(Locale.US)
		}
	}
	private val aFilters = arrayOf(inputFilterText, InputFilter.LengthFilter(7))
	//endregion

	fun getColor(context: Context, demoParam: DemoParameter, addColor: (String) -> Unit): View {
		//Has several colors
		if (demoParam.colors.size > 0)
		{
			val subView = LinearLayout(context)
			subView.layoutParams = LinearLayout.LayoutParams(-1, -2)
			subView.orientation = LinearLayout.VERTICAL

			for (indexColor in demoParam.colors.indices)
			{
				val color = demoParam.colors[indexColor]
				subView.addView(EditText(context).apply {
					val valueColor = color.value
					try
					{
						setBackgroundColor(Color.parseColor(valueColor))
					}
					finally
					{
						layoutParams = LinearLayout.LayoutParams(-1, 120)
						(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
						gravity = Gravity.CENTER
						setTextColor(Color.WHITE)
						addColor(valueColor)
						setText(valueColor)
						tag = indexColor

						filters = aFilters
						inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
					}
				})
			}

			subView.id = demoParam.idView
			return subView
		}
		//Has one color
		else
		{
			return EditText(context).apply {
				val valueColor = demoParam.value
				try
				{
					setBackgroundColor(Color.parseColor(valueColor))
				}
				finally
				{
					layoutParams = LinearLayout.LayoutParams(-1, 120)
					(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
					gravity = Gravity.CENTER
					setTextColor(Color.WHITE)
					id = demoParam.idView

					filters = aFilters
					inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS


					setText(valueColor)
				}
			}
		}
	}
}