package chata.can.chata_ai.fragment.exploreQuery

import chata.can.chata_ai.pojo.explore.ExploreQuery

interface ExploreQueriesContract
{
	fun showGif()
	fun showList()
	fun clearPage()
	fun getRelatedQueries(relatedQuery: ExploreQuery)
}