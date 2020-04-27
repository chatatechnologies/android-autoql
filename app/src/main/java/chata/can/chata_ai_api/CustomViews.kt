package chata.can.chata_ai_api

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.InputType
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

object CustomViews
{
	private val mViews = linkedMapOf<String, SparseBooleanArray>()

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

		if (demoParam.isPassword)
		{
			inputType = InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_PASSWORD)
		}
	}

	fun getButton(context: Context, demoParam: DemoParameter, onClickListener: View.OnClickListener) =
		TextView(context).apply {
		setBackgroundColor(ContextCompat.getColor(context,
			R.color.colorButton
		))
		layoutParams = LinearLayout.LayoutParams(-1, 90)
		(layoutParams as ViewGroup.MarginLayoutParams).setMargins(56, 28, 56, 28)
		gravity = Gravity.CENTER
		setTextColor(ContextCompat.getColor(context,
			R.color.textButton
		))
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

	fun getColor(context: Context, demoParam: DemoParameter, addColor:(String) -> Unit): View {
		if (demoParam.colors.size > 0)
		{
			val subView = LinearLayout(context)
			subView.layoutParams = LinearLayout.LayoutParams(-1, -2)
			subView.orientation = LinearLayout.VERTICAL

			for (index in demoParam.colors.indices)
			{
				val color = demoParam.colors[index]
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
						//bubbleHandle.addChartColor(valueColor)
						setText(valueColor)
						tag = index
					}
				})
			}

			subView.id = demoParam.idView
			return subView
		}
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
					setText(valueColor)
				}
			}
		}
	}
}