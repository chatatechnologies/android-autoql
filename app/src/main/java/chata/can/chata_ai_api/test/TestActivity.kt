package chata.can.chata_ai_api.test

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import chata.can.chata_ai.extension.margin
import chata.can.chata_ai.extension.marginAll
import chata.can.chata_ai.extension.paddingAll
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai_api.R
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout

class TestActivity: AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
//		setContentView(R.layout.test_activity)
		//region FlexboxLayout
		val root = FlexboxLayout(this).apply {
			layoutParams = ViewGroup.LayoutParams(-1, -2)
			flexWrap = FlexWrap.WRAP
			(0..6).forEach {
				iterator ->
				val view = newTextView(this@TestActivity, iterator)
				addView(view)
			}
		}
		//endregion
		setContentView(root)
	}

	private fun newTextView(context: Context, iterator: Int) = TextView(context).apply {
		layoutParams = FlexboxLayout.LayoutParams(-2, -2)
		background = DrawableBuilder.setGradientDrawable(Color.GREEN, 3f, 3, Color.BLUE)
		paddingAll(8f)
		marginAll(8f)
		val text = "${getString(R.string.app_name)} $iterator"
		setText(text)
	}
}