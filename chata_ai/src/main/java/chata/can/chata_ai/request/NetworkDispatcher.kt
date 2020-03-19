package chata.can.chata_ai.request

import android.annotation.TargetApi
import android.net.TrafficStats
import android.os.Build
import java.util.concurrent.BlockingQueue

class NetworkDispatcher: Thread
{
	/** The queue of requests to service. */
	var mQueue: BlockingQueue<Request<*>> ?= null
	/** The network interface for processing requests. */
	var mNetwork: Network ?= null
	/** The cache to write to. */
	var mCache: Cache ?= null
	/** For posting responses and errors. */
	var mDelivery: ResponseDelivery ?= null
	/** Used for telling us to die. */
	@Volatile
	private var mQuit: Boolean = false

	/**
	 * Creates a new network dispatcher thread. You must call {@link #start()} in order to begin
	 * processing.
	 *
	 * @param queue Queue of incoming requests for triage
	 * @param network Network interface to use for performing requests
	 * @param cache Cache interface to use for writing responses to cache
	 * @param delivery Delivery interface to use for posting responses
	 */
	constructor(
		queue: BlockingQueue<Request<*>>,
		network: Network,
		cache: Cache,
		delivery: ResponseDelivery)
	{
		mQueue = queue
		mNetwork = network
		mCache = cache
		mDelivery = delivery
	}

	/**
	 * Forces this dispatcher to quit immediately. If any requests are still in the queue, they are
	 * not guaranteed to be processed.
	 */
	fun quit()
	{
		mQuit = true
		interrupt()
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	fun addTrafficStatsTag(request: Request<*>)
	{
		// Tag the request (if API >= 14)
		TrafficStats.setThreadStatsTag(request.getTrafficStatsTag())
	}

	override fun run()
	{
		super.run()
	}
}