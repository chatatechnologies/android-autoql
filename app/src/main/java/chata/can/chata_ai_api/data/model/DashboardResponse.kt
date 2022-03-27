package chata.can.chata_ai_api.data.model

import chata.can.chata_ai.model.BaseModelList
import chata.can.chata_ai.pojo.dashboard.Dashboard
import com.google.gson.annotations.SerializedName

class DashboardResponse(
	@SerializedName("items")
	val items: List<DashboardItemResponse>
)

fun emptyDashboardResponse() = DashboardResponse(
	items = listOf()
)

class DashboardItemResponse(
	@SerializedName("created_at")
	val createdAt: String,
	@SerializedName("data")
	val data: List<DashboardItemDataResponse>,
	@SerializedName("id")
	val id: Int,
	@SerializedName("name")
	val name: String,
	@SerializedName("updated_at")
	val updatedAt: String
)

fun emptyDashboardItemResponse() = DashboardItemResponse(
	createdAt = "",
	data = listOf(),
	id = 0,
	name = "",
	updatedAt = ""
)

class DashboardItemDataResponse(
	@SerializedName("displayType")
	val displayType: String = "",
	@SerializedName("h")
	val h: Int = 0,
	@SerializedName("i")
	val i: String = "",
	@SerializedName("key")
	val key: String = "",
	@SerializedName("maxH")
	val maxH: Int = 0,
	@SerializedName("minH")
	val minH: Int = 0,
	@SerializedName("minW")
	val minW: Int = 0,
	@SerializedName("moved")
	val moved: Boolean = false,
	@SerializedName("query")
	val query: String = "",
	@SerializedName("static")
	val static: Boolean = false,
	@SerializedName("title")
	val title: String = "",
	@SerializedName("w")
	val w: Int = 0,
	@SerializedName("x")
	val x: Int = 0,
	@SerializedName("y")
	val y: Int = 0
)

fun emptyDashboardItemDataResponse() = DashboardItemDataResponse(
	displayType = "",
	h = 0,
	i = "",
	key = "",
	maxH = 0,
	minH = 0,
	minW = 0,
	moved = false,
	query = "",
	static = false,
	title = "",
	w = 0,
	x = 0,
	y = 0
)

class DashboardSingleEntity(
	val idDashboard: Int,
	val name: String,
	val mModel: BaseModelList<Dashboard>
)