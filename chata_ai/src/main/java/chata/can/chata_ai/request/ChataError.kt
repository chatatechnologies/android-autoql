package chata.can.chata_ai.request

class ChataError: Exception
{
	var networkResponse: NetworkResponse ?= null
	var networkTimeMs = 0L

	constructor()
	{
		networkResponse = null
	}

	constructor(response: NetworkResponse)
	{
		networkResponse = response
	}

	constructor(exceptionMessage: String): super(exceptionMessage)
	{
		networkResponse = null
	}

	constructor(exceptionMessage: String, reason: Throwable): super(exceptionMessage, reason)
	{
		networkResponse = null
	}

	constructor(cause: Throwable): super(cause)
	{
		networkResponse = null
	}
}