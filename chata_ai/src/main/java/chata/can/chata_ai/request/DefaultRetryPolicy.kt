package chata.can.chata_ai.request

class DefaultRetryPolicy: RetryPolicy
{
	/** The current timeout in milliseconds. */
	private var mCurrentTimeoutMs = 0

	/** The current retry count. */
	private var mCurrentRetryCount = 0

	/** The maximum number of attempts. */
	private var mMaxNumRetries = 0

	/** The backoff multiplier for the policy. */
	private var mBackoffMultiplier = 0f

	companion object {
		/** The default socket timeout in milliseconds */
		val DEFAULT_TIMEOUT_MS = 2500

		/** The default number of retries */
		val DEFAULT_MAX_RETRIES = 1

		/** The default backoff multiplier */
		val DEFAULT_BACKOFF_MULT = 1f
	}

	/** Constructs a new retry policy using the default timeouts. */
	constructor(): this(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT)

	/**
	 * Constructs a new retry policy.
	 *
	 * @param initialTimeoutMs The initial timeout for the policy.
	 * @param maxNumRetries The maximum number of retries.
	 * @param backoffMultiplier Backoff multiplier for the policy.
	 */
	constructor(initialTimeoutMs: Int, maxNumRetries: Int, backoffMultiplier: Float)
	{
		mCurrentTimeoutMs = initialTimeoutMs
		mMaxNumRetries = maxNumRetries
		mBackoffMultiplier = backoffMultiplier
	}

	/** Returns the current timeout. */
	override fun getCurrentTimeout() = mCurrentTimeoutMs

	/** Returns the current retry count. */
	override fun getCurrentRetryCount() = mCurrentRetryCount

	/** Returns the backoff multiplier for the policy. */
	fun getBackoffMultiplier() = mBackoffMultiplier

	/**
	 * Prepares for the next retry by applying a backoff to the timeout.
	 *
	 * @param error The error code of the last attempt.
	 */
	@Throws(ChataError::class)
	override fun retry(error: ChataError)
	{
		mCurrentRetryCount++
		mCurrentTimeoutMs += (mCurrentTimeoutMs * mBackoffMultiplier).toInt()
		if (!hasAttemptRemaining())
		{
			throw error
		}
	}

	/** Returns true if this policy has attempts remaining, false otherwise. */
	protected fun hasAttemptRemaining() = mCurrentRetryCount <= mMaxNumRetries
}