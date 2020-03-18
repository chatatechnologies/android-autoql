package chata.can.chata_ai.request

import java.util.*

class NetworkResponse
{
	constructor(
		statusCode: Int,
		data: ByteArray,
		notModified: Boolean,
		networkTimeMs: Long,
		allHeaders: List<Header>)
	{
		initClass(statusCode, data, toHeaderMap(allHeaders), allHeaders, notModified, networkTimeMs)
	}

	private fun initClass(
		statusCode: Int,
		data: ByteArray,
		headers: Map<String, String>?,
		allHeaders: List<Header>?,
		notModified: Boolean,
		networkTimeMs: Long)
	{
		this.statusCode = statusCode
		this.data = data
		this.headers = headers
		allHeaders?.let {
			this.allHeaders = Collections.unmodifiableList(allHeaders)
		}
		this.notModified = notModified
		this.networkTimeMs = networkTimeMs
	}

	var statusCode = 0
	var data: ByteArray ?= null
	var headers: Map<String, String> ?= null
	var allHeaders: List<Header> ?= null
	var notModified = false
	var networkTimeMs = 0L

	private fun toHeaderMap(allHeaders: List<Header>?): Map<String, String>?
	{
		if (allHeaders == null)
		{
			return null
		}
		if (allHeaders.isEmpty())
		{
			return Collections.emptyMap()
		}

		val headers = TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER)
		for (header in allHeaders)
		{
			headers[header.mName] = header.mValue
		}
		return headers
	}

	private fun toAllHeaderList(headers: Map<String, String>?): List<Header>?
	{
		if (headers == null)
		{
			return null
		}
		if (headers.isEmpty())
		{
			return emptyList()
		}
		val allHeaders = ArrayList<Header>(headers.size)
		for (header in headers.entries)
		{
			allHeaders.add(Header(header.key, header.value))
		}
		return allHeaders
	}
}