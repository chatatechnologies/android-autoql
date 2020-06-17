package chata.can.chata_ai.pojo.tool

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

object Network
{
	@Suppress("DEPRECATION")
	fun checkInternetConnection(context: Context): Boolean
	{
		val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
			cm.activeNetwork != null
		else
			cm.activeNetworkInfo != null
	}
}