package chata.can.chata_ai.retrofit.data.model.query

enum class TypeQueryDashboardHolder {
	EXECUTE_HOLDER,//0 for start view (execute message)
	LOADING_HOLDER,//1 for loading data (gifView)
	SUPPORT_HOLDER,//2 for support message
	CONTENT_HOLDER,//3 for simple text data
	WEB_HOLDER,//4 for webView data
	SUGGESTION_HOLDER,// = 5
	NO_QUERY_HOLDER,//8 for no query data
	DYNAMIC_HOLDER//10
}