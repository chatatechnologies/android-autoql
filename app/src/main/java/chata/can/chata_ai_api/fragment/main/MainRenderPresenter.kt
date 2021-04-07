package chata.can.chata_ai_api.fragment.main

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.model.DataMessengerAdmin
import chata.can.chata_ai_api.CustomViews
import chata.can.chata_ai_api.model.SectionData
import chata.can.chata_ai_api.model.TypeParameter

class MainRenderPresenter(
	private val context: Context,
	private val onClickListener: View.OnClickListener
	)
{
	fun initViews(llContainer: LinearLayout)
	{
		for ((header, demoParams) in SectionData.mData)
		{
			//region header
			with(TextView(context))
			{
				layoutParams = LinearLayout.LayoutParams(-1, -2)
				gravity = Gravity.CENTER_HORIZONTAL
				setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
				setTypeface(typeface, Typeface.BOLD)
				text = header
				llContainer.addView(this)
			}
			//endregion

			for (demoParam in demoParams)
			{
				if (demoParam.type != TypeParameter.BUTTON && demoParam.label.isNotEmpty())
				{
					//region label
					with(TextView(context))
					{
						layoutParams = LinearLayout.LayoutParams(-1, -2)
						gravity = Gravity.CENTER_HORIZONTAL
//						text = demoParam.label
						//region check * character
						if (demoParam.label.contains("*"))
						{
							SpannableStringBuilder().run {
								val spannable = SpannableString(demoParam.label)
								spannable.setSpan(ForegroundColorSpan(Color.RED), 0, 1, 0)
								append(spannable)
								setText(this, TextView.BufferType.SPANNABLE)
							}
						}
						else
						{
							text = demoParam.label
						}
						//endregion

						if (demoParam.labelId != 0)
						{
							id = demoParam.labelId
						}
						setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
						llContainer.addView(this)
					}
					//endregion
				}

				val viewChild = when(demoParam.type)
				{
					TypeParameter.TOGGLE ->
					{
						CustomViews.getSwitch(context, demoParam.value, demoParam.idView)
					}
					TypeParameter.INPUT ->
					{
						CustomViews.getEditText(context, demoParam)
					}
					TypeParameter.BUTTON ->
					{
						CustomViews.getButton(context, demoParam, onClickListener)
					}
					TypeParameter.SEGMENT ->
					{
						CustomViews.getSegment(context, demoParam, onClickListener)
					}
					TypeParameter.COLOR ->
					{
						if (demoParam.colors.isEmpty())
						{
							CustomViews.getColor(context, demoParam) {

							}
						}
						else
						{
							CustomViews.getColor(context, demoParam) {
								DataMessengerAdmin.addChartColor(it)
							}
						}
					}
				}

				llContainer.addView(viewChild)
			}
		}
	}
}