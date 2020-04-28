package chata.can.chata_ai_api.fragment.main

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
import chata.can.chata_ai_api.CustomViews
import chata.can.chata_ai_api.model.SectionData
import chata.can.chata_ai_api.model.TypeParameter

class MainRenderPresenter(
	private val context: Context,
	private val onClickListener: View.OnClickListener,
	private val bubbleHandle: BubbleHandle)
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
						text = demoParam.label
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
						CustomViews.getColor(context, demoParam) {
							valueColor ->
							bubbleHandle.addChartColor(valueColor)
						}
					}
				}

				llContainer.addView(viewChild)
			}
		}
	}
}