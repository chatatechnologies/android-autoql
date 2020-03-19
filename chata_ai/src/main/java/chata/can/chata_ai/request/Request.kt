package chata.can.chata_ai.request

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

	private var mRequestQueue: RequestQueue ?= null

	/** @return A tag for use with {@link TrafficStats#setThreadStatsTag(int)} */
	fun getTrafficStatsTag(): Int
	{
		return mDefaultTrafficStatsTag;
	}
}