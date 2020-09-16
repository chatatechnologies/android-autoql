package chata.can.chata_ai.activity.exploreQueries

import chata.can.chata_ai.pojo.explore.ExploreQuery

interface ExploreQueriesContract
{
	fun getRelatedQueries(relatedQuery: ExploreQuery)
}