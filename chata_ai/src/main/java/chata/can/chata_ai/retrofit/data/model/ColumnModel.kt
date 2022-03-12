package chata.can.chata_ai.retrofit.data.model

import com.google.gson.annotations.SerializedName

class ColumnModel(
	@SerializedName("display_name")
	val display_name: String,
	@SerializedName("groupable")
	val groupable: Boolean,
	@SerializedName("is_visible")
	val isVisible: Boolean,
	@SerializedName("multi_series")
	val multiSeries: Boolean,
	@SerializedName("name")
	val name: String,
	@SerializedName("type")
	val type: String
)