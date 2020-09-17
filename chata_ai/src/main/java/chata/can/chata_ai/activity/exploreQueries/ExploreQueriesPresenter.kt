package chata.can.chata_ai.activity.exploreQueries

import chata.can.chata_ai.view.bubbleHandle.DataMessenger
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.dataKey
import chata.can.chata_ai.pojo.explore.ExploreQuery
import chata.can.chata_ai.pojo.request.RequestBuilder.callStringRequest
import chata.can.chata_ai.pojo.request.StatusResponse
import chata.can.chata_ai.pojo.typeJSON
import chata.can.chata_ai.request.authentication.Authentication.getAuthorizationJWT
import com.android.volley.Request
import org.json.JSONArray
import org.json.JSONObject

class ExploreQueriesPresenter(private val view: ExploreQueriesContract): StatusResponse
{
	private var currentQuery = ""
	override fun onSuccess(jsonObject: JSONObject?, jsonArray: JSONArray?)
	{
		if(jsonObject != null)
		{
			when(jsonObject.optString("nameService"))
			{
				"validate" ->
				{
					jsonObject.optJSONObject(dataKey)?.let {
						joData ->
						joData.optJSONArray("replacements")?.let {
							jaReplacements ->
							if (jaReplacements.length() == 0)
							{
								getRelatedQueries()
							}
						}
					}
				}
				"related-queries" ->
				{
					jsonObject.optJSONObject(dataKey)?.let {
						joData ->
						val aItems = ArrayList<String>()
						//region items
						joData.optJSONArray("items")?.let {
							jaItems ->
							for(index in 0 until jaItems.length())
							{
								jaItems.optString(index)?.let {
									joItem ->
									aItems.add(joItem)
								}
							}
						}
						//endregion
						joData.optJSONObject("pagination")?.run {
							val currentPage = optInt("current_page")
							val totalPages = optInt("total_pages")
							val totalItems = optInt("total_items")
							val pageSize = optInt("page_size")
							val nextUrl = if (isNull("next_url")) "" else optString("next_url")
							val previousUrl = if (isNull("previous_url")) "" else optString("previous_url")

							val exploreQuery =
								ExploreQuery(aItems, currentPage, totalPages, totalItems, pageSize, nextUrl, previousUrl)
							view.getRelatedQueries(exploreQuery)
						}
					}
				}
				else ->
				{

				}
			}
		}
	}

	override fun onFailure(jsonObject: JSONObject?) {}

	fun validateQuery(query: String)
	{
		with(DataMessenger)
		{
			currentQuery = query
			val header = getAuthorizationJWT()
			val url = "$domainUrl/autoql/${api1}query/validate?text=$query&key=$apiKey"

			callStringRequest(
				Request.Method.GET,
				url,
				typeJSON,
				headers = header,
				infoHolder = hashMapOf("nameService" to "validate"),
				listener = this@ExploreQueriesPresenter)
		}
	}

	private fun getRelatedQueries(pageSize: Int = 11, page: Int = 1)
	{
		with(DataMessenger)
		{
			val header = getAuthorizationJWT()
			val url = "$domainUrl/autoql/${api1}query/related-queries?key=$apiKey" +
				"&search=$currentQuery&page_size=$pageSize&page=$page"
			callStringRequest(
				Request.Method.GET,
				url,
				typeJSON,
				headers = header,
				infoHolder = hashMapOf("nameService" to "related-queries"),
				listener = this@ExploreQueriesPresenter)
		}
	}
}