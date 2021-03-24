package chata.can.chata_ai.activity.dm

import android.content.Context
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object Currency
{
	val mCurrency = LinkedHashMap<String, String>()
	fun initCurrency(context: Context)
	{
		if (mCurrency.size == 0)
		{
			context.assets?.let { itAssets ->
				try {
					val inputStream: InputStream = itAssets.open("currency_symbols.json")
					val inputStreamReader = InputStreamReader(inputStream)
					val sb = StringBuilder()
					var line: String?
					val br = BufferedReader(inputStreamReader)
					line = br.readLine()
					while (line != null)
					{
						sb.append(line)
						line = br.readLine()
					}
					br.close()
					getCurrencySymbol(sb.toString())
				} catch (ex: Exception) { }
			}
		}
	}

	private fun getCurrencySymbol(currencySymbol: String)
	{
		with(JSONObject(currencySymbol))
		{
			for (key in keys())
			{
				if (!isNull(key))
				{
					val value = optString(key) ?: ""
					if (value.isNotEmpty())
					{
						mCurrency[key] = value
					}
				}
			}
		}
	}
}