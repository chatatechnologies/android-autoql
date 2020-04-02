package chata.can.chata_ai.extension

import android.widget.EditText
import chata.can.chata_ai.pojo.base.TextChanged

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