package chata.can.chata_ai.request

interface ResponseDelivery
{
	/** Parses a response from the network or cache and delivers it. */
	fun postResponse(request: Request<*>, response: Response<*>)

	/**
	 * Parses a response from the network or cache and delivers it. The provided Runnable will be
	 * executed after delivery.
	 */
	fun postResponse(request: Request<*>, response: Response<*>, runnable: Runnable)

	/** Posts an error for the given request. */
	fun postError(request: Request<*>, error: ChataError)
}