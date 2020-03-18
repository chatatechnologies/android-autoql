package chata.can.chata_ai.request

import java.util.*

interface Cache
{
	/**
	 * Retrieves an entry from the cache.
	 *
	 * @param key Cache key
	 * @return An {@link Entry} or null in the event of a cache miss
	 */
	fun get(key: String): Entry

	/**
	 * Adds or replaces an entry to the cache.
	 *
	 * @param key Cache key
	 * @param entry Data to store and metadata for cache coherency, TTL, etc.
	 */
	fun put(key: String, entry: Entry)

	/**
	 * Performs any potentially long-running actions needed to initialize the cache; will be called
	 * from a worker thread.
	 */
	fun initialize()

	/**
	 * Invalidates an entry in the cache.
	 *
	 * @param key Cache key
	 * @param fullExpire True to fully expire the entry, false to soft expire
	 */
	fun invalidate(key: String, fullExpire: Boolean)

	/**
	 * Removes an entry from the cache.
	 *
	 * @param key Cache key
	 */
	fun remove(key: String)

	/** Empties the cache. */
	fun clear()

	class Entry
	{
		var data: ByteArray ? = null
		var etag = ""
		var serverDate = 0L
		var lastModified = 0L
		var ttl = 0L
		var softTtl = 0L
		val responseHeaders: Map<String, String> = Collections.emptyMap()
		var allResponseHeaders: List<Header> ?= null

		fun isExpired() = this.ttl < System.currentTimeMillis()
		fun refreshNeeded() = this.softTtl < System.currentTimeMillis()
	}
}