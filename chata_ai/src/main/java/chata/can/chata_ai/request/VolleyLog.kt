package chata.can.chata_ai.request

import android.util.Log
import java.util.*

class VolleyLog
{
	var TAG = "Volley"
	var DEBUG = Log.isLoggable(TAG, Log.VERBOSE)

	/**
	 * {@link Class#getName()} uses reflection and calling it on a potentially hot code path may
	 * have some cost. To minimize this cost we fetch class name once here and use it later.
	 */
	companion object{
		private val CLASS_NAME = this::class.java.name
	}

	/**
	 * Customize the log tag for your application, so that other apps using Volley don't mix their
	 * logs with yours. <br>
	 * Enable the log property for your tag before starting your app: <br>
	 * {@code adb shell setprop log.tag.&lt;tag&gt;}
	 */
	fun setTag(tag: String)
	{
		d("Changing log tag to %s", tag)
		TAG = tag

		DEBUG = Log.isLoggable(TAG, Log.VERBOSE)
	}

	fun v(format: String, vararg args: Any?)
	{
		if (DEBUG)
		{
			Log.v(TAG, buildMessage(format, args))
		}
	}

	fun d(format: String, vararg args: Any?)
	{
		Log.d(TAG, buildMessage(format, args))
	}

	fun e(format: String, vararg args: Any?)
	{
		Log.e(TAG, buildMessage(format, args))
	}

	fun e(tr: Throwable, format: String, vararg args: Any?)
	{
		Log.wtf(TAG, buildMessage(format, args), tr)
	}

	fun wtf(format: String, vararg args: Any?)
	{
		Log.wtf(TAG, buildMessage(format, args))
	}

	fun wtf(tr: Throwable, format: String, vararg args: Any?)
	{
		Log.wtf(TAG, buildMessage(format, args), tr)
	}

	/**
	 * Formats the caller's provided message and prepends useful info like calling thread ID and
	 * method name.
	 */
	private fun buildMessage(format: String, vararg args: Any): String
	{
		val msg = if (args.isEmpty()) format else String.format(Locale.US, format, args)
		val trace = Throwable().fillInStackTrace().fillInStackTrace().stackTrace

		var caller = "<unknown>"
		for (traceElement in trace)
		{
			val clazz = traceElement.className
			if (clazz != CLASS_NAME)
			{
				var callingClass = clazz
				callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1)
				callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1)

				caller = callingClass + "." + traceElement.methodName
				break
			}
		}
		return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().id, caller, msg)
	}
}