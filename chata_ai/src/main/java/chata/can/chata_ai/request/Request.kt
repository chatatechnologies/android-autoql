package chata.can.chata_ai.request

import android.net.Uri
import android.text.TextUtils

abstract class Request<T>: Comparable<Request<T>>
{
	/** Default encoding for POST or PUT parameters. See {@link #getParamsEncoding()}. */
	val DEFAULT_PARAMS_ENCODING = "UTF-8"

	/** Callback to notify when the network request returns. */
	interface NetworkRequestCompleteListener
	{
		/** Callback when a network response has been received. */
		fun onResponseReceived(request: Request<*>?, response: Response<*>?)

		/** Callback when request returns from network without valid response. */
		fun onNoUsableResponseReceived(request: Request<*>?)
	}

	/** An event log tracing the lifetime of this request; for debugging. */
	val mEventLog = if (VolleyLog.DEBUG) VolleyLog().MarkerLog() else null

	/**
	 * Request method of this request. Currently supports GET, POST, PUT, DELETE, HEAD, OPTIONS,
	 * TRACE, and PATCH.
	 */
	var mMethod = 0

	/** URL of this request. */
	var mUrl = ""

	/** Default tag for {@link TrafficStats}. */
	var mDefaultTrafficStatsTag = 0

	/** Lock to guard state which can be mutated after a request is added to the queue. */
	var mLock = Object()

	/** Listener interface for errors. */
	var mErrorListener: Response.ErrorListener ?= null

	/** Sequence number of this request, used to enforce FIFO ordering. */
	private var mSequence = 0

	/** The request queue this request is associated with. */
	private var mRequestQueue: RequestQueue ?= null

	/** Whether or not responses to this request should be cached. */
	// TODO(#190): Turn this off by default for anything other than GET requests.
	private var mShouldCache = true

	/** Whether or not this request has been canceled. */
	private var mCanceled = false

	/** Whether or not a response has been delivered for this request yet. */
	private var mResponseDelivered = false

	/** Whether the request should be retried in the event of an HTTP 5xx (server) error. */
	private var mShouldRetryServerErrors = false

	/** The retry policy for this request. */
	private var mRetryPolicy: RetryPolicy ?= null

	/**
	 * When a request can be retrieved from cache but must be refreshed from the network, the cache
	 * entry will be stored here so that in the event of a "Not Modified" response, we can be sure
	 * it hasn't been evicted from cache.
	 */
	private var mCacheEntry: Cache.Entry ?= null

	/** An opaque token tagging this request; used for bulk cancellation. */
	private var mTag: Any?= null

	/** Listener that will be notified when a response has been delivered. */
	private var mRequestCompleteListener: NetworkRequestCompleteListener ?= null

	/**
	 * Creates a new request with the given URL and error listener. Note that the normal response
	 * listener is not provided here as delivery of responses is provided by subclasses, who have a
	 * better idea of how to deliver an already-parsed response.
	 *
	 * @deprecated Use {@link #Request(int, String, com.android.volley.Response.ErrorListener)}.
	 */
	constructor(url: String, listener: Response.ErrorListener)
	{

	}

	/**
	 * Creates a new request with the given method (one of the values from {@link Method}), URL, and
	 * error listener. Note that the normal response listener is not provided here as delivery of
	 * responses is provided by subclasses, who have a better idea of how to deliver an
	 * already-parsed response.
	 */
	constructor(method: Int, url: String, listener: Response.ErrorListener)
	{
		mMethod = method
		mUrl = url
		mErrorListener = listener
		setRetryPolicy(DefaultRetryPolicy())

		//mDefaultTrafficStatsTag = mDefaultTrafficStatsTag(url)
	}

	/** Return the method for this request. Can be one of the values in {@link Method}. */
	fun getMethod() = mMethod

	/**
	 * Set a tag on this request. Can be used to cancel all requests with this tag by {@link
	 * RequestQueue#cancelAll(Object)}.
	 *
	 * @return This Request object to allow for chaining.
	 */
	fun setTag(tag: Any): Request<*>
	{
		mTag = tag
		return this
	}

	/**
	 * Returns this request's tag.
	 *
	 * @see Request#setTag(Object)
	 */
	fun getTag() = mTag

	/** @return this request's {@link com.android.volley.Response.ErrorListener}. */
	fun getErrorListener(): Response.ErrorListener?
	{
		synchronized(mLock) { return mErrorListener }
	}

	/** @return A tag for use with {@link TrafficStats#setThreadStatsTag(int)} */
	fun getTrafficStatsTag(): Int
	{
		return mDefaultTrafficStatsTag
	}

	/** @return The hashcode of the URL's host component, or 0 if there is none. */
	fun findDefaultTrafficStatsTag(url: String): Int
	{
		if (!TextUtils.isEmpty(url))
		{
			val uri = Uri.parse(url)
			if (uri != null)
			{
				val host = uri.host
				if (host != null)
				{
					return host.hashCode()
				}
			}
		}
		return 0
	}

	/**
	 * Sets the retry policy for this request.
	 *
	 * @return This Request object to allow for chaining.
	 */
	fun setRetryPolicy(retryPolicy: RetryPolicy): Request<*>
	{
		mRetryPolicy = retryPolicy
		return this
	}

	/** Adds an event to this request's event log; for debugging. */
	fun addMarker(tag: String)
	{
		if (VolleyLog.DEBUG)
		{
			mEventLog?.add(tag, Thread.currentThread().id)
		}
	}

	/**
	 * Notifies the request queue that this request has finished (successfully or with error).
	 *
	 * <p>Also dumps all events from this request's event log; for debugging.
	 */
	fun finish(tag: String)
	{
		if (mRequestQueue != null)
		{
			mRequestQueue
		}
	}
}