package chata.can.chata_ai_api.fragment.main

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import chata.can.chata_ai.extension.setAnimator
import chata.can.chata_ai_api.R

class AnimationAlert(private val parentView: RelativeLayout)
{
	private val tvAlert: TextView = parentView.findViewById(R.id.tvAlert)
	private val ivAlert: ImageView = parentView.findViewById(R.id.ivAlert)

	fun setText(text: String)
	{
		tvAlert.text = text
	}

	fun setResource(intRes: Int)
	{
		ivAlert.setImageResource(intRes)
	}

	fun showAlert()
	{
		parentView.visibility = View.VISIBLE
		parentView.setAnimator(80f, "translationY")
	}

	fun hideAlert()
	{
		parentView.setAnimator(-300f, "translationY")
	}
}