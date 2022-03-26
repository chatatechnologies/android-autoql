package chata.can.chata_ai_api.data.model

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
	@SerializedName("id")
	val id: Int,
	@SerializedName("name")
	val name: String,
	@SerializedName("updated_at")
	val updatedAt: String
)

fun emptyDashboardItemResponse() = DashboardItemResponse(
	createdAt = "",
	id = 0,
	name = "",
	updatedAt = ""
)