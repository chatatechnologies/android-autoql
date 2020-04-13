package chata.can.chata_ai.extension

import android.graphics.PorterDuff
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import chata.can.chata_ai.pojo.base.TextChanged
import chata.can.chata_ai.pojo.color.ThemeColor

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

fun ImageView.setColorFilter()
{
	setColorFilter(
		ContextCompat.getColor(context, ThemeColor.currentColor.drawerColorPrimary),
		PorterDuff.Mode.SRC_ATOP)
}