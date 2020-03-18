package chata.can.chata_ai.request

import android.os.SystemClock
import android.util.Log
import java.util.*

class VolleyLog
{
	/**
	 * {@link Class#getName()} uses reflection and calling it on a potentially hot code path may
	 * have some cost. To minimize this cost we fetch class name once here and use it later.
	 */
	companion object{
		var TAG = "Volley"
		var DEBUG = Log.isLoggable(TAG, Log.VERBOSE)
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

	inner class MarkerLog
	{
		val ENABLED = DEBUG

		/** Minimum duration from first marker to last in an marker log to warrant logging. */
		private val MIN_DURATION_FOR_LOGGING_MS = 0

		inner class Marker(var name: String, var thread: Long, var time: Long)

		private val mMarkers = ArrayList<Marker>()
		var mFinished = false

		/** Adds a marker to this log with the specified name. */
		@Synchronized
		fun add(name: String, threadId: Long)
		{
			if (mFinished)
			{
				throw IllegalStateException("Marker added to finished log")
			}
			mMarkers.add(Marker(name, threadId, SystemClock.elapsedRealtime()))
		}

		/**
		 * Closes the log, dumping it to logcat if the time difference between the first and last
		 * markers is greater than {@link #MIN_DURATION_FOR_LOGGING_MS}.
		 *
		 * @param header Header string to print above the marker log.
		 */
		@Synchronized
		fun finish(header: String)
		{
			mFinished = true

			val duration = getTotalDuration()
			if (duration <= MIN_DURATION_FOR_LOGGING_MS)
			{
				return
			}

			var prevTime = mMarkers[0].time
			d("(%-4d ms) %s", duration, header)
			for (marker in mMarkers)
			{
				val thisTime = marker.time
				d("(+%-4d) [%2d] %s", (thisTime - prevTime), marker.thread, marker.name)
				prevTime = thisTime
			}
		}

		@Throws
		@Override
		protected fun finalize()
		{
			// Catch requests that have been collected (and hence end-of-lifed)
			// but had no debugging output printed for them.
			if (!mFinished)
			{
				finish("Request on the loose")
				e("Marker log finalized without finish() - uncaught exit point for request")
			}
		}

		/** Returns the time difference between the first and last events in this log. */
		private fun getTotalDuration(): Long
		{
			if (mMarkers.size == 0)
			{
				return 0L
			}
			val first = mMarkers[0].time
			val last = mMarkers[mMarkers.size - 1].time
			return last - first
		}
	}
}