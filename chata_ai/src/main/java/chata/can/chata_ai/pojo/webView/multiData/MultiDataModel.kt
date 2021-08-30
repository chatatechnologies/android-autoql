package chata.can.chata_ai.pojo.webView.multiData

data class MultiDataModel(
	val aCategoryMulti2: ArrayList<String>,
	val aCategoriesX: ArrayList<String>,
	val mDataOrder: LinkedHashMap<String, ArrayList<String>>,
	val aGroupedData: ArrayList<LinkedHashMap<String, ArrayList< ArrayList<String> > > >,
	val aData: ArrayList< LinkedHashMap<String, Double>>
)