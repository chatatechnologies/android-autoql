package chata.can.chata_ai_api

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.util.SparseBooleanArray
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import chata.can.chata_ai.extension.*
import chata.can.chata_ai_api.model.DemoParameter
import chata.can.chata_ai_api.model.TypeInput
import java.util.*
import java.util.regex.Pattern


object CustomViews
{
	val mViews = linkedMapOf<String, SparseBooleanArray>()

	fun getSwitch(context: Context, value: String, idView: Int) = SwitchCompat(context).apply {
		layoutParams = getLinearLayoutParams(-2, -2)
		gravity = Gravity.CENTER_HORIZONTAL
		isChecked = value == "true"
		id = idView
	}


	fun getEditText(context: Context, demoParam: DemoParameter) =
		context.run {
			EditText(context).apply {
				background = GradientDrawable().apply {
					shape = GradientDrawable.RECTANGLE
					setColor(getParsedColor(R.color.white))
					cornerRadius = 15f
					setStroke(3, getParsedColor(R.color.borderEditText))
				}
				layoutParams = getLinearLayoutParams(-1, -2)
				margin(20.5f, 10.5f, 20.5f, 10.5f)
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
					TypeInput.INTEGER -> {
						inputType = InputType.TYPE_CLASS_NUMBER
					}
					TypeInput.EMAIL -> {
						inputType = InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS).or(
							InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
						)
					}
					TypeInput.PASSWORD -> {
						@RequiresApi(Build.VERSION_CODES.O)
						importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
						inputType = InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_PASSWORD).or(
							InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
						)
					}
					else ->
					{
						inputType = InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
						typeface = Typeface.MONOSPACE
					}
				}
			}
		}

	fun getButton(context: Context, demoParam: DemoParameter, onClickListener: View.OnClickListener) =
		context.run {
			TextView(context).apply {
				background = CustomColor.getBackgroundDrawable(
					Color.WHITE,
					ColorDrawable(getParsedColor(R.color.colorButton)))

				layoutParams = getLinearLayoutParams(-1, 90)
				margin(20.5f, 10.5f, 20.5f, 10.5f)
				gravity = Gravity.CENTER
				setTextColor(getParsedColor(R.color.textButton))
				id = demoParam.idView
				if (id != 0)
				{
					setOnClickListener(onClickListener)
				}
				text = demoParam.label
			}
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
				layoutParams = getLinearLayoutParams(-1, -2).apply {
					if (sizeOptions > 2)
						weight = sizeOptions.toFloat()
					else
						setGravity(Gravity.CENTER)
				}
				orientation = LinearLayout.HORIZONTAL
				margin(20.5f, 10.5f, 20.5f, 10.5f)
				for (iterator in 0 until sizeOptions)
				{
					val option = demoParam.options[iterator]
					val llOption = LinearLayout(context).apply {
						layoutParams =
							if (sizeOptions > 2 || option.idResource != 0)
							{
								getLinearLayoutParams(0, 99).apply {
									setGravity(Gravity.CENTER)
									weight = 1f
								}
							}
							else
							{
								getLinearLayoutParams(-2, 99).apply {
									setGravity(Gravity.CENTER)
								}
							}
						if (option.idResource != 0)
						{
							addView(
								ImageView(context).apply {
									layoutParams = getLinearLayoutParams(56, 56)
									margin(28f, 0f, 16f, 0f)
									setImageResource(option.idResource)
								}
							)
							tag = "child"
						}
						val tv = TextView(context).apply {
							layoutParams = getLinearLayoutParams(
								if (option.idResource != 0) -2 else -1, -1
							)
							gravity = Gravity.CENTER
							id = option.idView
							setOnClickListener(onClickListener)
							text = option.text
							tag = demoParam.label
						}
						if (sizeOptions < 3)
						{
							if (option.idResource == 0)
							{
								tv.setPadding(32, 0, 32, 0)
							}
							else
							{
								tv.margin(8f, end = 16f)
							}
						}
						mViews[demoParam.label]?.put(tv.id, option.isActive) ?: run {
							val newSparse = SparseBooleanArray()
							newSparse.put(tv.id, option.isActive)
							mViews.put(demoParam.label, newSparse)
						}
						addView(tv)
					}
					addView(llOption)
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
			layoutParams = getLinearLayoutParams(-1, 120)
			margin(20.5f, 10.5f, 20.5f, 10.5f)
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
			subView.layoutParams = getLinearLayoutParams(-1, -2)
			subView.orientation = LinearLayout.VERTICAL

			for (indexColor in demoParam.colors.indices)
			{
				val color = demoParam.colors[indexColor]
				subView.addView(EditText(context).apply {
					val valueColor = color.value
					try {
						val pColor = valueColor.getContrast()
						setBackgroundColor(pColor.first)
						setTextColor(pColor.second)
					} finally {
						layoutParams = getLinearLayoutParams(-1, 120)
						margin(20.5f, 10.5f, 20.5f, 10.5f)
						gravity = Gravity.CENTER
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
					val pColor = valueColor.getContrast()
					setBackgroundColor(pColor.first)
					setTextColor(pColor.second)
				}
				finally
				{
					layoutParams = getLinearLayoutParams(-1, 120)
					margin(20.5f, 10.5f, 20.5f, 10.5f)
					gravity = Gravity.CENTER
					id = demoParam.idView

					filters = aFilters
					inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS


					setText(valueColor)
				}
			}
		}
	}
}