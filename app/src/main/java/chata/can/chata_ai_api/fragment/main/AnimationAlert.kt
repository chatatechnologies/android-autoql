package chata.can.chata_ai_api.fragment.main

import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai_api.R

class AnimationAlert(view: RelativeLayout)
{
	private val tvAlert: TextView = view.findViewById(R.id.tvAlert)
	private val ivAlert: ImageView = view.findViewById(R.id.ivAlert)

	fun setText(text: String)
	{
		tvAlert.text = text
	}

	fun setResource(intRes: Int)
	{
		ivAlert.setImageResource(intRes)
	}
}