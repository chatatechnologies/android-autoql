package chata.can.chata_ai_api.fragment.inputOutput

import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import chata.can.chata_ai.extension.getParsedColor
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.putArgs
import chata.can.chata_ai_api.BaseFragment
import chata.can.chata_ai_api.R

class InputOutputFragment: BaseFragment(), InputOutputContract
{
	companion object {
		const val nameFragment = "QueryInput/QueryOutput"
		fun newInstance() = InputOutputFragment().putArgs {
			putInt("LAYOUT", R.layout.fragment_input_output)
		}
	}

	private lateinit var ivChata: ImageView
	private lateinit var etQuery: AutoCompleteTextView

	private lateinit var presenter: InputOutputPresenter

	override fun onRenderViews(view: View)
	{
		super.onRenderViews(view)
		activity?.let { presenter = InputOutputPresenter(it, this) }
	}

	override fun initListener()
	{
		etQuery.addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				if (string.isNotEmpty())
				{
					if (SinglentonDrawer.mIsEnableAutocomplete)
					{
						presenter.getAutocomplete(string)
					}
				}
			}
		})
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

	override fun setDataAutocomplete(aData: ArrayList<String>)
	{
		if (aData.isNotEmpty())
		{

		}
	}
}