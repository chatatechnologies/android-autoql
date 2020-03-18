package chata.can.chata_ai.request

/**
 * Encapsulates a parsed response for delivery.
 *
 * @param <T> Parsed type of this response
 */
class Response<T>
{
	/** Callback interface for delivering parsed responses. */
	interface Listener<T>
	{
		/** Called when a response is received. */
		fun onResponse(response: T)
	}

	/** Callback interface for delivering error responses. */
	interface ErrorListener
	{
		/**
		 * Callback method that an error has been occurred with the provided error code and optional
		 * user-readable message.
		 */
		fun onErrorResponse(error: ChataError)
	}

	/** Returns a successful response containing the parsed result. */
	fun <T> success(result: T, cacheEntry: Cache.Entry): Response<T>
	{
		return Response(result, cacheEntry)
	}

	/**
	 * Returns a failed response containing the given error code and an optional localized message
	 * displayed to the user.
	 */
	fun <T> error(error: ChataError): Response<T>
	{
		return Response(error)
	}

	/** Parsed response, or null in the case of error. */
	var result: T ?= null
	/** Cache metadata for this response, or null in the case of error. */
	var cacheEntry: Cache.Entry ?= null
	/** Detailed error information if <code>errorCode != OK</code>. */
	var error: ChataError ?= null
	/** True if this response was a soft-expired one and a second one MAY be coming. */
	var intermediate = false

	/** Returns whether this response is considered successful. */
	fun isSuccess(): Boolean = error == null

	constructor(result: T, cacheEntry: Cache.Entry)
	{
		this.result = result
		this.cacheEntry = cacheEntry
		this.error = null
	}

	constructor(error: ChataError)
	{
		this.result = null
		this.cacheEntry = null
		this.error = error
	}
}