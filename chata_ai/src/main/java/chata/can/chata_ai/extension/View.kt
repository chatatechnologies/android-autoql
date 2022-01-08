package chata.can.chata_ai.extension

import android.animation.ObjectAnimator
import android.os.Build
import android.text.Html
import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import chata.can.chata_ai.pojo.base.ItemSelectedListener
import chata.can.chata_ai.pojo.base.TabSelected
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import com.google.android.material.tabs.TabLayout

fun TextView.textSize(size: Float)
{
	setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
}

@Suppress("deprecation")
fun TextView.fromHtml(content: String)
{
	text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)
	else
		Html.fromHtml(content)
}

fun EditText.setOnTextChanged(listener: (String) -> Unit)
{
	addTextChangedListener(object: TextChanged
		{
			override fun onTextChanged(string: String)
			{
				listener(string)
			}
		}
	)
}

fun Spinner.setOnItemSelected(listener: (AdapterView<*>?, View?, Int, Long) -> Unit)
{
	onItemSelectedListener = object : ItemSelectedListener {
		override fun onSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
		{
			listener(parent, view, position, id)
		}
	}
}

fun TabLayout.setOnTabSelectedListener(
	selected: (TabLayout.Tab?) -> Unit,
	unSelected: (TabLayout.Tab?) -> Unit)
{
	addOnTabSelectedListener(object: TabSelected {
		override fun onTabSelected(tab: TabLayout.Tab?)
		{
			selected(tab)
		}

		override fun onTabUnselected(tab: TabLayout.Tab?)
		{
			unSelected(tab)
		}
	})
}

fun View.backgroundGrayWhite(iCornerRadius: Float = 18f)
{
	context.run {
		background = DrawableBuilder.setGradientDrawable(
			ThemeColor.currentColor.pDrawerBackgroundColor,iCornerRadius)
	}
}

fun View.backgroundWhiteGray()
{
	context.run {
		ThemeColor.currentColor.run {
			background = DrawableBuilder.setGradientDrawable(
				pDrawerBackgroundColor,18f,1, pDrawerTextColorPrimary)
		}
	}
}

fun View.setAnimator(yValue: Float, property: String)
{
	ObjectAnimator.ofFloat(this, property, yValue).run {
		duration = 500
		start()
	}
}