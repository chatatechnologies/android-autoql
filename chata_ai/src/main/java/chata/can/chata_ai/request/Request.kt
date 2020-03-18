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
}