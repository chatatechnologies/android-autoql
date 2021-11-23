package chata.can.request_native

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

class Executor(
	val backgroundMethod: () -> Unit,
	val postMethod: () -> Unit
)
{
	private val executor = Executors.newSingleThreadExecutor()
	private val handler = Handler(Looper.getMainLooper())

	fun execute()
	{
		executor.execute { //Background work here
			backgroundMethod()
			handler.post { //UI Thread work here
				postMethod()
			}
		}
	}
}