package chata.can.chata_ai.view

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline

object GuideLine
{
	fun getGuideLine(context: Context, iId: Int, iOrientation: Int, percent: Float): Guideline
	{
		return Guideline(context).apply {
			id = iId
			val params = ConstraintLayout.LayoutParams(-2, -2).apply {
				orientation = iOrientation
				guidePercent = percent
			}
			layoutParams = params
		}
	}
}