package chata.can.chata_ai_api.fragment.main

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import chata.can.chata_ai.extension.setAnimator
import chata.can.chata_ai_api.R

class AnimationAlert(private val parentView: RelativeLayout)
{
	private val llAlert: LinearLayout = parentView.findViewById(R.id.llAlert)
	private val tvAlert: TextView = parentView.findViewById(R.id.tvAlert)
	private val ivAlert: ImageView = parentView.findViewById(R.id.ivAlert)

	init {
		ViewCompat.setElevation(llAlert, 12f)
	}

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