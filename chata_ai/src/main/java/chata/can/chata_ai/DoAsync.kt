package chata.can.chata_ai

import android.os.AsyncTask

class DoAsync(
	val handler: () -> Unit,
	val postMethod: () -> Unit): AsyncTask<Void, Void, Void>()
{
	override fun doInBackground(vararg params: Void?): Void?
	{
		handler()
		return null
	}

	override fun onPostExecute(result: Void?)
	{
		postMethod()
	}
}