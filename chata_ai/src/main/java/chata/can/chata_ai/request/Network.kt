package chata.can.chata_ai.request

/** An interface for performing requests. */
interface Network
{
	/**
	 * Performs the specified request.
	 *
	 * @param request Request to process
	 * @return A {@link NetworkResponse} with data and caching metadata; will never be null
	 * @throws ChataError on errors
	 */
	@Throws(ChataError::class)
	fun performRequest(request: Request<*>): NetworkResponse
}