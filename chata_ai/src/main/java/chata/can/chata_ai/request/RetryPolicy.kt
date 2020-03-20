package chata.can.chata_ai.request

/**
 * Retry policy for a request.
 *
 * <p>A retry policy can control two parameters:
 *
 * <ul>
 *   <li>The number of tries. This can be a simple counter or more complex logic based on the type
 *       of error passed to {@link #retry(VolleyError)}, although {@link #getCurrentRetryCount()}
 *       should always return the current retry count for logging purposes.
 *   <li>The request timeout for each try, via {@link #getCurrentTimeout()}. In the common case that
 *       a request times out before the response has been received from the server, retrying again
 *       with a longer timeout can increase the likelihood of success (at the expense of causing the
 *       user to wait longer, especially if the request still fails).
 * </ul>
 *
 * <p>Note that currently, retries triggered by a retry policy are attempted immediately in sequence
 * with no delay between them (although the time between tries may increase if the requests are
 * timing out and {@link #getCurrentTimeout()} is returning increasing values).
 *
 * <p>By default, Volley uses {@link DefaultRetryPolicy}.
 */
interface RetryPolicy
{
	/** Returns the current timeout (used for logging). */
	fun getCurrentTimeout(): Int

	/** Returns the current retry count (used for logging). */
	fun getCurrentRetryCount(): Int

	/**
	 * Prepares for the next retry by applying a backoff to the timeout.
	 *
	 * @param error The error code of the last attempt.
	 * @throws ChataError In the event that the retry could not be performed (for example if we ran
	 *     out of attempts), the passed in error is thrown.
	 */
	@Throws(ChataError::class)
	fun retry(error: ChataError)
}