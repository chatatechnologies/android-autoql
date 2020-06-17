package chata.can.chata_ai.extension

import android.content.Context

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