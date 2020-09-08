package chata.can.chata_ai.extension

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.getStringResources(iRes: Int): String
{
	return try
	{
		getString(iRes)
	}
	catch(ex: Exception)
	{
		"No data found"
	}
}

fun Context.getParsedColor(intRes: Int) = ContextCompat.getColor(this, intRes)