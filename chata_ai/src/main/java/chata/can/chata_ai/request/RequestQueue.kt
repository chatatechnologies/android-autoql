package chata.can.chata_ai.request

import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.atomic.AtomicInteger

/**
 * A request dispatch queue with a thread pool of dispatchers.
 *
 * <p>Calling {@link #add(Request)} will enqueue the given Request for dispatch, resolving from
 * either cache or network on a worker thread, and then delivering a parsed response on the main
 * thread.
 */
class RequestQueue
{
	/** Callback interface for completed requests. */
	// TODO: This should not be a generic class, because the request type can't be determined at
	// compile time, so all calls to onRequestFinished are unsafe. However, changing this would be
	// an API-breaking change. See also: https://github.com/google/volley/pull/109
	interface RequestFinishedListener<T>
	{
		fun onRequestFinished(request: Request<T>)
	}

	/** Used for generating monotonically-increasing sequence numbers for requests. */
	val mSequenceGenerator = AtomicInteger()

	/**
	 * The set of all requests currently being processed by this RequestQueue. A Request will be in
	 * this set if it is waiting in any queue or currently being processed by any dispatcher.
	 */
	val mCurrentRequest: Set<Request<*>> = HashSet()

	/** The cache triage queue. */
	val mCacheQueue = PriorityBlockingQueue<Request<*>>()

	/** The queue of requests that are actually going out to the network. */
	val mNetworkQueue = PriorityBlockingQueue<Request<*>>()

	/** Number of network request dispatcher threads to start. */
	val defaultNetworkThreadPoolSize = 4

	/** Cache interface for retrieving and storing responses. */
	var mCache: Cache ?= null

	/** Network interface for performing requests. */
	var mNetwork: Network ?= null

	/** Response delivery mechanism. */
	var mDelivery: ResponseDelivery ? = null

	/** The network dispatchers. */
	private val mDispatchers: ArrayList<NetworkDispatcher> ?= null

	/** The cache dispatcher. */
	//private var mCacheDispatcher: CacheDispatcher ?= null
}