package chata.can.chata_ai.view.animationAlert

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.setAnimator

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
		parentView.setAnimator(40f, "translationY")
	}

	fun hideAlert()
	{
		parentView.setAnimator(-300f, "translationY")
	}
}