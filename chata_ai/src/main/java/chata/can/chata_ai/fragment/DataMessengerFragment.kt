package chata.can.chata_ai.fragment

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chata.can.chata_ai.BaseFragment
import chata.can.chata_ai.R
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs
import chata.can.chata_ai.view.animationAlert.AnimationAlert
import chata.can.chata_ai.view.typing.TypingAutoComplete

class DataMessengerFragment: BaseFragment()
{
	companion object {
		const val nameFragment = "Data Messenger"
		fun newInstance() = DataMessengerFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_data_messenger)
		}
	}

	private lateinit var llParent: View
	private lateinit var rvChat: RecyclerView
	private lateinit var gifView: View
	private lateinit var tvMsg: TextView
	private lateinit var ivRun: ImageView
	private lateinit var etQuery: TypingAutoComplete
	private lateinit var ivMicrophone: ImageView
	private lateinit var animationAlert: AnimationAlert

	override fun initListener()
	{

	}

	override fun initViews(view: View)
	{
		with(view)
		{
			llParent = findViewById(R.id.llParent)
			rvChat = findViewById(R.id.rvChat)
			gifView = findViewById(R.id.gifView)
			tvMsg = findViewById(R.id.tvMsg)
			ivRun = findViewById(R.id.ivRun)
			etQuery = findViewById(R.id.etQuery)
			ivMicrophone = findViewById(R.id.ivMicrophone)
			animationAlert = AnimationAlert(findViewById(R.id.rlAlert))
		}
	}

	override fun setColors()
	{
		with(ThemeColor.currentColor)
		{
			activity?.let {
				llParent.setBackgroundColor(it.getParsedColor(drawerBackgroundColor))
				tvMsg.setTextColor(it.getParsedColor(drawerColorPrimary))
				ivRun.setColorFilter(it.getParsedColor(drawerColorPrimary))

				etQuery.setHintTextColor(it.getParsedColor(drawerColorPrimary))
				etQuery.setTextColor(it.getParsedColor(drawerColorPrimary))

				val blue = it.getParsedColor(drawerAccentColor)
				val first = GradientDrawable().apply {
					shape = GradientDrawable.OVAL
					setColor(blue)
				}

				val white = it.getParsedColor(drawerBackgroundColor)
				val gray = it.getParsedColor(drawerColorPrimary)
				val second = DrawableBuilder.setGradientDrawable(white,64f,1, gray)

				ivMicrophone.background = first
				etQuery.background = second
			}
		}
	}
}