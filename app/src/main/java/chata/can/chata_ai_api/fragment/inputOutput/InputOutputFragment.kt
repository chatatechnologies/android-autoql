package chata.can.chata_ai_api.fragment.inputOutput

import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs
import chata.can.chata_ai_api.BaseFragment
import chata.can.chata_ai_api.R

class InputOutputFragment: BaseFragment()
{
	companion object {
		const val nameFragment = "QueryInput/QueryOutput"
		fun newInstance() = InputOutputFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_input_output)
		}
	}

	private lateinit var ivChata: ImageView
	private lateinit var etQuery: AutoCompleteTextView

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)

	}

	override fun initListener()
	{

	}

	override fun initViews(view: View)
	{
		with(view){
			ivChata = findViewById(R.id.ivChata)
			etQuery = findViewById(R.id.etQuery)
		}
	}

	override fun setColors()
	{
		etQuery.run {
			setTextColor(context.getParsedColor(ThemeColor.currentColor.drawerColorPrimary))
			setHintTextColor(context.getParsedColor(ThemeColor.currentColor.drawerHoverColor))

			val white = context.getParsedColor(ThemeColor.currentColor.drawerBackgroundColor)
			val gray = context.getParsedColor(ThemeColor.currentColor.drawerColorPrimary)
			background = DrawableBuilder.setGradientDrawable(white,64f,1, gray)
		}
	}
}