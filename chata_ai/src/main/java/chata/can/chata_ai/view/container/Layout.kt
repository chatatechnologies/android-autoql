package chata.can.chata_ai.view.container

import android.content.Context
import android.widget.LinearLayout
import android.widget.RelativeLayout

object Layout
{
	fun getLinearLayout(context: Context) = LinearLayout(context)

	fun getRelativeLayout(context: Context) = RelativeLayout(context)
}