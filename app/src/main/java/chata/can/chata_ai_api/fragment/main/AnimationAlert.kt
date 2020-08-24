package chata.can.chata_ai_api.fragment.main

import android.widget.LinearLayout
import android.widget.TextView
import chata.can.chata_ai_api.R

class AnimationAlert(view: LinearLayout)
{
	private val tvAlert: TextView = view.findViewById(R.id.tvAlert)

	fun setText(text: String)
	{
		tvAlert.text = text
	}
}