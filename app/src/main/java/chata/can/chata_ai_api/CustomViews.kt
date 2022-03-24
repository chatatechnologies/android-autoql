package chata.can.chata_ai_api

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.text.TextUtils
import android.util.SparseBooleanArray
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import chata.can.chata_ai.extension.*
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.view.SwitchDM
import chata.can.chata_ai.view.container.LayoutParams
import chata.can.chata_ai.view.container.LayoutParams.getLinearLayoutParams
import chata.can.chata_ai_api.model.DemoParameter
import chata.can.chata_ai_api.model.TypeInput
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import java.util.regex.Pattern

object CustomViews
{
	val mViews = linkedMapOf<String, SparseBooleanArray>()

	fun getSwitchQA(context: Context) = SwitchDM(context).apply {
		layoutParams = getLinearLayoutParams(-2, -2)
		gravity = Gravity.CENTER_HORIZONTAL
		id = R.id.swQA
	}

	fun getSwitch(context: Context, value: String, idView: Int) = SwitchCompat(context).apply {
		layoutParams = getLinearLayoutParams(-2, -2)
		gravity = Gravity.CENTER_HORIZONTAL
		isChecked = value == "true"
		id = idView
	}

	/** MATERIAL **/
	fun getTextInput(context: Context, demoParam: DemoParameter): TextInputLayout {
		return TextInputLayout(context).apply {
			val whiteColor = context.getParsedColor(R.color.white)
			val blueColor = context.getParsedColor(R.color.blue_chata_circle)
			val redColor = context.getParsedColor(R.color.red_notification)

			layoutParams = getLinearLayoutParams(-1, -2)
			hint = demoParam.hint
			boxStrokeColor = blueColor
			boxBackgroundColor = whiteColor
			boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
			hintTextColor = ColorStateList.valueOf(blueColor)
			setBoxCornerRadii(5f, 5f, 5f, 5f)
			margin(12f, 10.5f, 12f, 10.5f)
			if (demoParam.helperText.isNotEmpty())
				helperText = demoParam.helperText
			setHelperTextColor(ColorStateList.valueOf(redColor))
			addView(TextInputEditText(this.context).apply {
				id = demoParam.idView

				if (demoParam.hint.isNotEmpty())
				{
					setLines(1)
					setSingleLine()
					imeOptions = EditorInfo.IME_ACTION_DONE
				}

				if (demoParam.value.isNotEmpty() &&
					demoParam.value != "true" && demoParam.value != "false")
				{
					setText(demoParam.value)
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
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
						{
							importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
						}
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
			})
		}
	}

	fun getEditText(context: Context, demoParam: DemoParameter) =
		context.run {
			EditText(context).apply {
				visibility = if (demoParam.isVisible) View.VISIBLE else View.GONE
				background = GradientDrawable().apply {
					shape = GradientDrawable.RECTANGLE
					setColor(getParsedColor(R.color.white))
					cornerRadius = 15f
					setStroke(3, getParsedColor(R.color.borderEditText))
				}
				layoutParams = getLinearLayoutParams(-1, -2)
				margin(12f, 10.5f, 12f, 10.5f)
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
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
						{
							importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
						}
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
			val blue = context.getParsedColor(R.color.blue_chata_circle)
			val alphaColor = ColorUtils.setAlphaComponent(blue, (0.3 * 255).toInt())

			MaterialButton(context).apply {
				layoutParams = getLinearLayoutParams(-1, dpToPx(48f))
				margin(12f, 10.5f, 12f, 10.5f)
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
				{
					stateListAnimator = null
				}
				backgroundTintList = ColorStateList.valueOf(alphaColor)
				setTextColor(blue)
				strokeColor = ColorStateList.valueOf(blue)
				strokeWidth = 3
				id = demoParam.idView
				isAllCaps = false
				if (id != 0) setOnClickListener(onClickListener)
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
				margin(12f, 10.5f, 12f, 10.5f)
				for (iterator in 0 until sizeOptions)
				{
					val option = demoParam.options[iterator]
					val llOption = LinearLayout(context).apply {
						layoutParams =
							if (sizeOptions > 2 || option.idResource != 0)
							{
								getLinearLayoutParams(0, -2).apply {//FACTOR
									orientation = LinearLayout.HORIZONTAL
									setGravity(Gravity.CENTER)
									weight = 1f
								}
							}
							else
							{
								getLinearLayoutParams(-2, -2).apply {
									orientation = LinearLayout.HORIZONTAL
									setGravity(Gravity.CENTER)
								}
							}
						if (option.idResource != 0)
						{
							addView(
								ImageView(context).apply {
									layoutParams = getLinearLayoutParams(56, 56)
									margin(16f, 0f, 16f, 0f)
									setImageResource(option.idResource)
								}
							)
							tag = "child"
						}
						val tv = TextView(context).apply {
							layoutParams = getLinearLayoutParams(
								if (option.idResource != 0) -2 else -1, -2
							)
							gravity = Gravity.CENTER
							id = option.idView
							setOnClickListener(onClickListener)
							text = option.text
							tag = demoParam.label
						}
						val pData = if (sizeOptions < 3)
							Pair(32, 24)
						else
							Pair(0, 24)
						tv.setPadding(pData.first, pData.second, pData.first, pData.second)

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

	fun getSegmentToggle(
		context: Context,
		demoParam: DemoParameter,
		onClickListener: View.OnClickListener): MaterialButtonToggleGroup
	{
		return ButtonToggleGroup(context).apply {
			val blue = context.getParsedColor(R.color.blue_chata_circle)
			val white = context.getParsedColor(R.color.white)

			layoutParams = getLinearLayoutParams(LayoutParams.MATCH_PARENT_WRAP_CONTENT)
			margin(12f, end = 12f)

			val sizeOptions = demoParam.options.size
			var idActive = 0
			for (iterator in 0 until sizeOptions)
			{
				val option = demoParam.options[iterator]
				if (option.isActive)
				{
					idActive = option.idView
				}
				addView(
					MaterialButton(context).apply {
						layoutParams = getLinearLayoutParams(-2, -2).apply { weight = 1f }
						val alphaColor = ColorUtils.setAlphaComponent(blue, (0.3 * 255).toInt())
						val aStates = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())
						val aColors = intArrayOf(alphaColor, white)

						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
						{
							stateListAnimator = null
						}
						backgroundTintList = ColorStateList(aStates, aColors)
						id = option.idView
						isAllCaps = false
						setTextColor(blue)
						strokeColor = ColorStateList.valueOf(blue)
						strokeWidth = 3
						tag = demoParam.label
						text = option.text
						setOnClickListener(onClickListener)
						//region icon
						if (option.idResource != 0)
						{
							icon = ContextCompat.getDrawable(context, option.idResource)
							iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
							iconPadding = dpToPx(8f)
							iconSize = dpToPx(16f)
							iconTint = ColorStateList.valueOf(blue)
							ellipsize = TextUtils.TruncateAt.END
							maxLines = 2
						}
						//endregion
					}
				)
			}
			check(idActive)
			addOnButtonCheckedListener { group, checkedId, _  ->
				group.run {
					val tmp = ArrayList<Int>()
					tmp.addAll(checkedButtonIds)

					if (demoParam.options[0].idResource != 0)
					{
						tmp.remove(idActive)
						check(idActive)
						if (tmp.isNotEmpty())
						{
							val unSelect = tmp[0]
							uncheck(unSelect)
						}
					}
					else
					{
						if (tmp.size > 1)
						{
							tmp.remove(checkedId)
							val unSelect = tmp[0]
							uncheck(unSelect)
						}
					}
				}
			}
		}
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
			margin(12f, 10.5f, 12f, 10.5f)
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
			return sb.toString().uppercase(Locale.US)
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

			for ((indexColor, color) in demoParam.colors.withIndex())
			{
				subView.addView(EditText(context).apply {
					val valueColor = color.value
					try {
						val pColor = valueColor.getContrast()
						background = DrawableBuilder.setGradientDrawable(pColor.first, 5f)
						setTextColor(pColor.second)
					}
					finally {
						layoutParams = getLinearLayoutParams(-1, -2)
						margin(12f, 10.5f, 12f, 10.5f)
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
					background = DrawableBuilder.setGradientDrawable(pColor.first, 5f)
					setTextColor(pColor.second)
				}
				finally
				{
					layoutParams = getLinearLayoutParams(-1, dpToPx(48f))
					margin(12f, 10.5f, 12f, 10.5f)
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